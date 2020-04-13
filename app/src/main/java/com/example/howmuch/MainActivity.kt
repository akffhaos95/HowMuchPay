package com.example.howmuch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.dialog_insert.*
import kotlinx.android.synthetic.main.dialog_insert.view.*


class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //LoadingActivity
        val intent = Intent(this, LoadingActivity::class.java)
        startActivity(intent)

        //Toolbar
        val toolbar = main_toolbar
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayShowTitleEnabled(false)

        //RecyclerView
        val adapter = MainAdapter(this,{ group ->
            val intent = Intent(this, InsertActivity::class.java)
            intent.putExtra("groupId", group.id)
            intent.putExtra("groupName",group.name)
            startActivity(intent)
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }, { group -> insertDialog(group) })
        val layoutManager = LinearLayoutManager(this)
        main_RecyclerView.adapter = adapter
        main_RecyclerView.layoutManager = layoutManager
        main_RecyclerView.setHasFixedSize(true)

        //SwipeToDelete
        val swipeHandler = object : SwipeToDelete(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = main_RecyclerView.adapter as MainAdapter
                val removeGroup = adapter.removeAt(viewHolder.adapterPosition)
                viewModel.deleteGroup(removeGroup)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(main_RecyclerView)

        //RoomDB
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
            overridePendingTransition(R.anim.fadein, R.anim.fadeout);
        }
    }

    override fun onResume() {
        super.onResume()
        //Fall Down Animation
        val resId:Int = R.anim.layout_animation_fall_down
        val animCon:LayoutAnimationController = AnimationUtils.loadLayoutAnimation(this,resId)
        main_RecyclerView.layoutAnimation = animCon
    }

    private fun insertDialog(group: Group?) {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_insert, null)
        dialogView.dialog_title.text = "그룹 추가"
        if(group!=null){
            dialogView.dialog_name.setText(group.name)
        }
        builder.setView(dialogView)
            .setPositiveButton("Save") { dialogInterface, i ->
                val newGroup = Group()
                newGroup.name  = dialogView.dialog_name.text.toString()
                viewModel.insertGroup(newGroup)
            }
            .setNegativeButton("Cancel") { dialogInterface, i -> }
            .show()

    }
}