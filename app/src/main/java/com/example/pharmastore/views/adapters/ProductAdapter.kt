package com.example.pharmastore.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pharmastore.R
import com.example.pharmastore.models.Product
import com.example.pharmastore.views.activities.MainActivity
import com.example.pharmastore.views.activities.ProductViewActivity

class ProductAdapter(
    private val context: Context,
    private val products: List<Product>
): RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {



    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        lateinit var nameTV: TextView
        lateinit var priceTV: TextView
        lateinit var mrpTV: TextView
        lateinit var imageIV: ImageView

        init {
            nameTV = itemView.findViewById(R.id.productSampleName)
            priceTV = itemView.findViewById(R.id.productSamplePrice)
            mrpTV = itemView.findViewById(R.id.productSampleMaxPrice)
            imageIV = itemView.findViewById(R.id.productSampleImage)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.product_saample,parent,false)

        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {


        val product = products.get(position)

        holder.nameTV.text = product.name
        holder.priceTV.text = product.price.toString()
        holder.mrpTV.text = product.mrp.toString()

        Glide.with(context).load(product.image).into(holder.imageIV)

        holder.itemView.setOnClickListener {
            MainActivity.mainViewModel?.getProductForView(product.id)
            it.context.startActivity(Intent(context,ProductViewActivity::class.java))
        }


    }

    override fun getItemCount(): Int {
        return products.size
    }

}