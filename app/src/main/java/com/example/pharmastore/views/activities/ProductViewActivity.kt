package com.example.pharmastore.views.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.pharmastore.R
import com.example.pharmastore.models.Cart
import com.example.pharmastore.models.Product
import com.example.pharmastore.viewmodels.MainViewModel
import com.google.firebase.database.FirebaseDatabase

class ProductViewActivity : AppCompatActivity() {



    lateinit var addCartBTN: LinearLayout
    lateinit var addFavoriteBTN: LinearLayout

    lateinit var imageView: ImageView
    lateinit var nameTextView: TextView
    lateinit var priceTextView: TextView
    lateinit var mrpTextView: TextView

    private var productId: String = ""
    private var product: Product? = null
    private var cart: Cart? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_view)



        addCartBTN = findViewById(R.id.activityProductViewAddCartBTN)
        addFavoriteBTN = findViewById(R.id.activityProductViewAddFavoriteBTN)

        imageView = findViewById(R.id.productViewActivityIV)
        nameTextView = findViewById(R.id.activityProductViewNameTV)
        priceTextView = findViewById(R.id.activityProductViewPriceTV)
        mrpTextView = findViewById(R.id.activityProductViewMrpTV)

        MainActivity.mainViewModel?.productForView?.observe(this, Observer {

            Glide.with(this).load(it.image).into(imageView)
            nameTextView.text = it.name
            priceTextView.text = it.price.toString()
            mrpTextView.text = it.mrp.toString()


            productId = it.id
            cart = Cart(it.id, it.price, 0, it.name, it.image)
        })

        addCartBTN.setOnClickListener {
            FirebaseDatabase.getInstance().getReference("cart").child("+8801736194336").child(productId).setValue(cart).addOnSuccessListener {

                Toast.makeText(this,"Add to Cart", Toast.LENGTH_SHORT).show()

            }.addOnFailureListener {
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()

            }
        }
        addFavoriteBTN.setOnClickListener {
            FirebaseDatabase.getInstance().getReference("favorite").child("+8801736194336").child(productId).setValue(product).addOnSuccessListener {
                Toast.makeText(this,"Add to Favorite", Toast.LENGTH_SHORT).show()
            }.addOnFailureListener {
                Toast.makeText(this,"Failed", Toast.LENGTH_SHORT).show()

            }
        }


    }
}