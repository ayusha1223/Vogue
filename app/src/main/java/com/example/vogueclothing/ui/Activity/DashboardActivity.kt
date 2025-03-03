package com.example.vogueclothing.ui.Activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.vogueclothing.R
import com.example.vogueclothing.ui.Fragment.CartFragment
import com.example.vogueclothing.ui.Fragment.CategoryFragment
import com.example.vogueclothing.ui.Fragment.DashboardFragment
import com.example.vogueclothing.ui.Fragment.ProfileFragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class DashboardActivity : AppCompatActivity() {

    private lateinit var bottomNavigationView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        // Initialize bottom navigation view
        bottomNavigationView = findViewById(R.id.bottomNavigationView)

        // Set default fragment (Dashboard) when the activity is created
        loadFragment(DashboardFragment())

        // Set up bottom navigation item selection listener
        bottomNavigationView.setOnNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_home -> {
                    loadFragment(DashboardFragment())  // Show Dashboard Fragment
                    true
                }
                R.id.nav_category -> {
                    loadFragment(CategoryFragment())  // Show Category Fragment
                    true
                }
                R.id.nav_cart -> {
                    loadFragment(CartFragment())  // Show Cart Fragment
                    true
                }
                R.id.nav_profile -> {
                    loadFragment(ProfileFragment())  // Show Profile Fragment
                    true
                }
                else -> false
            }
        }
    }

    // Helper method to load a fragment dynamically
    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.fragmentContainer, fragment)
            .commit()
    }
}