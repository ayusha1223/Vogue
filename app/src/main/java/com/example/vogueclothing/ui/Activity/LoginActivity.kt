package com.example.vogueclothing.ui.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.vogueclothing.R
import com.example.vogueclothing.ui.Activity.DashboardActivity
import com.example.vogueclothing.ui.Activity.ForgotPasswordActivity

class LoginActivity : AppCompatActivity() {

    private lateinit var usernameEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var forgotPasswordTextView: TextView
    private lateinit var loginButton: Button
    private lateinit var signUpButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login) // Make sure this corresponds to the correct XML file

        // Initialize views
        usernameEditText = findViewById(R.id.username)
        passwordEditText = findViewById(R.id.password)
        forgotPasswordTextView = findViewById(R.id.forgotPassword)
        loginButton = findViewById(R.id.loginButton)
        signUpButton = findViewById(R.id.signUpButton)

        // Login Button click listener
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Simple validation
            if (username.isNotEmpty() && password.isNotEmpty()) {
                if (username == "admin" && password == "password") {
                    // Go to the Dashboard activity (replace with actual activity)
                    val intent = Intent(this, DashboardActivity::class.java)
                    startActivity(intent)
                    finish() // Close the LoginActivity
                } else {
                    // Show an error message (you can use Toast or Snackbar)
                    showError("Invalid credentials")
                }
            } else {
                showError("Please enter both username and password")
            }
        }

        // Sign-up Button click listener
        signUpButton.setOnClickListener {
            // Redirect to sign-up activity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Forgot password click listener
        forgotPasswordTextView.setOnClickListener {
            // Redirect to forgot password activity (you can implement this)
            val intent = Intent(this, ForgotPasswordActivity::class.java)
            startActivity(intent)
        }
    }

    // Show an error message (Toast)
    private fun showError(message: String) {
        // You can use Toast or Snackbar to show the error message
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show()
    }
}
