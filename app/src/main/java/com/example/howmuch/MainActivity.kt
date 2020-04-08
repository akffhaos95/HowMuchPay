package com.example.howmuch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //Toolbar
        val toolbar = main_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        //RecyclerView
        val adapter = GroupAdapter(this,{group ->
            val intent = Intent(this, CalculActivity::class.java)
            intent.putExtra("groupId", group.id)
            intent.putExtra("groupName",group.name)
            startActivity(intent)
        }, { group ->
            var builder = AlertDialog.Builder(this)
            builder.setTitle("Update or Delete")
            builder.setPositiveButton("Update"){dialog, i ->
                insertDialog(group)
            }
            builder.setNegativeButton("Delete"){dialog, i ->
                deleteDialog(group)
            }.show()
        })
        val layoutManager = GridLayoutManager(this,2)
        main_RecyclerView.adapter = adapter
        main_RecyclerView.layoutManager = layoutManager
        main_RecyclerView.setHasFixedSize(true)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getAllGroup().observe(this, Observer<List<Group>> { group ->
            adapter.setGroup(group!!)
        })

        //ClickListener
        main_plus.setOnClickListener {
            insertDialog(null)
        }
        main_setting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }
    }
    private fun insertDialog(group: Group?) {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_insert, null)
        val dialogName = dialogView.findViewById<EditText>(R.id.txt_insert_Name)
        if(group==null) {
            builder.setView(dialogView)
                .setPositiveButton("Save") { dialogInterface, i ->
                    val newGroup = Group()
                    newGroup.name  = dialogName.text.toString()
                    viewModel.insertGroup(newGroup)
                }
                .setNegativeButton("Cancel") { dialogInterface, i -> }
                .show()
        } else {
            dialogName.setText(group.name)
            builder.setView(dialogView)
                .setPositiveButton("Save") { dialogInterface, i ->
                    group.name  = dialogName.text.toString()
                    viewModel.insertGroup(group)
                }
                .setNegativeButton("Cancel") { dialogInterface, i -> }
                .show()
        }
    }
    private fun deleteDialog(group:Group) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected group?")
            .setPositiveButton("Yes") {_, _ ->
                viewModel.deleteGroup(group)
            }
            .setNegativeButton("No") {_, _ ->}
        builder.show()
    }
}