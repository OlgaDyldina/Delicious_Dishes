package com.example.delicious_dishes.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.example.delicious_dishes.MainActivity
import com.example.delicious_dishes.R

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_screen)

        val homeIntent = Intent(this@SplashScreen, MainActivity::class.java)
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(homeIntent)
            finish()
        }, SPLASH_TIME_OUT.toLong())
        supportActionBar?.hide()
    }
    companion object {
        const val SPLASH_TIME_OUT = 2000
    }
}