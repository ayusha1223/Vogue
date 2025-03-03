package com.example.vogueclothing.ui.Fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.vogueclothing.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.*

class EditProfileFragment : Fragment() {

    private lateinit var fullNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var phoneNumberEditText: EditText
    private lateinit var updateButton: Button

    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseRef: DatabaseReference
    private var currentUser: FirebaseUser? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_edit_profile, container, false)

        // Initialize Firebase Auth & Database
        auth = FirebaseAuth.getInstance()
        currentUser = auth.currentUser
        databaseRef = FirebaseDatabase.getInstance().getReference("Users")

        // Initialize Views
        fullNameEditText = view.findViewById(R.id.edit_full_name)
        emailEditText = view.findViewById(R.id.edit_email)
        passwordEditText = view.findViewById(R.id.edit_password)
        phoneNumberEditText = view.findViewById(R.id.edit_phone_number)
        updateButton = view.findViewById(R.id.button_update_profile)

        // Initialize SharedPreferences
        sharedPreferences = requireActivity().getSharedPreferences("UserProfile", Context.MODE_PRIVATE)

        // Load existing user details
        loadUserData()

        // Handle update button click
        updateButton.setOnClickListener {
            updateUserProfile()
        }

        return view
    }

    private fun loadUserData() {
        currentUser?.let { user ->
            emailEditText.setText(user.email)

            // Load user data from Firebase Realtime Database
            databaseRef.child(user.uid).addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        fullNameEditText.setText(snapshot.child("full_name").value.toString())
                        phoneNumberEditText.setText(snapshot.child("phone_number").value.toString())
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(requireContext(), "Failed to load user data.", Toast.LENGTH_SHORT).show()
                }
            })
        }
    }

    private fun updateUserProfile() {
        val fullName = fullNameEditText.text.toString().trim()
        val email = emailEditText.text.toString().trim()
        val password = passwordEditText.text.toString().trim()
        val phoneNumber = phoneNumberEditText.text.toString().trim()

        if (TextUtils.isEmpty(fullName) || TextUtils.isEmpty(email) || TextUtils.isEmpty(phoneNumber)) {
            Toast.makeText(requireContext(), "All fields must be filled!", Toast.LENGTH_SHORT).show()
            return
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            Toast.makeText(requireContext(), "Invalid Email Address!", Toast.LENGTH_SHORT).show()
            return
        }

        if (password.isNotEmpty() && password.length < 6) {
            Toast.makeText(requireContext(), "Password must be at least 6 characters!", Toast.LENGTH_SHORT).show()
            return
        }

        if (phoneNumber.length < 10) {
            Toast.makeText(requireContext(), "Invalid Phone Number!", Toast.LENGTH_SHORT).show()
            return
        }

        val user = currentUser
        if (user != null) {
            val userId = user.uid

            // Update Email in Firebase Authentication
            if (email != user.email) {
                user.updateEmail(email).addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(requireContext(), "Failed to update email!", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                }
            }

            // Update Password in Firebase Authentication
            if (password.isNotEmpty()) {
                user.updatePassword(password).addOnCompleteListener { task ->
                    if (!task.isSuccessful) {
                        Toast.makeText(requireContext(), "Failed to update password!", Toast.LENGTH_SHORT).show()
                        return@addOnCompleteListener
                    }
                }
            }

            // Update Profile in Firebase Realtime Database
            val userUpdates = mapOf(
                "full_name" to fullName,
                "email" to email,
                "phone_number" to phoneNumber
            )

            databaseRef.child(userId).updateChildren(userUpdates).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Save data locally
                    val editor = sharedPreferences.edit()
                    editor.putString("full_name", fullName)
                    editor.putString("email", email)
                    editor.putString("phone_number", phoneNumber)
                    editor.apply()

                    Toast.makeText(requireContext(), "Profile Updated Successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(requireContext(), "Failed to update profile!", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
}
