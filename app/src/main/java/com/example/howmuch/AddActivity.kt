package com.example.howmuch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import kotlinx.android.synthetic.main.activity_add.*

class AddActivity : AppCompatActivity() {

    private var menuDB : Db_menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        menuDB = Db_menu.getInstance(this)

        val addRunnable = Runnable {
            val newMenu = Db_menu_Entity()
            newMenu.name = addName.text.toString()
            newMenu.price = addPrice.text.toString()
            menuDB?.menu_Dao()?.insert(newMenu)
        }

        addBtn.setOnClickListener {
            val addThread = Thread(addRunnable)
            addThread.start()

            val i = Intent(this, GroupActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onDestroy() {
        Db_menu.destroyInstance()
        super.onDestroy()
    }
}