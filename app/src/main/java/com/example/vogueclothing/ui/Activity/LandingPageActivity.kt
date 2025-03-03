package com.example.vogueclothing.ui.Activity


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vogueclothing.R
import com.example.vogueclothing.ui.Activity.LoginActivity
import com.example.vogueclothing.ui.Activity.SignUpActivity

class LandingPageActivity : AppCompatActivity() {

    private lateinit var signUpButton: Button
    private lateinit var loginText: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_landing_page) // Make sure this XML corresponds to the updated one

        // Initialize views
        signUpButton = findViewById(R.id.signUpButton)
        loginText = findViewById(R.id.loginText)

        // Sign Up Button click listener
        signUpButton.setOnClickListener {
            // Navigate to Sign Up Activity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

//         Login Button click listener
        loginText.setOnClickListener {
            // Navigate to Login Activity
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
        }

    }
}
