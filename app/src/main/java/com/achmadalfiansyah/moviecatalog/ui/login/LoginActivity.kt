package com.achmadalfiansyah.moviecatalog.ui.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.doOnTextChanged
import com.achmadalfiansyah.moviecatalog.R
import com.achmadalfiansyah.moviecatalog.databinding.ActivityLoginBinding
import com.achmadalfiansyah.moviecatalog.ui.signup.SignUpActivity
import com.achmadalfiansyah.moviecatalog.ui.home.HomeActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.flow.*

class LoginActivity : AppCompatActivity() {
    private var _binding: ActivityLoginBinding? = null
    private val binding get() = _binding!!
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    private lateinit var googleSignInClient: GoogleSignInClient

    //    @ExperimentalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        _binding = ActivityLoginBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_ids))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(this, gso)
        binding.btnSignInWithGoogle.setOnClickListener {
            signInWithGoogle()

        }

        with(binding){
            etEmail.doOnTextChanged{ text, _, _, _ ->
                when {
                    text?.isEmpty()!! -> {
                        etEmail.error = "This field cannot be blank"
                        btnSignIn.isEnabled = false
                    }
                    !Patterns.EMAIL_ADDRESS.matcher(text).matches() -> {
                        etEmail.error = "Please enter a valid email address"

                        btnSignIn.isEnabled = false
                    }
                    else -> {
                        etEmail.error = null

                        btnSignIn.isEnabled = true
                    }
                }
            }
            etPassword.doOnTextChanged { text, _, _, _ ->
                when {
                    text?.isEmpty()!! -> {
                        etPassword.error = "This field cannot be blank"
                        btnSignIn.isEnabled = false
                    }
                    text.length < 6 -> {
                        etPassword.error = "This field must be at least 6 characters in length"
                        btnSignIn.isEnabled = false
                    }
                    else -> {
                        etPassword.error = null
                        btnSignIn.isEnabled = true
                    }
                }
            }
            forgotPassword.setOnClickListener {
                Intent(this@LoginActivity,ForgotPasswordActivity::class.java).also {
                    startActivity(it)
                }
            }
            btnSignIn.setOnClickListener {
                val email = binding.etEmail.text.toString().trim()
                val password = binding.etPassword.text.toString().trim()
                showProgressBar(true)
                loginUser(email, password)
            }

            signUp.setOnClickListener {
                Intent(this@LoginActivity, SignUpActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)!!
                Log.d(TAG, "firebaseAuthWithGoogle:" + account.id)
                firebaseAuthWithGoogle(account.idToken!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e)
            }
        }
    }

    private fun signInWithGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d(TAG, "signInWithCredential:success")
                    val user: FirebaseUser? = auth.currentUser
                    val userId: String = user!!.uid
                    databaseReference =
                        FirebaseDatabase.getInstance().getReference("Users").child(userId)
                    val hashMap: HashMap<String, String> = HashMap()
                    hashMap["userId"] = userId
                    hashMap["userName"] = user.displayName.toString()
                    hashMap["userPhoneNumber"] = user.phoneNumber.toString()
                    hashMap["profileImage"] = ""

                    databaseReference.setValue(hashMap).addOnCompleteListener(this) { task2 ->
                        if (task2.isSuccessful) {
                            binding.etEmail.setText("")
                            binding.etPassword.setText("")
                            binding.etEmail.error = null
                            binding.etPassword.error = null
                            Intent(this@LoginActivity, HomeActivity::class.java).also {
                                it.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                startActivity(it)
                            }
                        } else {
                            Toast.makeText(this, task2.exception?.message, Toast.LENGTH_SHORT)
                                .show()
                        }

                    }
                } else {
                    Log.w(TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) {
                if (it.isSuccessful) {
                    showProgressBar(false)
                    Intent(this, HomeActivity::class.java).also { intent ->
                        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                        startActivity(intent)
                    }
                } else {
                    showProgressBar(false)
                    Toast.makeText(this, "${it.exception?.message}", Toast.LENGTH_SHORT).show()
                }
            }
    }

    companion object {
        private const val RC_SIGN_IN = 120
        private const val TAG = "GoogleActivity"
    }

    private fun showProgressBar(isLoading: Boolean) {
        binding.loginProgressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }

}