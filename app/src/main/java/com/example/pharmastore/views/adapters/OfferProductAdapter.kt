package com.example.pharmastore.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.pharmastore.R
import com.example.pharmastore.models.OfferProduct

class OfferProductAdapter(
    private val context: Context,
    private val products: List<OfferProduct>
): RecyclerView.Adapter<OfferProductAdapter.ProductViewHolder>() {



    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {



        lateinit var imageIV: ImageView

        init {
            imageIV = itemView.findViewById(R.id.offerProductSampleIV)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.offer_product_sample,parent,false)

        return ProductViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val offerProduct = products.get(position)

       holder.imageIV.setImageResource(offerProduct.image)

        holder.itemView.setOnClickListener {

        }
    }

    override fun getItemCount(): Int {
        return products.size
    }


}