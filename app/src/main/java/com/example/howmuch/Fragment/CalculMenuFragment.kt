package com.example.howmuch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.calcul_member.*
import kotlinx.android.synthetic.main.calcul_menu.*
import kotlinx.android.synthetic.main.calcul_menu.recyclerView

class CalculMenuFragment: Fragment() {
    private lateinit var viewModel: ViewModel
    var groupId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calcul_menu, container, false)
        Log.d("debug1","onCreateView")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        Log.d("debug1","onViewCreated")
        groupId = arguments!!.getInt("groupId")
        Log.d("debug1","onViewCreated1")
        btn_menu_add.setOnClickListener{ insertDialog(null) }
        val adapter = MenuAdapter({ menu -> insertDialog(menu)
        }, { menu -> deleteDialog(menu) })

        val layoutManager = LinearLayoutManager(getActivity())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)
        Log.d("debug1","onViewCreated2")

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getAllMenu(groupId).observe(this, Observer<List<Menu>> { menu ->
            adapter.setMenu(menu!!)
        })
        Log.d("debug1","onViewCreated3")

        viewModel.getPrice(groupId).observe(this, Observer { price ->
            txt_menu.text = price
        })
    }

    private fun deleteDialog(menu:Menu) {
        val builder = AlertDialog.Builder(getContext()!!)
        builder.setMessage("Delete selected menu?")
            .setNegativeButton("No") {_, _ ->}
            .setPositiveButton("Yes") {_, _ ->
                viewModel.deleteMenu(menu)
            }
        builder.show()
    }
    private fun deleteAllDialog(groupId:Int?) {
        val builder = AlertDialog.Builder(getContext()!!)
        builder.setMessage("Delete all are you sure?")
            .setNegativeButton("No") {_, _ ->}
            .setPositiveButton("Yes") {_, _ ->
                //viewModel.deleteAll(groupId)
            }
        builder.show()
    }
    private fun insertDialog(menu: Menu?) {
        val builder = AlertDialog.Builder(getContext()!!)
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