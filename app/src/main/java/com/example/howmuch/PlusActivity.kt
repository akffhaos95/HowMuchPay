package com.example.howmuch

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_plus.*
import java.lang.Exception

class PlusActivity : AppCompatActivity() {
    private var menuDB : db_menu? = null
    private var menu_List = arrayListOf<Menu>()
    lateinit var mAdapter : MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus)
        Log.d("error","Error - 1")
        //Room DB(db_menu)
        menuDB = db_menu.getInstance(this)
        mAdapter = MenuAdapter(this, menu_List, { menu ->
            Toast.makeText(this,"Menu ${menu.name}, Price ${menu.price}", Toast.LENGTH_SHORT).show()
        }, { menu->
            Toast.makeText(this, "Delete ${menu.name}",Toast.LENGTH_SHORT).show()
        })
        val r = Runnable {
            try {

                Log.d("error","Error - 2")
                menu_List = menuDB?.menu_Dao()?.getAll()!! as ArrayList<Menu>
                MenuAdapter(this, menu_List, { menu ->
                    Toast.makeText(this,"Menu ${menu.name}, Price ${menu.price}", Toast.LENGTH_SHORT).show()
                }, { menu->
                    Toast.makeText(this, "Delete ${menu.name}",Toast.LENGTH_SHORT).show()
                })

                Log.d("error","Error - 3")
                mAdapter.notifyDataSetChanged()
                mRecyclerView.adapter = mAdapter
                mRecyclerView.layoutManager = LinearLayoutManager(this)
                mRecyclerView.setHasFixedSize(true)
            } catch (e: Exception){
                Log.d("error","Error - ${e}")
            }
        }

        Log.d("error","Error - 4")
        val thread = Thread(r)
        thread.start()

        Log.d("error","Error - 5")

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
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_plus_menu, null)
            val dialogName = dialogView.findViewById<EditText>(R.id.txt_plus_menu_name)
            val dialogPrice = dialogView.findViewById<EditText>(R.id.txt_plus_menu_price)
            val addRunnable = Runnable {
                val newMenu = db_menu_Entity()
                newMenu.name = dialogName.text.toString()
                newMenu.price = dialogPrice.text.toString().toInt()
                menuDB?.menu_Dao()?.insert(newMenu)
            }
            builder.setView(dialogView).setPositiveButton("저장") { dialogInterface: DialogInterface, i: Int ->
                val addThread = Thread(addRunnable)
                addThread.start()
            } .setNegativeButton("취소") {
                    dialogInterface, i-> Toast.makeText(this,"가격 : ${dialogPrice.text.toString()}",Toast.LENGTH_SHORT).show()
            }.show()
        }
    }
}
