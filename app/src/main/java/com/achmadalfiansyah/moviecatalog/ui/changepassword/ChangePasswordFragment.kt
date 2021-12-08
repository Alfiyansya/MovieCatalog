package com.achmadalfiansyah.moviecatalog.ui.changepassword

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.achmadalfiansyah.moviecatalog.databinding.FragmentChangePasswordBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class ChangePasswordFragment : Fragment() {
    private var _binding: FragmentChangePasswordBinding? = null
    private val binding get() = _binding!!

    private lateinit var auth: FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangePasswordBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        val user = auth.currentUser
        inputFormValidation()
        with(binding) {
            layoutPassword.visibility = View.VISIBLE
            layoutNewPassword.visibility = View.GONE
            btnAuth.setOnClickListener {
                val password = etPassword.text.toString().trim()
                user?.let { task ->
                    val userCredential = EmailAuthProvider.getCredential(task.email!!, password)
                    task.reauthenticate(userCredential).addOnCompleteListener {
                        when {
                            it.isSuccessful -> {
                                layoutPassword.visibility = View.GONE
                                layoutNewPassword.visibility = View.VISIBLE
                            }
                            it.exception is FirebaseAuthInvalidCredentialsException -> {
                                etPassword.error = "Password Wrong"
                                etPassword.requestFocus()
                            }
                            else -> {
                                Toast.makeText(
                                    activity,
                                    "${it.exception?.message}",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            }
                        }
                    }
                }
            }
            btnUpdate.setOnClickListener { view ->
                val newPassword = etNewPassword.text.toString().trim()
                user?.let {
                    user.updatePassword(newPassword).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val actionPasswordChange =
                                ChangePasswordFragmentDirections.actionPasswordChanged()
                            Navigation.findNavController(view).navigate(actionPasswordChange)
                            Toast.makeText(
                                activity,
                                "Password has been changed successfully",
                                Toast.LENGTH_SHORT
                            )
                                .show()
                        } else {
                            Toast.makeText(activity, "${it.exception?.message}", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
                }
            }
        }

    }

    private fun inputFormValidation() {
        var password = ""
        with(binding) {
            etPassword.doOnTextChanged { text, _, _, _ ->
                when {
                    text.isNullOrEmpty() -> {
                        etPassword.error = "This field cannot be blank"
                        btnAuth.isEnabled = false
                    }
                    text.length < 6 -> {
                        etPassword.error = "This field must be at least 6 characters in length"
                        btnAuth.isEnabled = false
                    }
                    else -> {
                        etPassword.error = null
                        btnAuth.isEnabled = true
                    }
                }
            }
            etNewPassword.doOnTextChanged { text, _, _, _ ->
                when {
                    text.isNullOrEmpty() -> {
                        etPassword.error = "This field cannot be blank"
                        btnUpdate.isEnabled = false
                    }
                    text.length < 6 -> {
                        etPassword.error = "This field must be at least 6 characters in length"
                        btnUpdate.isEnabled = false
                    }
                    else -> {
                        password = text.toString()
                        etPassword.error = null
                        btnUpdate.isEnabled = true
                    }
                }
            }
            etNewPasswordConfirm.doOnTextChanged { text, _, _, _ ->
                when {
                    text.isNullOrEmpty() -> {
                        etNewPasswordConfirm.error = "This field cannot be blank"
                        btnUpdate.isEnabled = false
                    }
                    text.toString() != password -> {
                        etNewPasswordConfirm.error = "Password don't match"
                        btnUpdate.isEnabled = false
                    }
                    else -> {
                        etNewPasswordConfirm.error = null
                        btnUpdate.isEnabled = true
                    }
                }
            }
        }
    }
}