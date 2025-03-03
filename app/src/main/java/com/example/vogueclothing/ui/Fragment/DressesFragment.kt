package com.example.vogueclothing.ui.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.Log
import com.example.vogueclothing.R
import com.example.vogueclothing.viewmodel.SharedViewModel
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DressesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_dresses, container, false)

        // Initialize image views and set click listeners
        val image1 = view.findViewById<ImageView>(R.id.image1)
        val image2 = view.findViewById<ImageView>(R.id.image2)
        val image3 = view.findViewById<ImageView>(R.id.image3)
        val image4 = view.findViewById<ImageView>(R.id.image4)
        val image5 = view.findViewById<ImageView>(R.id.image5)
        val image6 = view.findViewById<ImageView>(R.id.image6)
        val image7 = view.findViewById<ImageView>(R.id.image7)
        val image8 = view.findViewById<ImageView>(R.id.image8)

        // Set click listeners for all images
        image1.setOnClickListener { showBottomSheet("Mini", "$10") }
        image2.setOnClickListener { showBottomSheet("Tube", "$10") }
        image3.setOnClickListener { showBottomSheet("Pleated", "$5") }
        image4.setOnClickListener { showBottomSheet("Godget", "$15") }
        image5.setOnClickListener { showBottomSheet("Wrap", "$20") }
        image6.setOnClickListener { showBottomSheet("Bubble", "$82") }
        image7.setOnClickListener { showBottomSheet("Tulip", "$20") }
        image8.setOnClickListener { showBottomSheet("Circle", "$25") }

        return view
    }

    // Function to show a bottom sheet dialog
    @SuppressLint("MissingInflatedId")
    private fun showPaymentBottomSheet(itemName: String, itemPrice: String) {
        try {
            if (!isAdded) return  // Ensure the fragment is still attached

            // Inflate the bottom sheet layout
            val paymentBottomSheetView = layoutInflater.inflate(R.layout.payment_bottom_sheet_layout, null, false)

            // Initialize views
            val itemNameTextView = paymentBottomSheetView.findViewById<TextView>(R.id.itemname)
            val itemPriceTextView = paymentBottomSheetView.findViewById<TextView>(R.id.itemprice)
            val cashOnDeliveryButton = paymentBottomSheetView.findViewById<Button>(R.id.cashOnDeliveryOption)
            val confirmButton = paymentBottomSheetView.findViewById<Button>(R.id.confirmButton)

            // Set item details
            itemNameTextView.text = itemName
            itemPriceTextView.text = itemPrice

            // Create the BottomSheetDialog
            val paymentBottomSheetDialog = BottomSheetDialog(requireContext())
            paymentBottomSheetDialog.setContentView(paymentBottomSheetView)
            paymentBottomSheetDialog.show()

            // Handle Cash on Delivery button click
            cashOnDeliveryButton.setOnClickListener {
                MaterialAlertDialogBuilder(requireContext())
                    .setTitle("Confirm Order")
                    .setMessage("Are you sure you want to place this order with Cash on Delivery?")
                    .setPositiveButton("Confirm") { _, _ ->
                        paymentBottomSheetDialog.dismiss()
                        Toast.makeText(requireContext(), "Order confirmed for $itemName", Toast.LENGTH_SHORT).show()
                    }
                    .setNegativeButton("Cancel") { dialog, _ -> dialog.dismiss() }
                    .show()
            }

            // Handle Confirm button click (optional)
            confirmButton.setOnClickListener {
                paymentBottomSheetDialog.dismiss()
            }

        } catch (e: Exception) {
            Log.e("PaymentSheet", "Error showing payment bottom sheet", e)
        }
    }
    private fun addToCart(itemName: String, itemPrice: String) {
        // Get the SharedViewModel
        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        val cleanedPrice = itemPrice.replace("$", "").trim()

        // Create a CartItem and add it to the cart
        val cartItem = CartItem(
            name = itemName,
            price = itemPrice.toDoubleOrNull() ?: 0.0, // Convert price safely
            quantity = 1, // Default value

        )
        sharedViewModel.addToCart(cartItem)
        // Show a toast message
        android.widget.Toast.makeText(requireContext(), "$itemName added to cart", android.widget.Toast.LENGTH_SHORT).show()
    }
}
