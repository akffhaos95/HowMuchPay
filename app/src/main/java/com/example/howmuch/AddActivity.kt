package com.example.howmuch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_add.*
import kotlinx.android.synthetic.main.activity_plus.*

class AddActivity : AppCompatActivity(), View.OnClickListener {
    private var menuDB : Db_menu? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add)
        btn_add_save.setOnClickListener(this)
        btn_add_cancel.setOnClickListener(this)
        menuDB = Db_menu.getInstance(this)
    }
    val addRunnable = Runnable {
        val newMenu = Db_menu_Entity()
        newMenu.name = txt_add_Name.text.toString()
        newMenu.price = txt_add_Price.text.toString()
        menuDB?.menu_Dao()?.insert(newMenu)
    }
    override fun onClick(clickView: View?) {
        when(clickView?.id) {
            R.id.btn_add_save -> {
                val addThread = Thread(addRunnable)
                addThread.start()
                val i = Intent(this, GroupActivity::class.java)
                startActivity(i)
                finish()
            }
            R.id.btn_add_cancel -> {
                val i = Intent(this, GroupActivity::class.java)
                startActivity(i)
                finish()
            }
        }
    }
    override fun onDestroy() {
        Db_menu.destroyInstance()
        menuDB = null
        super.onDestroy()
    }
}