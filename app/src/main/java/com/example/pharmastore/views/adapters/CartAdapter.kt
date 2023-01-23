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
import com.example.pharmastore.models.Cart
import com.example.pharmastore.models.Product
import com.example.pharmastore.views.activities.MainActivity
import com.example.pharmastore.views.activities.ProductViewActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class CartAdapter (
    private val context: Context,
    private val products: List<Cart>
): RecyclerView.Adapter<CartAdapter.ProductViewHolder>() {


    private var productId = ""


    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        lateinit var nameTV: TextView
        lateinit var imageIV: ImageView
        lateinit var deleteImageButton: ImageButton
        lateinit var cartPositiveBTN: ImageButton
        lateinit var cartNegativeBTN: ImageButton

        init {
            nameTV = itemView.findViewById(R.id.cartSampleName)
            imageIV = itemView.findViewById(R.id.cartSampleImage)
            deleteImageButton = itemView.findViewById(R.id.cartSampleDeleteBTN)
            cartPositiveBTN = itemView.findViewById(R.id.cartPositiveBTN)
            cartNegativeBTN = itemView.findViewById(R.id.cartNegativeBTN)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.cart_sample,parent,false)

        return ProductViewHolder(view)
    }


    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val product = products.get(position)

        holder.nameTV.text = "${product.name} Price: ${product.price}"

        Glide.with(context).load(product.image).into(holder.imageIV)

        holder.itemView.setOnClickListener {

            productId = product.id
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

        holder.cartPositiveBTN.setOnClickListener {
            FirebaseDatabase.getInstance().getReference("cart").child("+8801736194336").child(product.id).addValueEventListener(incrementEL)
        }
    }

    override fun getItemCount(): Int {
        return products.size
    }


    private val incrementEL = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            var value = snapshot.getValue(Cart::class.java)

            val count = value?.count?:0

            if (count<10){
                FirebaseDatabase.getInstance().getReference("cart").child("+8801736194336").child(productId).child("count").setValue(count+1)
            }


        }
        override fun onCancelled(databaseError: DatabaseError) {

        }
    }


}