package com.example.pharmastore.views.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pharmastore.R
import com.example.pharmastore.models.Product
import com.example.pharmastore.views.activities.MainActivity
import com.example.pharmastore.views.activities.ProductViewActivity
import com.google.firebase.database.FirebaseDatabase

class CartAdapter (
    private val context: Context,
    private val products: List<Product>
): RecyclerView.Adapter<CartAdapter.ProductViewHolder>() {



    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        lateinit var nameTV: TextView
        lateinit var priceTV: TextView
        lateinit var mrpTV: TextView
        lateinit var imageIV: ImageView
        lateinit var deleteImageButton: ImageButton

        init {
            nameTV = itemView.findViewById(R.id.cartSampleName)
            priceTV = itemView.findViewById(R.id.cartSamplePrice)
            mrpTV = itemView.findViewById(R.id.cartSampleMaxPrice)
            imageIV = itemView.findViewById(R.id.cartSampleImage)
            deleteImageButton = itemView.findViewById(R.id.cartSampleDeleteBTN)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_sample,parent,false)

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
            it.context.startActivity(Intent(context, ProductViewActivity::class.java))

        }

        holder.deleteImageButton.setOnClickListener {
            FirebaseDatabase.getInstance().getReference("cart").child("+8801736194336").child(product.id).setValue(null).addOnSuccessListener {
                Toast.makeText(context,"Removed From Cart", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(context,"Remove Failed From Cart", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }


}