package com.app.petmgt.pets.viewModel

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.*
import com.app.base.di.UserId
import com.app.base.model.network.petMgt.Breed
import com.app.base.model.network.petMgt.Pet
import com.app.base.model.network.petMgt.Species
import com.app.base.utils.NetworkApiStatus
import com.app.base.utils.Utils
import com.app.petmgt.repository.PetRepository
import com.app.petmgt.utils.ResponseModel
import kotlinx.coroutines.launch
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreatePetViewModel @ViewModelInject constructor(
    @UserId private val userId: String,
    private val petRepository: PetRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _status = MutableLiveData<NetworkApiStatus>()
    val status: LiveData<NetworkApiStatus> = _status

    private val _statusImage = MutableLiveData<NetworkApiStatus>()
    val statusImage: LiveData<NetworkApiStatus> = _statusImage

    private val _species = MutableLiveData<List<Species>>()
    val species: LiveData<List<Species>> = _species

    private val _breeds = MutableLiveData<List<Breed>>()
    val breeds: LiveData<List<Breed>> = _breeds

    private val _pet = MutableLiveData<Pet>()
    val pet: LiveData<Pet> = _pet

    private val _finishActivity = MutableLiveData<Boolean>()
    val finishActivity: LiveData<Boolean> = _finishActivity

    private val _dataImage = MutableLiveData<ByteArray>()
    val dataImage: LiveData<ByteArray> = _dataImage

    init {
        getSpecies()
    }

    fun setData(data: ByteArray) {
        _dataImage.value = data
    }

    private fun getSpecies() {

        viewModelScope.launch {
            try {
                val data = petRepository.getSpecies()
                _species.value = data
            } catch (t: Throwable) {
                _species.value = ArrayList()
            }
        }
    }

    fun getBreedBySpecies(idSpecies: String) {

        viewModelScope.launch {
            try {
                val data = petRepository.getBreed(idSpecies)
                _breeds.value = data
            } catch (t: Throwable) {
                _breeds.value = ArrayList()
            }
        }
    }

    fun createPet(pet: Pet, fileExtension: String) {
        pet.user_id = userId
        pet.mime_type = "image/${fileExtension}"
        pet.born_date = "${pet._born_date}"
        viewModelScope.launch {
            try {
                val data = petRepository.createPet(pet)
                if (_dataImage.value != null) {
                    createImage(data)
                } else {
                    _finishActivity.value = true
                }
            } catch (t: Throwable) {
                _finishActivity.value = false
            }
        }
    }

    fun getPetData(id: String) {

        viewModelScope.launch {
            try {
                var data = petRepository.getPet(id)
                data.born_date = Utils.stringFormatToString(data.born_date)
                _pet.value = data
            } catch (t: Throwable) {
                Log.e("_status.value", "No se pudo cargar la informacion de la mascota ")
            }
        }
    }

    fun updatePet(pet: Pet) {

        viewModelScope.launch {
            try {
                _status.value = NetworkApiStatus.LOADING
                _pet.value?.let {
                    it.born_date = "${it._born_date}"
                    it.breed_id = pet.breed.id
                    petRepository.updatePet(it, it.id)
                    if (_dataImage.value != null) {
                        upDateImage(it.id)
                    } else {
                        _finishActivity.value = true
                    }
                }
            } catch (t: Throwable) {
                _finishActivity.value = false
            }
        }
    }

    private fun createImage(fileName: ResponseModel) {

        val fileReqBody: RequestBody =
            RequestBody.create(MediaType.parse("image/*"), _dataImage.value)
        val part =
            MultipartBody.Part.createFormData("profile_picture", fileName.filename, fileReqBody)
        val filename = RequestBody.create(
            MediaType.parse("text/plain"),
            fileName.filename
        )
        viewModelScope.launch {
            _statusImage.value = NetworkApiStatus.LOADING
            petRepository.createImage(part, filename)
            _statusImage.value = NetworkApiStatus.DONE
            _finishActivity.value = true
        }
    }

    private fun upDateImage(petId: String) {

        val fileReqBody: RequestBody =
            RequestBody.create(MediaType.parse("image/jpeg"), _dataImage.value)
        val part =
            MultipartBody.Part.createFormData("profile_picture", "image.jpg", fileReqBody)
        val filename = RequestBody.create(
            MediaType.parse("text/plain"),
            "image/jpeg"
        )
        viewModelScope.launch {
            try {
                _statusImage.value = NetworkApiStatus.LOADING
                petRepository.updateImage(part, filename, petId)
                _statusImage.value = NetworkApiStatus.DONE
                _finishActivity.value = true
            } catch (t: Throwable) {
                _statusImage.value = NetworkApiStatus.ERROR
                _finishActivity.value = false
            }
        }
    }

}