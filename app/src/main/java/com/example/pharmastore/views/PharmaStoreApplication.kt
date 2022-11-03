package com.example.pharmastore.views

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.example.pharmastore.repositories.ProductRepository
import com.example.pharmastore.viewmodels.MainViewModel
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PharmaStoreApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        context = applicationContext
        databaseReference = FirebaseDatabase.getInstance().reference

    }

    companion object{

        @SuppressLint("StaticFieldLeak")
        var context: Context? = null
       var databaseReference: DatabaseReference? = null


    }

}