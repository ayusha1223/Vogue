package com.example.vogueclothing.ui.Fragment


import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Button
import android.widget.ImageButton
import androidx.recyclerview.widget.RecyclerView
import com.example.vogueclothing.R
import com.example.vogueclothing.ui.Fragment.CartItem

class CartAdapter(
    private var cartItems: MutableList<CartItem>,
    private val onCartUpdated: (List<CartItem>) -> Unit
) : RecyclerView.Adapter<CartAdapter.CartViewHolder>() {

    class CartViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val itemName: TextView = view.findViewById(R.id.itemNameTextView)
        val itemPrice: TextView = view.findViewById(R.id.itemPriceTextView)
        val itemQuantity: TextView = view.findViewById(R.id.itemQuantityTextView)
        val increaseQuantity: ImageButton = view.findViewById(R.id.increaseButton)
        val decreaseQuantity: ImageButton = view.findViewById(R.id.decreaseButton)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(view)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        val item = cartItems[position]

        holder.itemName.text = item.name
        holder.itemPrice.text = "$${"%.2f".format(item.price * item.quantity)}" // ✅ Updated price
        holder.itemQuantity.text = "Qty: ${item.quantity}"

        // ✅ Increase quantity
        holder.increaseQuantity.setOnClickListener {
            item.quantity++
            notifyItemChanged(position)
            onCartUpdated(cartItems) // ✅ Update total price
        }

        // ✅ Decrease quantity
        holder.decreaseQuantity.setOnClickListener {
            if (item.quantity > 1) {
                item.quantity--
                notifyItemChanged(position)
                onCartUpdated(cartItems) // ✅ Update total price
            }
        }
    }

    override fun getItemCount() = cartItems.size

    fun updateCartItems(newItems: List<CartItem>) {
        cartItems.clear()
        cartItems.addAll(newItems)

        // ✅ Correct way to update RecyclerView without causing UI lag
        notifyDataSetChanged()

        onCartUpdated(cartItems)
    }
}
