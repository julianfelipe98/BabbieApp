package com.app.petmgt.repository


import com.app.base.model.network.petMgt.Breed
import com.app.base.model.network.petMgt.Pet
import com.app.base.model.network.petMgt.Species
import com.app.petmgt.network.PetMgtService
import com.app.petmgt.utils.ResponseModel
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.json.JSONObject
import retrofit2.Response
import javax.inject.Inject


class PetRepository @Inject constructor(
    private val service: PetMgtService
) {

    suspend fun getPets(userId: String): List<Pet> {
        return service.getPets(userId)
    }

    suspend fun getPet(petId: String): Pet {
        return service.getPet(petId)
    }

    suspend fun getSpecies(): List<Species> {
        return service.getSpecies()
    }

    suspend fun getBreed(idSpecies: String): List<Breed> {
        return service.getBreedBySpecies(idSpecies)
    }

    suspend fun createPet(pet: Pet): ResponseModel {
        return service.createPet(pet)
    }

    suspend fun createImage(
        file: MultipartBody.Part,
        filename: RequestBody
    ): Response<JSONObject> {
        return service.setImage(file, filename)
    }

    suspend fun updateImage(
        file: MultipartBody.Part,
        filename: RequestBody, petId: String
    ): Response<JSONObject> {
        return service.updateImage(file, filename, petId)
    }

    suspend fun updatePet(pet: Pet, id: String) {
        service.updatePet(pet, id).toString()
    }
}