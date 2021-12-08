package com.achmadalfiansyah.moviecatalog.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.achmadalfiansyah.moviecatalog.databinding.ActivityForgotPasswordBinding
import com.google.firebase.auth.FirebaseAuth

class ForgotPasswordActivity : AppCompatActivity() {
    private var _binding: ActivityForgotPasswordBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivityForgotPasswordBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        with(binding) {
            etEmail.doOnTextChanged { text, _, _, _ ->
                when {
                    text?.isEmpty()!! -> {
                        etEmail.error = "Email belum di isi"
                        btnResetPassword.isEnabled = false
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(text).matches() -> {
                        etEmail.error = "Email tidak valid"

                        btnResetPassword.isEnabled = false
                    }
                    else -> {
                        etEmail.error = null

                        btnResetPassword.isEnabled = true
                    }
                }
            }
            btnResetPassword.setOnClickListener {
                val email = etEmail.text.toString().trim()
                auth.sendPasswordResetEmail(email).addOnCompleteListener { task1 ->
                    if (task1.isSuccessful) {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "Check your email to reset password",
                            Toast.LENGTH_SHORT
                        ).show()
                        Intent(
                            this@ForgotPasswordActivity,
                            LoginActivity::class.java
                        ).also { intent ->
                            intent.flags =
                                Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                            startActivity(intent)
                        }
                    } else {
                        Toast.makeText(
                            this@ForgotPasswordActivity,
                            "${task1.exception?.message}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}