package com.example.pharmastore.views.activities

import android.app.ProgressDialog
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.pharmastore.R
import com.example.pharmastore.models.Product
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import java.text.SimpleDateFormat
import java.util.*

class AdminMainActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var imageView: ImageView


    lateinit var nameET: EditText
    lateinit var priceET: EditText
    lateinit var mrpET: EditText

    var imageUri: Uri? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_admin_main)

        button = findViewById(R.id.activityAdminMainCaptureBTN)
        imageView = findViewById(R.id.activityAdminMainIV)

        nameET = findViewById(R.id.activityAdminMainNameET)
        priceET = findViewById(R.id.activityAdminMainPriceET)
        mrpET = findViewById(R.id.activityAdminMainMrpET)

        imageView.setOnClickListener {
            selectImage()
        }

        button.setOnClickListener {
            uploadImage()
        }


    }

    private fun uploadImage() {


        val name = nameET.text.toString()
        val price = priceET.text.toString()
        val mrp = mrpET.text.toString()

        if (imageUri==null){

            Toast.makeText(this,"Select an Image",Toast.LENGTH_SHORT).show()

        }else if (name == ""){
            Toast.makeText(this,"Enter Name",Toast.LENGTH_SHORT).show()

        }else if (price == ""){
            Toast.makeText(this,"Enter Price",Toast.LENGTH_SHORT).show()

        }else if (mrp == ""){
            Toast.makeText(this,"Enter MRP",Toast.LENGTH_SHORT).show()

        } else{

            val progressDialog = ProgressDialog(this)

            progressDialog.setMessage("Please Wait...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)

            val storageReference = FirebaseStorage.getInstance().getReference("products/$fileName")

            storageReference.putFile(imageUri!!).addOnSuccessListener {

                it.storage.downloadUrl.addOnCompleteListener {

                    val product = Product(fileName,price.toInt(),mrp.toInt(),name,  it.result.toString())

                    FirebaseDatabase.getInstance().getReference("products").child(fileName).setValue(product).addOnSuccessListener {

                        Toast.makeText(this,"Image saved Successful",Toast.LENGTH_SHORT).show()
                        if (progressDialog.isShowing)progressDialog.dismiss()

                        finish()

                    }.addOnFailureListener {

                        Toast.makeText(this,"Image saved Failed",Toast.LENGTH_SHORT).show()
                        if (progressDialog.isShowing)progressDialog.dismiss()

                    }



                }

            }.addOnFailureListener {

                Toast.makeText(this,"Image saved Failed",Toast.LENGTH_SHORT).show()
                if (progressDialog.isShowing)progressDialog.dismiss()
            }

        }




    }

    private fun selectImage() {

        val intent = Intent()
        intent.type = "image/*"
        intent.action = Intent.ACTION_GET_CONTENT

        startActivityForResult(intent,101)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){



            101 ->{

                if (resultCode == RESULT_OK){

                    imageUri = data?.data!!
                    imageView.setImageURI(imageUri)
                }
            }
        }
    }
}