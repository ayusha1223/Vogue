package com.example.vogueclothing.ui.Fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.vogueclothing.R
import com.example.vogueclothing.viewmodel.SharedViewModel

class CartFragment : Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var cartAdapter: CartAdapter
    private lateinit var totalPriceText: TextView
    private lateinit var totalItemsText: TextView
    private lateinit var emptyCartMessage: TextView
    private lateinit var recyclerViewCart: RecyclerView

    @SuppressLint("MissingInflatedId")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = try {
            inflater.inflate(R.layout.fragment_cart2, container, false) // ✅ Ensure this file exists
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }

        recyclerViewCart = view.findViewById(R.id.recyclerviewCart)
        totalPriceText = view.findViewById(R.id.totalPriceTextView)
        totalItemsText = view.findViewById(R.id.totalItemsTextView)
        emptyCartMessage = view.findViewById(R.id.emptyCartText)

        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        cartAdapter = CartAdapter(mutableListOf()) { cartItems ->
            updateTotalPrice(cartItems)
            sharedViewModel.updateCart(cartItems) // ✅ Ensures LiveData updates correctly
        }
        recyclerViewCart.layoutManager = LinearLayoutManager(requireContext())
        recyclerViewCart.adapter = cartAdapter

        sharedViewModel.getCartItems().observe(viewLifecycleOwner, Observer { cartItems ->
            if (cartItems.isNullOrEmpty()) {
                updateCartVisibility(emptyList())
            } else {
                recyclerViewCart.post {
                    cartAdapter.updateCartItems(cartItems.toMutableList()) // ✅ Ensures smooth UI updates
                    updateTotalPrice(cartItems)
                    updateCartVisibility(cartItems)
                }
            }
        })

        return view
    }

    private fun updateTotalPrice(cartItems: List<CartItem>) {
        val totalPrice = cartItems.sumOf { it.price * it.quantity }
        val totalItems = cartItems.sumOf { it.quantity }

        totalPriceText.text = "Total: $${"%.2f".format(totalPrice)}"
        totalItemsText.text = "Items: $totalItems"

        // ✅ Update ViewModel with new prices
        sharedViewModel.updateCart(cartItems)
    }

    private fun updateCartVisibility(cartItems: List<CartItem>) {
        val isEmpty = cartItems.isEmpty()
        recyclerViewCart.visibility = if (isEmpty) View.GONE else View.VISIBLE
        emptyCartMessage.visibility = if (isEmpty) View.VISIBLE else View.GONE
    }
}
