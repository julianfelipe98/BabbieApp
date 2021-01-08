package com.app.petmgt.pets

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.graphics.drawable.BitmapDrawable
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.net.toUri
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.app.base.model.network.petMgt.Breed
import com.app.base.model.network.petMgt.Pet
import com.app.base.model.network.petMgt.Species
import com.app.base.utils.NetworkApiStatus
import com.app.base.utils.Utils.getMimeType
import com.app.petmgt.R
import com.app.petmgt.databinding.FragmentCreatePetBinding
import com.app.petmgt.pets.viewModel.CreatePetViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import dagger.hilt.android.AndroidEntryPoint
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

@AndroidEntryPoint
class CreatePetFragment : Fragment() {

    private lateinit var binding: FragmentCreatePetBinding
    private val PICK_IMAGE_REQUEST = 71
    private var filePath: Uri = Uri.EMPTY
    private val viewModel: CreatePetViewModel by viewModels()

    private var pet = Pet()
    private var fileExtension = "jpg"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_create_pet, container, false)
        binding.lifecycleOwner = this
        binding.dateButton.setOnClickListener { showDatePickerDialog() }
        binding.pet = pet
        binding.imageButton.setOnClickListener { launchGallery() }
        initSpinners()
        initData()
        loadImage()
        return binding.root
    }

    private fun initData() {

        val id: String? =
            CreatePetFragmentArgs.fromBundle(requireArguments()).idPet

        if (id != null) {
            binding.loadingPanel.visibility = View.VISIBLE
            viewModel.getPetData(id)
            viewModel.pet.observe(requireActivity(), Observer {
                if (it !== null) binding.loadingPanel.visibility = View.GONE
                binding.pet = it
                bindImage(binding.imgView, it.picture_url)
                binding.speciesEditText.setSelection(
                    getIndex(
                        binding.speciesEditText,
                        it.breed.specie
                    )
                )
            })
            initViews(false)
        } else {
            initViews(true)
        }
    }

    private fun bindImage(imgView: ImageView, imgUrl: String?) {

        imgUrl?.let {
            val imgUri = imgUrl.toUri().buildUpon().scheme("https").build()
            Glide.with(imgView.context)
                .load(imgUri)
                .apply(
                    RequestOptions()
                        .placeholder(R.drawable.loading_animation)
                        .error(R.drawable.pancho)
                ).into(imgView)
        }

    }

    private fun updatePet() {

        viewModel.updatePet(pet)
        viewModel.pet.observe(requireActivity(), Observer {
            Toast.makeText(
                requireContext(),
                getString(R.string.
                success_edited_pet),
                Toast.LENGTH_LONG
            ).show()
            val pet = it
            viewModel.finishActivity.observe(requireActivity(), Observer { isFinish ->
                if (isFinish) {
                    findNavController().navigate(
                        CreatePetFragmentDirections.actionCreatePetFragmentToDetailPetFragment(pet)
                    )
                }
            })
        })
    }

    private fun createPet() {
        viewModel.createPet(pet, fileExtension)
        viewModel.finishActivity.observe(requireActivity(), Observer { isFinish ->
            if (isFinish) {
                Toast.makeText(
                    requireContext(),
                    "Mascota creada satisfactoriamente",
                    Toast.LENGTH_LONG
                ).show()
                findNavController().navigate(CreatePetFragmentDirections.actionCreatePetFragmentToPetListFragment())
            }
        })
    }

    private fun initSpinners() {

        viewModel.species.observe(requireActivity(), Observer {
            val a = ArrayAdapter<Species>(
                requireContext(), android.R.layout.simple_spinner_dropdown_item, it
            )
            val spinner = binding.speciesEditText
            spinner.adapter = a
            spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                    TODO("Not yet implemented")
                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    pet.specie = spinner.selectedItem as Species
                    initBreedSpeener(pet.specie.id)
                }
            })
        })
    }

    private fun initBreedSpeener(id: String) {

        viewModel.getBreedBySpecies(id)
        viewModel.breeds.observe(requireActivity(), Observer { it ->
            val sortedAppsList = it.sortedBy { it.name }
            val a = ArrayAdapter<Breed>(
                requireContext(), android.R.layout.simple_spinner_dropdown_item, sortedAppsList
            )
            val spinner = binding.breedSpinner
            spinner.adapter = a

            spinner.setOnItemSelectedListener(object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {

                }

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    pet.breed = spinner.selectedItem as Breed
                    pet.breed_id = pet.breed.id
                }
            })
        })
    }

    private fun showDatePickerDialog() {

        var cal = Calendar.getInstance()
        val textView: TextView = binding.datePickerEditField

        val dateSetListener =
            DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                cal.set(Calendar.YEAR, year)
                cal.set(Calendar.MONTH, monthOfYear)
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val myFormat = "dd/MM/yyyy"
                val sdf = SimpleDateFormat(myFormat, Locale.US)
                textView.setText(sdf.format(cal.time))
            }

        val dpd = DatePickerDialog(
            requireContext(), dateSetListener,
            cal.get(Calendar.YEAR),
            cal.get(Calendar.MONTH),
            cal.get(Calendar.DAY_OF_MONTH)
        )

        dpd.datePicker.maxDate = (cal.getTimeInMillis())
        dpd.show()
    }

    private fun launchGallery() {
        val intent = Intent()
        intent.type = "image/jpeg"
        intent.action = Intent.ACTION_GET_CONTENT
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data == null || data.data == null) {
                return
            }
            filePath = data.data!!
            fileExtension = getMimeType(requireContext(), filePath) ?: ""
            try {
                val bitmap = when {
                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.P -> {
                        val source =
                            ImageDecoder.createSource(requireActivity().contentResolver, filePath)
                        ImageDecoder.decodeBitmap(source)
                    }
                    else -> MediaStore.Images.Media.getBitmap(
                        requireActivity().contentResolver, filePath
                    )
                }
                Glide.with(binding.imgView).clear(binding.imgView)
                binding.imgView.setImageBitmap(bitmap)
                imageData()
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    private fun initViews(isCreated: Boolean) {
        val editTextTextPetName: EditText = binding.nameEditText
        val editDate: EditText = binding.datePickerEditField

        binding.submitButton.setOnClickListener {
            val textName: String = editTextTextPetName.text.toString()
            val textDate: String = editDate.text.toString()
            when {
                textName.trim().isEmpty() -> editTextTextPetName.error = getString(R.string.mandatory_field)
                textDate.trim().isEmpty() -> editDate.error = getString(R.string.mandatory_field)
                else -> {
                    if (isCreated) createPet() else updatePet()
                }
            }
        }
    }

    fun imageData() {
        val bitmap = (binding.imgView.drawable as BitmapDrawable).bitmap
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()
        viewModel.setData(data)
    }

    private fun getIndex(spinner: Spinner, species: Species): Int {
        var index = 0
        for (i in 0 until spinner.count) {
            if ((spinner.getItemAtPosition(i) as Species).name == species.name) {
                index = i
            }
        }
        return index
    }

    private fun loadImage() {
        viewModel.statusImage.observe(requireActivity(), Observer {
            if (it == NetworkApiStatus.LOADING) {
                binding.loadingPanel.visibility = View.VISIBLE
                Toast.makeText(requireContext(), "Uploading image", Toast.LENGTH_LONG).show()

            } else {
                binding.loadingPanel.visibility = View.GONE
                Toast.makeText(requireContext(), "${it.name}", Toast.LENGTH_LONG).show()
            }
        })
    }

}