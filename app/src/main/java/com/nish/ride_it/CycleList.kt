package com.nish.ride_it

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class CycleList : AppCompatActivity() {

    private lateinit var welcomeTextView: TextView
    private lateinit var addButton: Button
    private lateinit var rentButton: Button
    private lateinit var rent1Button: Button
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cycle_list)

        welcomeTextView = findViewById(R.id.welcomeTextView)
        addButton = findViewById(R.id.addCycleButton)
        rentButton = findViewById(R.id.rentButton)
        rent1Button = findViewById(R.id.rent1Button)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        // Check if the user is logged in
        val uid = auth.currentUser?.uid
        if (uid != null) {
            firestore.collection("users")
                .document(uid)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        val name = document.getString("name")
                        welcomeTextView.text = "${name?.uppercase()}"
                    } else {
                        welcomeTextView.text = "USER"
                    }
                }
                .addOnFailureListener {
                    welcomeTextView.text = "USER"
                }
        } else {
            // If not logged in, redirect to login screen
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
            finish()  // Close this activity to prevent back navigation
            return  // Exit the method
        }

        // Navigate to AddCycle screen when add button is clicked
        addButton.setOnClickListener {
            val intent = Intent(this, AddCycle::class.java)
            startActivity(intent)
        }

        // Navigate to GetYourCycle screen when Rent button is clicked (1st button)
        rentButton.setOnClickListener {
            // Check if the user is logged in
            if (auth.currentUser != null) {

                val intent = Intent(this, GetYourCycle::class.java)
                startActivity(intent)
            } else {
                // If not logged in, redirect to login screen
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }

        // Navigate to GetYourCycle screen when Rent button is clicked (2nd button)
        rent1Button.setOnClickListener {
            // Check if the user is logged in
            if (auth.currentUser != null) {
                val intent = Intent(this, GetYourCycle::class.java)
                startActivity(intent)
            } else {
                // If not logged in, redirect to login screen
                val intent = Intent(this, Login::class.java)
                startActivity(intent)
                finish()
            }
        }
    }
}
