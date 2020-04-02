package com.example.howmuch

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_member.*
import kotlinx.android.synthetic.main.activity_plus.*
import kotlinx.android.synthetic.main.activity_plus.recyclerView

class MemberActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_member)

        btn_member_add.setOnClickListener(this)
        btn_menu.setOnClickListener(this)

        val adapter = MemberAdapter({ member ->
        }, { member ->
            deleteDialog(member)
        })
        val layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getAllMember().observe(this, Observer<List<Member>> { member ->
            adapter.setMember(member!!)
        })
        val groupId = 0
        viewModel.getMemberCnt(groupId).observe(this, Observer<Int> { cnt ->
            txt_member.text = cnt.toString()
        })
    }
    override fun onClick(clickView: View?) {
        when(clickView?.id) {
            R.id.btn_menu -> {
                val Intent = Intent(this, MenuActivity::class.java)
                startActivity(Intent)
                finish()
            }
            R.id.btn_member_add -> {
                insertDialog()
            }
        }
    }
    private fun deleteDialog(member:Member) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete selected menu?")
            .setNegativeButton("No") {_, _ ->}
            .setPositiveButton("Yes") {_, _ ->
                viewModel.deleteMember(member)
            }
        builder.show()
    }
    private fun deleteAllDialog(groupId:Int?) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Delete all are you sure?")
            .setNegativeButton("No") {_, _ ->}
            .setPositiveButton("Yes") {_, _ ->
                //viewModel.deleteAll(groupId)
            }
        builder.show()
    }
    private fun insertDialog() {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_insert, null)
        val dialogName = dialogView.findViewById<EditText>(R.id.txt_insert_Name)
        builder.setView(dialogView)
            .setPositiveButton("Save") { dialogInterface, i ->
                val newMember = Member()
                newMember.name  = dialogName.text.toString()
                viewModel.insertMember(newMember)
            }
            .setNegativeButton("Cancel") { dialogInterface, i -> }
            .show()
    }
}