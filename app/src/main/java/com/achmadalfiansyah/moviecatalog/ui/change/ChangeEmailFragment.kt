package com.achmadalfiansyah.moviecatalog.ui.change

import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import com.achmadalfiansyah.moviecatalog.databinding.FragmentChangeEmailBinding
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException

class ChangeEmailFragment : Fragment() {
    private var _binding: FragmentChangeEmailBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth : FirebaseAuth
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentChangeEmailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        auth = FirebaseAuth.getInstance()
        inputFormValidation()
        buttonFunction()

    }
    private fun buttonFunction(){
        val user = auth.currentUser
        with(binding){
            layoutPassword.visibility = View.VISIBLE
            layoutEmail.visibility = View.GONE
            btnAuth.setOnClickListener{
                val password = etPassword.text.toString().trim()
                user?.let {
                    val userCredential = EmailAuthProvider.getCredential(it.email!!,password)
                    it.reauthenticate(userCredential).addOnCompleteListener {
                            task ->
                        when {
                            task.isSuccessful -> {
                                layoutPassword.visibility = View.GONE
                                layoutEmail.visibility = View.VISIBLE
                            }
                            task.exception is FirebaseAuthInvalidCredentialsException -> {
                                etPassword.error = "Password salah"
                                etPassword.requestFocus()
                            }
                            else -> {
                                Toast.makeText(activity,"${task.exception?.message}", Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            }
            btnUpdate.setOnClickListener { view ->
                val email = etEmail.text.toString().trim()
                user?.let {
                    user.updateEmail(email).addOnCompleteListener {
                        if (it.isSuccessful){
                            val actionEmailUpdated = ChangeEmailFragmentDirections.actionEmailUpdated()
                            Navigation.findNavController(view).navigate(actionEmailUpdated)
                        }else {
                            Toast.makeText(activity,"${it.exception?.message}",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }

        }
    }
    private fun inputFormValidation() {
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
            etEmail.doOnTextChanged { text, _, _, _ ->
                when {
                    text.isNullOrEmpty() -> {
                        etEmail.error = "This field cannot be blank"
                        btnUpdate.isEnabled = false
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(text).matches() -> {
                        etEmail.error = "Please enter a valid email address"
                        btnUpdate.isEnabled = false
                    }
                    else -> {
                        etEmail.error = null
                        btnUpdate.isEnabled = true
                    }
                }
            }
        }
    }



}