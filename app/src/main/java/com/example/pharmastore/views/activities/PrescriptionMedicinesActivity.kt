package com.example.pharmastore.views.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmastore.R
import com.example.pharmastore.models.Product
import com.example.pharmastore.views.adapters.ProductAdapter

class PrescriptionMedicinesActivity : AppCompatActivity() {

    lateinit var textView: TextView


    lateinit var recyclerView: RecyclerView

    private lateinit var productAdapter: ProductAdapter

    private lateinit var products: MutableList<Product>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription_medicines)

        textView = findViewById(R.id.textViewPrescriptionMedicinesActivity)


        recyclerView = findViewById(R.id.rvPrescriptionMedicinesActivity)
        recyclerView.layoutManager = GridLayoutManager(this,3)
        products = ArrayList()
        productAdapter = ProductAdapter(this,products)
        recyclerView.adapter = productAdapter

        val value = intent.getStringExtra("text")

        textView.text = value.toString()

        MainActivity.mainViewModel?.products?.observe(this) {
            products.clear()
            for (item in it) {
                if (item.name.lowercase().contains(value.toString().lowercase())){
                    products.add(item)
                    productAdapter.notifyDataSetChanged()
                }

            }
        }

    }
}