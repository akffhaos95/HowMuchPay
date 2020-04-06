package com.example.howmuch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment = MainHomeFragment()

        when(item.itemId) {
            R.id.nav_home -> selectedFragment = MainHomeFragment()
            R.id.nav_menu1 -> selectedFragment = MainMenuFragment()
            R.id.nav_setting -> selectedFragment = MainSettingFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
        selectedFragment).commit()
        true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container,
                MainHomeFragment()).commit()
        }
    }
}
