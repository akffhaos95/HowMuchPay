package com.example.howmuch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_plus.*

class MenuActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus)

        btn_result.setOnClickListener(this)
        btn_menu_plus.setOnClickListener(this)
        val groupId = 0
        val adapter = MenuAdapter({ menu ->
            insertDialog(menu)
        }, { menu ->
            deleteDialog(menu)
        })
        val layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getAllMenu().observe(this, Observer<List<Menu>> { menu ->
            adapter.setMenu(menu!!)
        })
        viewModel.getPrice(groupId).observe(this, Observer { price ->
            txt_price.text = price
        })
    }
    override fun onClick(clickView: View?) {
        when(clickView?.id) {
            R.id.btn_result -> {
                deleteAllDialog(0)
            }
            R.id.btn_menu_plus -> {
                insertDialog(null)
            }
        }
    }
    private fun deleteDialog(menu:Menu) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected menu?")
            .setNegativeButton("No") {_, _ ->}
            .setPositiveButton("Yes") {_, _ ->
                viewModel.deleteMenu(menu)
            }
        builder.show()
    }
    private fun deleteAllDialog(groupId:Int?) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete all are you sure?")
            .setNegativeButton("No") {_, _ ->}
            .setPositiveButton("Yes") {_, _ ->
                viewModel.deleteAll(groupId)
            }
        builder.show()
    }
    private fun insertDialog(menu: Menu?) {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_insert, null)
        val dialogName = dialogView.findViewById<EditText>(R.id.txt_insert_Name)
        val dialogPrice = dialogView.findViewById<EditText>(R.id.txt_insert_Price)
        if(menu==null) {
            builder.setView(dialogView)
                .setPositiveButton("Save") { dialogInterface, i ->
                    val newMenu = Menu()
                    newMenu.name  = dialogName.text.toString()
                    newMenu.price  = dialogPrice.text.toString()
                    viewModel.insertMenu(newMenu)
                }
                .setNegativeButton("Cancel") { dialogInterface, i -> }
                .show()
        } else {
            dialogName.setText(menu.name)
            dialogPrice.setText(menu.price)
            builder.setView(dialogView)
                .setPositiveButton("Save") { dialogInterface, i ->
                    menu.name  = dialogName.text.toString()
                    menu.price  = dialogPrice.text.toString()
                    viewModel.insertMenu(menu)
                }
                .setNegativeButton("Cancel") { dialogInterface, i -> }
                .show()
        }
    }
}