package com.example.howmuch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_plus.*


class GroupActivity : AppCompatActivity(), View.OnClickListener {
    private var menuDB : Db_menu? = null
    private var menu_List = LiveData<Db_menu_Entity>()
    lateinit var mAdapter : MenuAdapter
    private var menuView = MenuViewModel(application)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus)
        btn_result.setOnClickListener(this)
        btn_menu_plus.setOnClickListener(this)
        menuDB = Db_menu.getInstance(this)
    }
    override fun onResume() {
        super.onResume()
        val thread = Thread(r)
        thread.start()
    }

    val r = Runnable {
        try {
            mAdapter = MenuAdapter(this, menu_List, { menu ->
                Toast.makeText(this,"Menu ${menu.name}, Price ${menu.price}", Toast.LENGTH_SHORT).show()
            }, { menu ->
                val del = Runnable {
                    try{
                        menuDB?.menu_Dao()?.deleteMenu(menu.id)!!
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
                val delAll = Runnable {
                    try{ menuDB?.menu_Dao()?.deleteAll()!!
                    } catch (e: Exception){ Log.d("error","Error2 - ${e}")
                    }
                }
                val delAll_thread = Thread(delAll)
                delAll_thread.start()
                menu_List.clear()
                mAdapter.notifyDataSetChanged()
                Toast.makeText(this,"전체 삭제", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_menu_plus -> {
                val add = Intent(this, AddActivity::class.java)
                startActivity(add)
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