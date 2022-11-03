package com.example.pharmastore.views.activities


import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.replace
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import com.example.pharmastore.R
import com.example.pharmastore.viewmodels.MainViewModel
import com.example.pharmastore.views.PharmaStoreApplication
import com.example.pharmastore.views.fragments.*
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    companion object{

        var mainViewModel: MainViewModel? = null
    }

    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle

    private lateinit var bottomNavigationView: BottomNavigationView

    private lateinit var fragmentContainerView : FragmentContainerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initWized()
        buttonClickListener()

        replaceFragment(HomeFragment())




    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)

        }else if (bottomNavigationView.selectedItemId != R.id.bottomHome){
            replaceFragment(HomeFragment())
            bottomNavigationView.selectedItemId = R.id.bottomHome
        }else{
            super.onBackPressed()
        }
    }

    private fun initWized(){

        drawerLayout = findViewById(R.id.activityMainDrawerLayout)
        navigationView = findViewById(R.id.activityMainNavigationView)
        toolbar = findViewById(R.id.activityMainToolbar)

        bottomNavigationView = findViewById(R.id.bottom_nav)

        fragmentContainerView = findViewById(R.id.fragmentContainerView)

        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        setSupportActionBar(toolbar)


        toggle = ActionBarDrawerToggle(this,drawerLayout,R.string.open,R.string.close)
        toggle.setHomeAsUpIndicator(R.drawable.ic_baseline_home_24)

        drawerLayout.addDrawerListener(toggle)

        toggle.syncState()

        supportActionBar?.setDisplayHomeAsUpEnabled(true)




    }

    private fun buttonClickListener(){

        navigationView.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.mainMenuHome -> {
                    replaceFragment(HomeFragment())
                    bottomNavigationView.selectedItemId = R.id.bottomHome
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.mainMenuNotifications -> {
                    replaceFragment(NotificationsFragment())
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.mainMenuAccount -> {
                    replaceFragment(AccountFragment())
                    bottomNavigationView.selectedItemId = R.id.bottomAccount
                    drawerLayout.closeDrawer(GravityCompat.START)
                }
                R.id.mainMenuAdmin -> {
                    val intent = Intent(applicationContext,AdminMainActivity::class.java)
                    startActivity(intent)

                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }

                }
                R.id.mainMenuSignOut -> {
                    val sharedPreference =  getSharedPreferences("main", Context.MODE_PRIVATE)
                    var editor = sharedPreference.edit()
                    editor.putBoolean("auth",false)
                    editor.commit()
                    val intent = Intent(applicationContext,LoginActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                    startActivity(intent)

                    if (drawerLayout.isDrawerOpen(GravityCompat.START)){
                        drawerLayout.closeDrawer(GravityCompat.START)

                    }

                }




            }
            true
        }

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.bottomHome ->{
                    replaceFragment(HomeFragment())
                }
                R.id.bottomFavorite ->{
                    replaceFragment(FavoriteFragment())
                }
                R.id.bottomOffers ->{
                    replaceFragment(OffersFragment())
                }
                R.id.bottomCart ->{
                    replaceFragment(CartFragment())
                }
                R.id.bottomAccount ->{
                    replaceFragment(AccountFragment())
                }
            }
            true
        }

    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainerView,fragment)
        fragmentTransaction.commit()
    }



}