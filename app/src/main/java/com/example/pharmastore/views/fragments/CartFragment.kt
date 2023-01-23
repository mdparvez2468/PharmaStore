package com.example.pharmastore.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmastore.R
import com.example.pharmastore.models.Cart
import com.example.pharmastore.models.Product
import com.example.pharmastore.views.activities.MainActivity
import com.example.pharmastore.views.adapters.CartAdapter
import com.example.pharmastore.views.adapters.FavoriteAdapter


class CartFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var cartAdapter: CartAdapter

    private lateinit var products: MutableList<Cart>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_cart, container, false)


        recyclerView = view.findViewById(R.id.fragmentCartMainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        products = ArrayList()
        cartAdapter = CartAdapter(requireActivity(),products)
        recyclerView.adapter = cartAdapter

        MainActivity.mainViewModel?.getCartProducts()

        MainActivity.mainViewModel?.cartProducts?.observe(requireActivity(), Observer {
            products.clear()
            for (item in it){
                products.add(item)
                cartAdapter.notifyDataSetChanged()
            }
        })


        return view
    }

}