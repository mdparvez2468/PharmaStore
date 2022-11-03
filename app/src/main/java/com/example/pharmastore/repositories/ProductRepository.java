package com.example.pharmastore.repositories;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import com.example.pharmastore.models.Post;
import com.example.pharmastore.models.Product;
import com.example.pharmastore.viewmodels.MainViewModel;
import com.example.pharmastore.views.PharmaStoreApplication;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ProductRepository {



    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

   public MutableLiveData<List<Product>> products = new MutableLiveData<>();

    public void getProducts(){

        List<Product> list = new ArrayList<>();

        databaseReference.child("products").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Product product = dataSnapshot.getValue(Product.class);

                    list.add(product);

                    Log.i("TAG", "onDataChange: "+product.getName());

                }
                products.postValue(list);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }



}