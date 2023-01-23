package com.example.pharmastore.views.activities

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.pharmastore.R

class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val sharedPreference =  getSharedPreferences("main", Context.MODE_PRIVATE)
        val auth = sharedPreference.getBoolean("auth",false)

        Handler().postDelayed({
            if (/*auth*/true ){
                val intent = Intent(this,MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)


            }else{
                val intent = Intent(this,LoginActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }, 3000)






    }
}