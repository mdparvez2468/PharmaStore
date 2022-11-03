package com.example.pharmastore.views.activities

import android.Manifest.permission
import android.app.ProgressDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.pharmastore.R
import com.example.pharmastore.models.Prescription
import com.example.pharmastore.models.Product
import com.google.android.gms.tasks.Task
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.UploadTask
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.*


class PrescriptionActivity : AppCompatActivity() {


    lateinit var button: Button
    lateinit var imageView: ImageView


    lateinit var detailsET: EditText


    var imageBitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_prescription)

        button = findViewById(R.id.activityPrescriptionBTN)
        imageView = findViewById(R.id.activityPrescriptionIV)

        detailsET = findViewById(R.id.activityPrescriptionDetailsET)



        if (ActivityCompat.checkSelfPermission(
                this,
                permission.CAMERA

            ) == PackageManager.PERMISSION_GRANTED
        ) {

            val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(intent, 101)

        } else {
            ActivityCompat.requestPermissions(this, arrayOf(permission.CAMERA), 401)
        }

        button.setOnClickListener {
            firebaseUploadBitmap()
        }

    }







    private fun firebaseUploadBitmap() {

        var details = detailsET.text.toString()

        if (details == ""){
            details = "N/A"
        }


        if (imageBitmap==null){

            Toast.makeText(this,"Fount Some Problem ",Toast.LENGTH_SHORT).show()
            finish()

        }else {

            val progressDialog = ProgressDialog(this)
            progressDialog.setMessage("Please Wait...")
            progressDialog.setCancelable(false)
            progressDialog.show()

            val stream = ByteArrayOutputStream()
            imageBitmap?.compress(Bitmap.CompressFormat.PNG, 100, stream)
            val data: ByteArray = stream.toByteArray()
            val imageStorage: StorageReference = FirebaseStorage.getInstance().reference

            val formatter = SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.getDefault())
            val now = Date()
            val fileName = formatter.format(now)

            val imageRef = imageStorage.child("prescriptions/$fileName")
            val urlTask: Task<Uri> =
                imageRef.putBytes(data).continueWithTask { task: Task<UploadTask.TaskSnapshot?> ->
                    if (!task.isSuccessful) {
                        throw task.exception!!
                    }
                    imageRef.downloadUrl
                }.addOnCompleteListener { task: Task<Uri> ->
                    if (task.isSuccessful) {
                        val downloadUri = task.result
                        val uri = downloadUri.toString()
                        val prescription = Prescription(fileName, uri, details)

                        FirebaseDatabase.getInstance().reference.child("prescriptions").child(fileName)
                            .setValue(prescription).addOnSuccessListener {

                            Toast.makeText(this, "Upload Successful", Toast.LENGTH_SHORT)
                                .show()
                            if (progressDialog.isShowing) progressDialog.dismiss()

                            finish()

                        }.addOnFailureListener {

                            Toast.makeText(this, "Upload Failed", Toast.LENGTH_SHORT).show()
                            if (progressDialog.isShowing) progressDialog.dismiss()

                        }
                    } else {
                        // Handle failures
                        // ...
                    }
                    if (progressDialog.isShowing) progressDialog.dismiss()

                    finish()
                }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode){
            101 ->{
                if (resultCode == RESULT_OK){

                    try {
                        imageBitmap = data?.getParcelableExtra("data")
                        imageView.setImageBitmap(imageBitmap)
                        Log.i("TAG", "onActivityResult: Success")
                    }catch (e: Exception){
                        Log.i("TAG", "onActivityResult: $e")
                    }

                }
            }
        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            401 ->{
                if (grantResults.isNotEmpty() ){
                    val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(intent, 101)
                }else{
                    Toast.makeText(this, "CAMERA Permission Denied", Toast.LENGTH_SHORT)
                        .show()
                }
            }
        }

    }


}