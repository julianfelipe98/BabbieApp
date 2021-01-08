package com.app.auth.profile

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.EditText
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.app.auth.R
import com.app.auth.databinding.ActivityProfileBinding
import com.app.auth.profile.viewModel.ProfileViewModel
import com.app.base.model.network.User
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileActivity : AppCompatActivity() {

    lateinit var binding: ActivityProfileBinding
    private val viewModel: ProfileViewModel by viewModels()
    private val user = User()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_profile)
        binding.user = user
        initViews()
    }

    private fun initViews() {
        val editTextTextPersonName: EditText = binding.editTextTextSignUpPersonName
        val editTextTextPersonPhone: EditText = binding.editTextTextSignUpPersonphone
        val editTextTextPersonAddress: EditText = binding.editTextTextSignUpPersonAdress

        binding.button2.setOnClickListener {
            val textName: String = editTextTextPersonName.text.toString()
            val textPhone: String = editTextTextPersonPhone.text.toString()
            val textAddress: String = editTextTextPersonAddress.text.toString()
            when {
                textName.trim().isEmpty() -> editTextTextPersonName.error =
                    getString(R.string.mandatory_field)
                textPhone.trim().isEmpty() -> editTextTextPersonPhone.error =
                    getString(R.string.mandatory_field)
                textAddress.trim().isEmpty() -> editTextTextPersonAddress.error =
                    getString(R.string.mandatory_field)
                else -> {
                    viewModel.createUser(user)
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse("com.app.firuapp://main"))
                    startActivity(intent)
                }
            }
        }
    }
}