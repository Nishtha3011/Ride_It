package com.nish.ride_it

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.button.MaterialButton

class GetYourCycle : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_get_your_cycle2)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        val upiButton: MaterialButton = findViewById(R.id.upiButton)

        // Set OnClickListener on the UPI button
        upiButton.setOnClickListener {
            // Navigate to DropCycleActivity when button is clicked
            Log.d("UPIButton", "UPI button clicked, navigating to DropCycleActivity")
            val intent = Intent(this, DropCycle::class.java)
            startActivity(intent)
        }
    }
}
