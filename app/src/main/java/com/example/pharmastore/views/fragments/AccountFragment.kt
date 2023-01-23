package com.example.pharmastore.views.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.pharmastore.R
import com.example.pharmastore.models.User
import com.example.pharmastore.views.activities.LoginActivity
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import de.hdodenhof.circleimageview.CircleImageView

class AccountFragment : Fragment() {

    lateinit var signOutTextView: TextView
    lateinit var nameTextView: TextView
    lateinit var phoneTextView: TextView
    lateinit var profileCircleImageView: CircleImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val  view = inflater.inflate(R.layout.fragment_account, container, false)


        signOutTextView = view.findViewById(R.id.fragmentAccountSignOutTV)
        nameTextView = view.findViewById(R.id.fragmentAccountName)
        phoneTextView = view.findViewById(R.id.fragmentAccountPhone)
        profileCircleImageView = view.findViewById(R.id.fragmentAccountProfileImage)



        val sharedPreference =  requireActivity().getSharedPreferences("main", Context.MODE_PRIVATE)
        val auth = sharedPreference.getBoolean("auth",false)

        if (auth){

            val phone = sharedPreference.getString("phone","000")
            FirebaseDatabase.getInstance().getReference("users").child(phone!!).addListenerForSingleValueEvent(getUserData)

        }else{
            doSignOut()
        }

        signOutTextView.setOnClickListener {
            doSignOut()
        }


        return view
    }

    private fun doSignOut() {
        val sharedPreference = requireActivity().getSharedPreferences("main", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("auth",false)
        editor.commit()
        val intent = Intent(requireActivity(), LoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

    }

    private val getUserData = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            if (snapshot.exists()){
                val user = snapshot.getValue(User::class.java)

                nameTextView.text = user?.name
                phoneTextView.text = user?.phone
                Glide.with(requireActivity()).load(user?.image).into(profileCircleImageView)

            }else{
                doSignOut()
            }

        }
        override fun onCancelled(databaseError: DatabaseError) {
            Toast.makeText(requireActivity(),"Request Failed", Toast.LENGTH_SHORT).show()
        }
    }


}