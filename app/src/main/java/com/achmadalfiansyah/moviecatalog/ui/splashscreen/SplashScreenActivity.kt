package com.achmadalfiansyah.moviecatalog.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.achmadalfiansyah.moviecatalog.databinding.ActivitySplashScreenBinding
import com.achmadalfiansyah.moviecatalog.ui.home.HomeActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        moveToMainActivity()
    }

    private fun moveToMainActivity() {
        Handler(Looper.getMainLooper()).postDelayed({
            Intent(this@SplashScreenActivity, HomeActivity::class.java).also {
                it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(it)
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
            }
        }, DELAY_MILLIS)
    }

    companion object {
        private const val DELAY_MILLIS = 3000L
    }
}