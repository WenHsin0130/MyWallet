package com.example.mywallet

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.example.wallet.R

import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.database.*

class MainActivity : AppCompatActivity() {

    // creating a variable for our "Firebase Database"
    lateinit var firebaseDatabase: FirebaseDatabase
    // creating a variable for our "Database Reference for Firebase"
    lateinit var databaseReference: DatabaseReference

    // variable "bottom navigation view" (聲明之後會進行轉換)
    lateinit var bottomNav : BottomNavigationView

    // variable for Text view
    private lateinit var retrieveTV: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // bottom navigation，呼叫 loadFragment() 將首頁預設成 Home()
        loadFragment(HomeActivity())

        // 設置 bottomNav 的 Listener，控制 fragment 切換
        // 轉換 bottom_navigation 成 BottomNavigationView
        bottomNav = findViewById(R.id.bottom_navigation) as BottomNavigationView
        bottomNav.setOnItemSelectedListener { item: MenuItem ->
            when (item.itemId) {
                // home 連接 fragment_home
                R.id.nav_home -> {
                    loadFragment(HomeActivity())
                    true
                }

                // search 連接 fragment_menu
                R.id.nav_search -> {
                    loadFragment(MenuActivity())
                    true
                }

                // setting 連接 fragment_setting
                R.id.nav_setting -> {
                    loadFragment(SettingActivity())
                    true
                }

                else -> false
            }
        }

        // below line is used to get the instance of our Firebase database
        firebaseDatabase = FirebaseDatabase.getInstance()

        // below line is used to get reference for our database
        databaseReference = firebaseDatabase.getReference("Data")
    }

    // bottom navigation - load fragment() 用於替換 main_container 內的 fragment
    private  fun loadFragment(fragment: Fragment){
        // 開始新的 Fragment 行為
        val transaction = supportFragmentManager.beginTransaction()

        // 使用 replace() 將 main_container 的內容替換成代入的 Fargment，並提交請求
        transaction.replace(R.id.main_container,fragment)
        transaction.commit()
    }



}