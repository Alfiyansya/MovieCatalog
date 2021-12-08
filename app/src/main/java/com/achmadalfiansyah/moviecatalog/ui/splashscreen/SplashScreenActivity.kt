package com.achmadalfiansyah.moviecatalog.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.achmadalfiansyah.moviecatalog.ui.home.HomeActivity
import com.achmadalfiansyah.moviecatalog.databinding.ActivitySplashScreenBinding
import com.achmadalfiansyah.moviecatalog.ui.login.LoginActivity
import com.google.firebase.auth.FirebaseAuth

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        auth = FirebaseAuth.getInstance()
        supportActionBar?.hide()
        moveToMainActivity()
    }
    private fun moveToMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            if (auth.currentUser != null) {
                Intent(this@SplashScreenActivity, HomeActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }else{
                Intent(this@SplashScreenActivity,LoginActivity::class.java).also {
                    it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(it)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            }
        }, DELAY_MILLIS)
    }

    companion object {
        private const val DELAY_MILLIS = 3000L
    }
}