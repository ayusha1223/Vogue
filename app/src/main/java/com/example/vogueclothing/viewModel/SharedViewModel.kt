package com.example.vogueclothing.viewmodel  // ✅ Make sure this matches your package

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.vogueclothing.ui.Fragment.CartItem

class SharedViewModel : ViewModel() {

    private val cartItemsLiveData = MutableLiveData<MutableList<CartItem>>()

    init {
        cartItemsLiveData.value = mutableListOf() // ✅ Initialize LiveData
    }

    fun getCartItems(): LiveData<MutableList<CartItem>> {
        return cartItemsLiveData
    }

    fun addToCart(item: CartItem) {
        val currentCart = cartItemsLiveData.value ?: mutableListOf()

        val existingItem = currentCart.find { it.name == item.name }
        if (existingItem != null) {
            existingItem.quantity += item.quantity
        } else {
            currentCart.add(item)
        }

        cartItemsLiveData.value = currentCart // ✅ Use `value` instead of `postValue()` to update immediately
    }

    fun updateCart(cartItems: List<CartItem>) {
        cartItemsLiveData.postValue(cartItems.toMutableList()) // ✅ Prevents UI lag
    }
}
