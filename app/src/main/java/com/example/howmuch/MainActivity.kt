package com.example.howmuch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_plus.*

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_plus.setOnClickListener {
            insertDialog(null)
        }
        val adapter = GroupAdapter({ group ->
            val Intent = Intent(this, MemberActivity::class.java)
            Intent.putExtra("groupId", group.id)
            startActivity(Intent)
        }, { group ->
            insertDialog(group)
            //deleteDialog(group)
        })
        val layoutManager = LinearLayoutManager(this)
        mainRecyclerView.adapter = adapter
        mainRecyclerView.layoutManager = layoutManager
        mainRecyclerView.setHasFixedSize(true)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getAllGroup().observe(this, Observer<List<Group>> { group ->
            adapter.setGroup(group!!)
        })
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
}
