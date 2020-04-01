package com.example.howmuch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_plus.*

class GroupActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var menuViewModel: MenuViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus)

        btn_result.setOnClickListener(this)
        btn_menu_plus.setOnClickListener(this)

        val adapter = MenuAdapter( {menu ->

        }, {menu ->
            deleteDialog(menu)
        })

        val layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        menuViewModel = ViewModelProviders.of(this).get(MenuViewModel::class.java)
        menuViewModel.getAll().observe(this, Observer<List<Menu>> { menu->
            adapter.setMenu(menu!!)
        })
    }
    private fun deleteDialog(menu:Menu) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected menu?")
            .setNegativeButton("No") {_, _ ->}
            .setPositiveButton("Yes") {_, _ ->
                menuViewModel.deleteMenu(menu)
            }
        builder.show()
    }
    private fun insertDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_insert, null)
        val dialogName = dialogView.findViewById<EditText>(R.id.txt_insert_Name)
        val dialogPrice = dialogView.findViewById<EditText>(R.id.txt_insert_Price)
        builder.setView(dialogView)
            .setPositiveButton("Save") { dialogInterface, i ->
                val newMenu = Menu()
                newMenu.name  = dialogName.text.toString()
                newMenu.price  = dialogPrice.text.toString()
                menuViewModel.insert(newMenu)
            }
            .setNegativeButton("Cancel") { dialogInterface, i ->

            }
            .show()
    }
    override fun onClick(clickView: View?) {
        when(clickView?.id) {
            R.id.btn_result -> {
                //menuViewModel.deleteAll(menu)
                Toast.makeText(this,"전체 삭제", Toast.LENGTH_SHORT).show()
            }
            R.id.btn_menu_plus -> {
                insertDialog()
            }
        }
    }
}