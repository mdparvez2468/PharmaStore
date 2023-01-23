package com.example.pharmastore.views.activities

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.telephony.TelephonyManager
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.pharmastore.R
import com.example.pharmastore.models.Product
import com.example.pharmastore.models.User
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

private const val USER_PROFILE_DEFAULT_URL = "https://firebasestorage.googleapis.com/v0/b/internship-projects-ab58c.appspot.com/o/users%2Fuser.png?alt=media&token=950fb4fc-ed85-4cac-af0b-079dc9cb9634"
class LoginActivity : AppCompatActivity() {

    lateinit var button: Button
    lateinit var progressBar: ProgressBar



    var permissions = arrayOf(Manifest.permission.READ_PHONE_NUMBERS)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        button = findViewById(R.id.loginBTN)

        progressBar = findViewById(R.id.activityLoginPB)

        button.setOnClickListener {

            progressBar.visibility = View.VISIBLE

            Handler().postDelayed({

                getPhoneNumber()

                progressBar.visibility = View.INVISIBLE

            }, 1500)

        }
    }


    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String?>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            1 -> if (requestCode == RESULT_OK) {
                getPhoneNumber()
            }
        }
    }

    private fun getPhoneNumber(){

        val telephonyManager = getSystemService(TELEPHONY_SERVICE) as TelephonyManager

        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_SMS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_NUMBERS
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.READ_PHONE_STATE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                requestPermissions(permissions, 101)
            }
            return
        }


       val phoneNumber = telephonyManager.line1Number

        if (phoneNumber.equals("") and phoneNumber.isNullOrEmpty()){
            doSignIn("+8801711223344")
        }else{
            doSignIn(phoneNumber)
        }

    }

    private fun doSignIn(phone: String) {
        FirebaseDatabase.getInstance().getReference("users").child(phone).addListenerForSingleValueEvent(checkUser)
    }

    private fun doSignUn(phone: String){

        val user = User(phone,"N/A", USER_PROFILE_DEFAULT_URL)
        FirebaseDatabase.getInstance().getReference("users").child(phone).setValue(user).addOnSuccessListener {
            signInSuccess(phone)
        }.addOnFailureListener {
            Toast.makeText(this,"SignIn Failed", Toast.LENGTH_SHORT).show()

        }

    }

    private fun signInSuccess(phone: String) {
        Toast.makeText(this,"SignIn Successfully", Toast.LENGTH_SHORT).show()
        val sharedPreference =  getSharedPreferences("main", Context.MODE_PRIVATE)
        var editor = sharedPreference.edit()
        editor.putBoolean("auth",true)
        editor.putString("phone",phone)
        editor.commit()
        val intent = Intent(this,MainActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    private val checkUser = object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {


            if (snapshot.exists()){
                signInSuccess(snapshot.key!!)
                Log.i("TAG", "onDataChange: User Here ${snapshot.key!!}")
            }else{
                doSignUn(snapshot.key!!)
                Log.i("TAG", "onDataChange: User Not Here ${snapshot.key!!}")
            }

        }
        override fun onCancelled(databaseError: DatabaseError) {

            Toast.makeText(this@LoginActivity,"SignIn Failed", Toast.LENGTH_SHORT).show()

        }
    }

}