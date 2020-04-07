package com.example.howmuch

import android.content.DialogInterface
import android.content.Intent
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
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.main_home.*

class MainHomeFragment : Fragment() {
    private lateinit var viewModel: ViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.main_home, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //RecyclerView
        val adapter = GroupAdapter(context,{group ->
            val intent = Intent(context, CalculActivity::class.java)
            intent.putExtra("groupId", group.id)
            startActivity(intent)
        }, { group ->
            var builder = AlertDialog.Builder(getContext()!!)
            builder.setTitle("Update or Delete")
            builder.setPositiveButton("Update"){dialog, i ->
                insertDialog(group)
            }
            builder.setNegativeButton("Delete"){dialog, i ->
                deleteDialog(group)
            }.show()
        })
        val layoutManager = GridLayoutManager(getActivity(),2)
        mainRecyclerView.adapter = adapter
        mainRecyclerView.layoutManager = layoutManager
        mainRecyclerView.setHasFixedSize(true)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getAllGroup().observe(this, Observer<List<Group>> { group ->
            adapter.setGroup(group!!)
        })

        //ClickListener
        btn_plus.setOnClickListener {
            insertDialog(null)
        }

    }
    private fun insertDialog(group: Group?) {
        val builder = AlertDialog.Builder(getContext()!!)
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
        val builder = AlertDialog.Builder(getContext()!!)
        builder.setMessage("Delete selected group?")
            .setPositiveButton("Yes") {_, _ ->
                viewModel.deleteGroup(group)
            }
            .setNegativeButton("No") {_, _ ->}
        builder.show()
    }
}