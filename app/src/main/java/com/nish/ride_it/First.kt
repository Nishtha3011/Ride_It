package com.nish.ride_it

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class First : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_first)
        val loginBtn = findViewById<Button>(R.id.loginButton)

        loginBtn.setOnClickListener {
            startActivity(Intent(this, Login::class.java))
        }
        val signInBtn=findViewById<Button>(R.id.signInButton)
        signInBtn.setOnClickListener {
            startActivity(Intent(this, SignIn::class.java))
        }
    }
}