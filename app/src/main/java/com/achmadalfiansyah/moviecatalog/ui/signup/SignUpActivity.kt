package com.achmadalfiansyah.moviecatalog.ui.signup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import com.achmadalfiansyah.moviecatalog.databinding.ActivitySignUpBinding
import com.achmadalfiansyah.moviecatalog.ui.home.HomeActivity
import com.achmadalfiansyah.moviecatalog.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class SignUpActivity : AppCompatActivity() {
    private var _binding: ActivitySignUpBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        inputFormValidation()
        binding.btnSignUp.setOnClickListener {
            showProgressBar(true)
            val userName = binding.etFullName.text.toString()
            val email = binding.etEmail.text.toString()
            val phoneNumber = binding.etPhoneNumber.text.toString()
            val password = binding.etPassword.text.toString()
            registerUser(userName, phoneNumber, email, password)
        }
        binding.login.setOnClickListener {
            Intent(this@SignUpActivity, LoginActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
            }
        }
    }

    private fun inputFormValidation() {
        var password = ""
        with(binding) {
            etFullName.doOnTextChanged { text, _, _, _ ->
                if (text.isNullOrEmpty()) {
                    etFullName.error = "This field cannot be blank"
                    btnSignUp.isEnabled = false
                } else {
                    etFullName.error = null
                    btnSignUp.isEnabled = true
                }
            }
            etEmail.doOnTextChanged { text, _, _, _ ->
                when {
                    text.isNullOrEmpty() -> {
                        etEmail.error = "This field cannot be blank"
                        btnSignUp.isEnabled = false
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(text).matches()-> {
                        etEmail.error = "Please enter a valid email address"
                        btnSignUp.isEnabled = false
                    }
                    else -> {
                        etEmail.error = null
                        btnSignUp.isEnabled = true
                    }
                }
            }
            etPhoneNumber.doOnTextChanged { text, _, _, _ ->
                when {
                    text.isNullOrEmpty() -> {
                        etPhoneNumber.error = "This field cannot be blank"
                        btnSignUp.isEnabled = false
                    }
                    text.length < 11 -> {
                        etPhoneNumber.error = "This field must be at least 11 characters in length"
                        btnSignUp.isEnabled = false
                    }
                    else -> {
                        etPhoneNumber.error = null
                        btnSignUp.isEnabled = true
                    }
                }
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                when {
                    text.isNullOrEmpty() -> {
                        etPassword.error = "This field cannot be blank"
                        btnSignUp.isEnabled = false
                    }
                    text.length < 6 -> {
                        etPassword.error = "This field must be at least 6 characters in length"
                        btnSignUp.isEnabled = false
                    }
                    else -> {
                        password = text.toString()
                        etPassword.error = null
                        btnSignUp.isEnabled = true
                    }
                }
            }
            etConfirmPassword.doOnTextChanged { text, _, _, _ ->
                when {
                    text.isNullOrEmpty() -> {
                        etConfirmPassword.error = "This field cannot be blank"
                        btnSignUp.isEnabled = false
                    }
                    text.toString() != password -> {
                        etConfirmPassword.error =  "Password don't match"
                        btnSignUp.isEnabled = false
                    }
                    else -> {
                        etConfirmPassword.error = null
                        btnSignUp.isEnabled = true
                    }
                }
            }
        }
    }
    private fun showProgressBar(isLoading: Boolean) {
        binding.signUpProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
    private fun registerUser(
        userName: String,
        phoneNumber: String,
        email: String,
        password: String
    ) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task1 ->
                if (task1.isSuccessful) {
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid
                    databaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(userId)
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap["userId"] = userId
                    hashMap["userName"] = userName
                    hashMap["userPhoneNumber"] = phoneNumber
                    hashMap["profileImage"] = ""

                    databaseReference.setValue(hashMap).addOnCompleteListener(this) { task2 ->
                        if (task2.isSuccessful) {
                            showProgressBar(false)
                            binding.etFullName.setText("")
                            binding.etEmail.setText("")
                            binding.etPassword.setText("")
                            binding.etPhoneNumber.setText("")
                            binding.etConfirmPassword.setText("")
                            Intent(this@SignUpActivity, HomeActivity::class.java).also {
                                it.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(it)
                            }
                        } else {
                            showProgressBar(false)
                            Toast.makeText(this, task2.exception?.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                }
            }
    }
}