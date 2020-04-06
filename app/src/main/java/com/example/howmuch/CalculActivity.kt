package com.example.howmuch

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView

class CalculActivity : AppCompatActivity() {
    var groupId: Int = 0
    var calculMenu = CalculMenuFragment()
    var calculMember = CalculMemberFragment()
    var calculResult = CalculResultFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calcul)
        if (intent.hasExtra("groupId")) {
            groupId = intent.getIntExtra("groupId",0)
        }
        Log.d("debug1","groupId: ${groupId}")

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation2)
        bottomNav.setOnNavigationItemSelectedListener(navListener)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().replace(R.id.fragment_container2, calculMenu).commit()
        }
    }
    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        var selectedFragment: Fragment = calculMenu
        var bundle = Bundle()
        bundle.putInt("groupId", groupId)
        calculMenu.arguments = bundle
        calculMember.arguments = bundle
        calculResult.arguments = bundle
        when(item.itemId) {
            R.id.nav_menu ->  selectedFragment = calculMenu
            R.id.nav_member -> selectedFragment = calculMember
            R.id.nav_result -> selectedFragment = calculResult
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container2,
            selectedFragment).commit()
        true
    }
}

