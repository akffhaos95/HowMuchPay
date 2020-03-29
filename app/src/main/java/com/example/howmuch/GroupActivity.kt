package com.example.howmuch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_plus.*
import java.lang.Exception

class GroupActivity : AppCompatActivity(), View.OnClickListener {
    private var menuDB : Db_menu? = null
    private var menu_List = arrayListOf<Db_menu_Entity>()
    lateinit var mAdapter : MenuAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus)
        btn_result.setOnClickListener(this)
        btn_menu_plus.setOnClickListener(this)
        menuDB = Db_menu.getInstance(this)
        val thread = Thread(r)
        thread.start()
    }
    val r = Runnable {
        try {
            menu_List = ArrayList(menuDB?.menu_Dao()?.getAll()!!)
            mAdapter = MenuAdapter(this, menu_List, { menu ->
                Toast.makeText(this,"Menu ${menu.name}, Price ${menu.price}", Toast.LENGTH_SHORT).show()
            }, { menu ->
                val del = Runnable {
                    try{
                        menuDB?.menu_Dao()?.deleteMenu(menu.id)!!
                        menu_List = ArrayList(menuDB?.menu_Dao()?.getAll()!!)
                    } catch (e: Exception){ Log.d("error","Error2 - ${e}") }
                }
                val del_thread = Thread(del)
                del_thread.start()
                del_thread.join()
                mAdapter.notifyDataSetChanged()
                Toast.makeText(this,"Menu : ${menu.name} 삭제", Toast.LENGTH_SHORT).show()
            })
            mAdapter.notifyDataSetChanged()
            mRecyclerView.adapter = mAdapter
            mRecyclerView.layoutManager = LinearLayoutManager(this)
            mRecyclerView.setHasFixedSize(true)
        } catch (e: Exception){
            Log.d("error","Error - ${e}")
        }
    }
    override fun onClick(clickView: View?) {
        when(clickView?.id) {
            R.id.btn_result -> {
                /*var member = txt_member.text.toString()
                var money = txt_money.text.toString()
                if (""== member||""==money||"0"==member||"0"==money) {
                    Toast.makeText(this, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()
                }  else {
                    txt_result.text = (Integer.parseInt(money)/Integer.parseInt(member)).toString()
                }*/
                val delAll = Runnable {
                    try{
                        menuDB?.menu_Dao()?.deleteAll()!!
                    } catch (e: Exception){
                        Log.d("error","Error2 - ${e}")
                    }
                }
                val delAll_thread = Thread(delAll)
                delAll_thread.start()
                delAll_thread.join()
                menu_List.clear()
                mAdapter!!.notifyDataSetChanged()
                Toast.makeText(this,"전체 삭제", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_menu_plus -> {
                val i = Intent(this, AddActivity::class.java)
                startActivity(i)
            }
        }
    }
    override fun onDestroy() {
        Db_menu.destroyInstance()
        menuDB = null
        super.onDestroy()
    }
}