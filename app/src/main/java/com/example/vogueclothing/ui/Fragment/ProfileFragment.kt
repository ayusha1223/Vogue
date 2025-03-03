package com.example.vogueclothing.ui.Fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.example.vogueclothing.R
import com.example.vogueclothing.ui.Activity.LoginActivity

class ProfileFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)

        // Initialize views
        val editProfile = view.findViewById<TextView>(R.id.edit_profile)
        val termsConditions = view.findViewById<TextView>(R.id.terms_conditions)
        val logout = view.findViewById<TextView>(R.id.logout)

        // Navigate to Edit Profile page
        editProfile.setOnClickListener {
            val fragmentTransaction: FragmentTransaction = requireActivity().supportFragmentManager.beginTransaction()
            fragmentTransaction.replace(R.id.fragmentContainer, EditProfileFragment())
            fragmentTransaction.addToBackStack(null) // Allows user to go back
            fragmentTransaction.commit()
        }

        // Logout and go to Login page
    logout.setOnClickListener {
        requireActivity().finish() // Close current activity and go back to login
    }

        return view
    }
}
