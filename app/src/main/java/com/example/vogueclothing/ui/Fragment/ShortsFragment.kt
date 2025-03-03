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

class ShortsFragment: Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_shorts, container, false)

        // Initialize views
        val image1 = view.findViewById<ImageView>(R.id.image1)
        val image2 = view.findViewById<ImageView>(R.id.image2)
        val image3 = view.findViewById<ImageView>(R.id.image3)
        val image4 = view.findViewById<ImageView>(R.id.image4)
        val image5 = view.findViewById<ImageView>(R.id.image5)
        val image6 = view.findViewById<ImageView>(R.id.image6)
        val image7 = view.findViewById<ImageView>(R.id.image7)
        val image8 = view.findViewById<ImageView>(R.id.image8)

        // Set click listeners for all images
        image1.setOnClickListener { showBottomSheet("Bombay", "$50") }
        image2.setOnClickListener { showBottomSheet("Silk", "$60") }
        image3.setOnClickListener { showBottomSheet("Wrap", "$70") }
        image4.setOnClickListener { showBottomSheet("Globe", "$80") }
        image5.setOnClickListener { showBottomSheet("Tank", "$90") }
        image6.setOnClickListener { showBottomSheet("Choker", "$100") }
        image7.setOnClickListener { showBottomSheet("BWrap top", "$110") }
        image8.setOnClickListener { showBottomSheet("Cami", "$120") }

        return view
    }

    // Function to show a bottom sheet dialog with Buy Now and Add to Cart options
    private fun showBottomSheet(itemName: String, itemPrice: String) {
        // Inflate the bottom sheet layout
        val bottomSheetView = layoutInflater.inflate(R.layout.bottom_sheet_layout, null)

        // Initialize views in the bottom sheet
        val itemNameTextView = bottomSheetView.findViewById<TextView>(R.id.itemname)
        val itemPriceTextView = bottomSheetView.findViewById<TextView>(R.id.itemprice)
        val buyNowButton = bottomSheetView.findViewById<TextView>(R.id.buynowButton)
        val addToCartButton = bottomSheetView.findViewById<TextView>(R.id.addTocartButton)

        // Set item details
        itemNameTextView.text = itemName
        itemPriceTextView.text = itemPrice

        // Create and show the bottom sheet dialog
        val bottomSheetDialog = BottomSheetDialog(requireContext())
        bottomSheetDialog.setContentView(bottomSheetView)
        bottomSheetDialog.show()

        // Handle Buy Now button click
        buyNowButton.setOnClickListener {
            bottomSheetDialog.dismiss()
            showPaymentBottomSheet(itemName, itemPrice) // Show payment options
        }

        // Handle Add to Cart button click
        addToCartButton.setOnClickListener {
            bottomSheetDialog.dismiss()
            addToCart(itemName, itemPrice) // Add item to cart
        }
    }

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

//            // Handle Confirm button click (optional)
//            confirmButton.setOnClickListener {
//                paymentBottomSheetDialog.dismiss()
//            }

        } catch (e: Exception) {
            Log.e("PaymentSheet", "Error showing payment bottom sheet", e)
        }
    }


    // Function to add item to cart
    private fun addToCart(itemName: String, itemPrice: String) {
        val sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        // Remove "$" and trim spaces before converting to Double
        val cleanedPrice = itemPrice.replace("$", "").trim()

        val cartItem = CartItem(
            name = itemName,
            price = cleanedPrice.toDoubleOrNull() ?: 0.0, // ✅ Fix price parsing
            quantity = 1
        )

        sharedViewModel.addToCart(cartItem)

        Toast.makeText(requireContext(), "$itemName added to cart", Toast.LENGTH_SHORT).show()
    }

}