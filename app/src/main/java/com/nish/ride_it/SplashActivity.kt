package com.nish.ride_it

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Delay for 2 seconds (2000 milliseconds), then start LoginActivity or MainActivity
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, First::class.java) // or MainActivity::class.java
            startActivity(intent)
            finish() // So user can’t go back to splash screen
        }, 750)
    }
}