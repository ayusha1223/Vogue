package com.example.vogueclothing.ui.Fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.vogueclothing.R

class CategoryFragment : Fragment() {

    // Declare buttons
    private lateinit var btnPant: Button
    private lateinit var btnShort: Button
    private lateinit var btnTees: Button
    private lateinit var btnDresses: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_category, container, false)

        // Initialize buttons
        btnPant = view.findViewById(R.id.btn_pant)
        btnShort = view.findViewById(R.id.btn_short)
        btnTees = view.findViewById(R.id.btn_tees)
        btnDresses = view.findViewById(R.id.btn_dresses)

        // Set click listeners for buttons
        btnPant.setOnClickListener {
            // Open PantFragment when Pant button is clicked
            openFragment(PantFragment())
        }

        btnShort.setOnClickListener {
            // Open ShortsFragment when Short button is clicked
            openFragment(ShortsFragment())
        }

        btnTees.setOnClickListener {
            // Open TeesFragment when Tees button is clicked
            openFragment(TeesFragment())
        }

        btnDresses.setOnClickListener {
            // Open DressesFragment when Dresses button is clicked
            openFragment(DressesFragment())
        }
        openFragment(PantFragment())

        return view
    }

    // Helper method to load a fragment dynamically
    private fun openFragment(fragment: Fragment) {
        val fragmentManager: FragmentManager = childFragmentManager
        val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()

        // Replace the content of fragmentContainer with the new fragment
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)

        // Add the transaction to the back stack (optional)
        fragmentTransaction.addToBackStack(null)

        // Commit the transaction
        fragmentTransaction.commit()
    }
}