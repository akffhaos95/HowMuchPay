package com.example.howmuch

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_plus.*
import java.lang.Exception

class GroupActivity : AppCompatActivity() {
    private var menuDB : Db_menu? = null
    private var menu_List = listOf<Db_menu_Entity>()
    lateinit var mAdapter : MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus)
        //Room DB(db_menu)
        menuDB = Db_menu.getInstance(this)

        val r = Runnable {
            try {
                menu_List = menuDB?.menu_Dao()?.getNP()!!
                mAdapter = MenuAdapter(this, menu_List, { menu ->
                    Toast.makeText(this,"Menu ${menu.name}, Price ${menu.price}", Toast.LENGTH_SHORT).show()
                }, { menu->
                    Toast.makeText(this,"Menu ${menu.name} 삭제", Toast.LENGTH_SHORT).show()
                })
                mAdapter.notifyDataSetChanged()
                mRecyclerView.adapter = mAdapter
                mRecyclerView.layoutManager = LinearLayoutManager(this)
                mRecyclerView.setHasFixedSize(true)
            } catch (e: Exception){
                Log.d("error","Error - ${e}")
            }
        }
        val thread = Thread(r)
        thread.start()
        //Result Button
        btn_result.setOnClickListener {
            var member = txt_member.text.toString()
            var money = txt_money.text.toString()
            if (""== member||""==money) {
                Toast.makeText(this, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()
            } else if ("0"==member||"0"==money) {
                Toast.makeText(this, "숫자 0이 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                txt_result.text = (Integer.parseInt(money)/Integer.parseInt(member)).toString()
            }
        }
        //Menu Plus Button
        btn_menu_plus.setOnClickListener {
            val i = Intent(this, AddActivity::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onDestroy() {
        Db_menu.destroyInstance()
        menuDB = null
        super.onDestroy()
    }
}
