package com.example.pharmastore.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pharmastore.models.Cart
import com.example.pharmastore.models.Product
import com.example.pharmastore.views.PharmaStoreApplication
import com.google.firebase.database.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.launch

class MainViewModel: ViewModel() {

    private val  _products = MutableLiveData<List<Product>>()
    val products: LiveData<List<Product>> get() = _products

    private val  _favoriteProducts = MutableLiveData<List<Product>>()
    val favoriteProducts: LiveData<List<Product>> get() = _favoriteProducts

    private val  _cartProducts = MutableLiveData<List<Cart>>()
    val cartProducts: LiveData<List<Cart>> get() = _cartProducts





    private val _productForView = MutableLiveData<Product>()
    val productForView: LiveData<Product> get() = _productForView

    private val postListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            var list = mutableListOf<Product>()
            for (dataSnapshot in snapshot.children){
                list.add(dataSnapshot.getValue(Product::class.java)!!)
            }
            _products.postValue(list)
        }
        override fun onCancelled(databaseError: DatabaseError) {

        }
    }

    private val favoriteProductListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            var list = mutableListOf<Product>()
            for (dataSnapshot in snapshot.children){
                list.add(dataSnapshot.getValue(Product::class.java)!!)
            }
            _favoriteProducts.postValue(list)
        }
        override fun onCancelled(databaseError: DatabaseError) {

        }
    }

     private val cartProductListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            var list = mutableListOf<Cart>()
            for (dataSnapshot in snapshot.children){
                list.add(dataSnapshot.getValue(Cart::class.java)!!)
            }
            _cartProducts.postValue(list)
        }
        override fun onCancelled(databaseError: DatabaseError) {

        }
    }





    private val productForViewListener = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            val product = snapshot.getValue(Product::class.java)
            _productForView.postValue(product!!)
        }
        override fun onCancelled(databaseError: DatabaseError) {

        }
    }

    fun getProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            PharmaStoreApplication.databaseReference?.child("products")?.addValueEventListener(postListener)
        }
    }

    fun getFavoriteProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            PharmaStoreApplication.databaseReference?.child("favorite")?.child("+8801736194336")?.addValueEventListener(favoriteProductListener)
        }
    }

    fun getCartProducts(){
        viewModelScope.launch(Dispatchers.IO) {
            PharmaStoreApplication.databaseReference?.child("cart")?.child("+8801736194336")?.addValueEventListener(cartProductListener)
        }
    }



    fun getProductForView(id: String){
        viewModelScope.launch {
            PharmaStoreApplication.databaseReference?.child("products")?.child(id)?.addListenerForSingleValueEvent(productForViewListener)
        }

    }



}