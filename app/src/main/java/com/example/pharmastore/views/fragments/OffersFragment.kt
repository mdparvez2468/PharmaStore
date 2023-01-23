package com.example.pharmastore.views.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmastore.R
import com.example.pharmastore.models.OfferProduct
import com.example.pharmastore.models.Product
import com.example.pharmastore.views.adapters.CartAdapter
import com.example.pharmastore.views.adapters.OfferProductAdapter


class OffersFragment : Fragment() {

    lateinit var recyclerView: RecyclerView
    private lateinit var offerProductAdapter: OfferProductAdapter

    private lateinit var products: MutableList<OfferProduct>


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_offers, container, false)


        recyclerView = view.findViewById(R.id.fragmentOfferMainRecyclerView)
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        products = ArrayList()

        products.add(OfferProduct(R.drawable.slider_5))
        products.add(OfferProduct(R.drawable.slider_1))
        products.add(OfferProduct(R.drawable.slider_2))
        products.add(OfferProduct(R.drawable.slider_3))
        products.add(OfferProduct(R.drawable.slider_4))
        products.add(OfferProduct(R.drawable.slider_5))
        products.add(OfferProduct(R.drawable.slider_1))
        products.add(OfferProduct(R.drawable.slider_2))
        products.add(OfferProduct(R.drawable.slider_3))
        products.add(OfferProduct(R.drawable.slider_4))


         offerProductAdapter = OfferProductAdapter(requireActivity(),products)
        recyclerView.adapter = offerProductAdapter

        return view
    }


}