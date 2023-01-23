package com.example.pharmastore.views.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.denzcoskun.imageslider.ImageSlider
import com.denzcoskun.imageslider.constants.ScaleTypes
import com.denzcoskun.imageslider.models.SlideModel
import com.example.pharmastore.R
import com.example.pharmastore.models.Product
import com.example.pharmastore.viewmodels.MainViewModel
import com.example.pharmastore.views.activities.MainActivity
import com.example.pharmastore.views.activities.PrescriptionActivity
import com.example.pharmastore.views.activities.SearchActivity
import com.example.pharmastore.views.adapters.ProductAdapter

class HomeFragment : Fragment() {



    private lateinit var linearLayout: LinearLayout

    private lateinit var prescriptionLinearLayout: LinearLayout

    lateinit var recyclerView: RecyclerView

    private lateinit var imageSlider: ImageSlider


    private lateinit var productAdapter: ProductAdapter

    private lateinit var products: MutableList<Product>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        val view = inflater.inflate(R.layout.fragment_home, container, false)

        prescriptionLinearLayout = view.findViewById(R.id.fragmentHomePrescriptionScanBTN)

        recyclerView = view.findViewById(R.id.fragmentHomeMainRecyclerView)
        recyclerView.layoutManager = GridLayoutManager(requireActivity(),3)
        products = ArrayList()
        productAdapter = ProductAdapter(requireActivity(),products)
        recyclerView.adapter = productAdapter



        linearLayout = view.findViewById(R.id.searchLL)

        imageSlider = view.findViewById(R.id.image_slider)
        val images: MutableList<SlideModel> = ArrayList()
        images.add(SlideModel(R.drawable.slider_5,ScaleTypes.FIT))
        images.add(SlideModel(R.drawable.slider_4,ScaleTypes.FIT))
        images.add(SlideModel(R.drawable.slider_3,ScaleTypes.FIT))
        images.add(SlideModel(R.drawable.slider_2,ScaleTypes.FIT))
        images.add(SlideModel(R.drawable.slider_1,ScaleTypes.FIT))
        imageSlider.setImageList(images)





       MainActivity.mainViewModel?.getProducts()

        MainActivity.mainViewModel?.products?.observe(requireActivity(), Observer {
            products.clear()
            for (item in it){
                products.add(item)
                productAdapter.notifyDataSetChanged()
            }
        })

        linearLayout.setOnClickListener{
            startActivity(Intent(requireActivity(),SearchActivity::class.java))
        }

        prescriptionLinearLayout.setOnClickListener {
            startActivity(Intent(requireActivity(),PrescriptionActivity::class.java))
        }
        return view
    }

}