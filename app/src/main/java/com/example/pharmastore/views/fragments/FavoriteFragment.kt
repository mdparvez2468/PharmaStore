package com.example.pharmastore.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmastore.R
import com.example.pharmastore.models.Product
import com.example.pharmastore.views.activities.MainActivity
import com.example.pharmastore.views.adapters.FavoriteAdapter
import com.example.pharmastore.views.adapters.ProductAdapter


class FavoriteFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var favoriteAdapter: FavoriteAdapter

    private lateinit var products: MutableList<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_favorite, container, false)

        recyclerView = view.findViewById(R.id.fragmentFavoriteMainRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireActivity(),2)
        products = ArrayList()
        favoriteAdapter = FavoriteAdapter(requireActivity(),products)
        recyclerView.adapter = favoriteAdapter

        MainActivity.mainViewModel?.getFavoriteProducts()

        MainActivity.mainViewModel?.favoriteProducts?.observe(requireActivity(), Observer {
            products.clear()
            for (item in it){
                products.add(item)
                favoriteAdapter.notifyDataSetChanged()
            }
        })

        return view
    }


}