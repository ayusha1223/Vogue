package com.example.vogueclothing.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.vogueclothing.R
import com.example.vogueclothing.ui.Fragment.CartItem

class CartAdapter(
    private var cartItems: MutableList<CartItem>,
    private val onCartUpdated: (List<CartItem>) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    inner class CartViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val itemNameTextView: TextView = itemView.findViewById(R.id.itemNameTextView)
        val itemPriceTextView: TextView = itemView.findViewById(R.id.itemPriceTextView)
        val itemQuantityTextView: TextView = itemView.findViewById(R.id.itemQuantityTextView)
        val increaseButton: ImageButton = itemView.findViewById(R.id.increaseButton)
        val decreaseButton: ImageButton = itemView.findViewById(R.id.decreaseButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val cartItem = cartItems[position]

        holder.itemNameTextView.text = cartItem.name
        holder.itemPriceTextView.text = "$${"%.2f".format(cartItem.price)}"  // ✅ Ensure price format
        holder.itemQuantityTextView.text = cartItem.quantity.toString()

        holder.increaseButton.setOnClickListener {
            cartItem.quantity++
            notifyItemChanged(position)
            onCartUpdated(cartItems)  // ✅ Ensure ViewModel updates
        }

        holder.decreaseButton.setOnClickListener {
            if (cartItem.quantity > 1) {
                cartItem.quantity--
                notifyItemChanged(position)
                onCartUpdated(cartItems)  // ✅ Ensure ViewModel updates
            }
        }
    }

    override fun getItemCount(): Int = cartItems.size

    fun updateCartItems(newCartItems: MutableList<CartItem>) {
        cartItems = newCartItems
        notifyDataSetChanged()
    }
}
