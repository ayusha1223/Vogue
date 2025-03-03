package com.example.vogueclothing.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

import com.example.vogueclothing.Model.CartModel
import com.example.vogueclothing.repository.CartRepository

class CartViewModel(private val repo: CartRepository) : ViewModel() {

    private val _cartItems = MutableLiveData<List<CartModel>?>() // Make it nullable
    val cartItems: MutableLiveData<List<CartModel>?> get() = _cartItems // Make it nullable

    private val _loading = MutableLiveData<Boolean>()
    val loading: MutableLiveData<Boolean> get() = _loading

    fun addToCart(cartModel: CartModel, callback: (Boolean, String) -> Unit) {
        repo.addToCart(cartModel, callback)
    }

    fun removeFromCart(cartId: String, callback: (Boolean, String) -> Unit) {
        repo.removeFromCart(cartId, callback)
    }

    fun updateCartItem(cartId: String, quantity: Int, callback: (Boolean, String) -> Unit) {
        repo.updateCartItem(cartId, quantity, callback)
    }

    fun getCartItems(userId: String) {
        _loading.value = true
        repo.getCartItems(userId) { cartItems, success, message ->
            if (success) {
                _cartItems.value = cartItems // This can now accept null
            }
            _loading.value = false
        }
    }
}