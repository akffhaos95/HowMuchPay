package com.example.howmuch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView


class CalculActivity : AppCompatActivity() {
    var groupId = 0

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment = CalculMenuFragment()
        when(item.itemId) {
            R.id.nav_menu -> selectedFragment = CalculMenuFragment()
            R.id.nav_member -> selectedFragment = CalculMemberFragment()
            R.id.nav_result -> selectedFragment = CalculResultFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container2,
            selectedFragment).commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcul)
        Log.d("debug1","calcul activity")
        if (intent.hasExtra("groupId")) {
            groupId = intent.getIntExtra("groupId",0)
        }

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation2)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container2,
                CalculMenuFragment()
            ).commit()
        }
    }
}

