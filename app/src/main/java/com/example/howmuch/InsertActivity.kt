package com.example.howmuch

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_insert.*
import kotlinx.android.synthetic.main.dialog_insert.*
import kotlinx.android.synthetic.main.dialog_insert.view.*

class InsertActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var viewModel: ViewModel
    var groupId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insert)
        var title:TextView = findViewById(R.id.title)

        if (intent.hasExtra("groupId")) {
            groupId = intent.getIntExtra("groupId",0)
            title.text = intent.getStringExtra("groupName").toString()
        }

        val menuAdapter = MenuAdapter(){ menu -> insertMenuDialog(menu) }
        val layoutMenuManager = LinearLayoutManager(this)
        menu_recyclerView.adapter = menuAdapter
        menu_recyclerView.layoutManager = layoutMenuManager
        menu_recyclerView.setHasFixedSize(true)
        val memberAdapter = MemberAdapter(){ member -> insertMemberDialog(member) }
        val layoutMemberManager = GridLayoutManager(this,5)
        member_recyclerView.adapter = memberAdapter
        member_recyclerView.layoutManager = layoutMemberManager
        member_recyclerView.setHasFixedSize(true)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getPrice(groupId).observe(this, Observer { price ->
            menu_cnt.text = price
        })
        viewModel.getMemberCnt(groupId).observe(this, Observer { cnt ->
            member_cnt.text = cnt.toString()
        })
        viewModel.getAllMenu(groupId).observe(this, Observer<List<Menu>> { menu ->
            menuAdapter.setMenu(menu!!)
        })
        viewModel.getAllMember(groupId).observe(this, Observer<List<Member>> { member ->
            memberAdapter.setMember(member!!)
        })

        val resId:Int = R.anim.layout_animation_fall_down
        val animCon: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(this,resId)
        menu_recyclerView.layoutAnimation = animCon
        member_recyclerView.layoutAnimation = animCon

        val swipeMenuHandler = object : SwipeToDelete(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = menu_recyclerView.adapter as MenuAdapter
                val removeMenu = adapter.removeAt(viewHolder.adapterPosition)
                viewModel.deleteMenu(removeMenu)
            }
        }
        val itemMenuTouchHelper = ItemTouchHelper(swipeMenuHandler)
        itemMenuTouchHelper.attachToRecyclerView(menu_recyclerView)

        val swipeMemberHandler = object : SwipeToDelete(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = member_recyclerView.adapter as MemberAdapter
                val removeMember = adapter.removeAt(viewHolder.adapterPosition)
                viewModel.deleteMember(removeMember)
            }
        }
        val itemMemberTouchHelper = ItemTouchHelper(swipeMemberHandler)
        itemMemberTouchHelper.attachToRecyclerView(member_recyclerView)

        menu_add.setOnClickListener(this)
        member_add.setOnClickListener(this)
        check.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.menu_add -> insertMenuDialog(null)
            R.id.member_add -> insertMemberDialog(null)
            R.id.check -> {
                val intent = Intent(this, CheckActivity::class.java)
                intent.putExtra("groupId", groupId)
                startActivity(intent)
            }
        }
    }
    private fun insertMenuDialog(menu: Menu?) {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_insert, null)
        val menuId = menu?.id
        val newMenu = Menu()
        dialogView.dialog_price.visibility = View.VISIBLE
        dialogView.dialog_title.text = "메뉴 추가"
        if(menu!=null){
            dialogView.dialog_name.setText(menu.name)
            dialogView.dialog_price.setText(menu.price)
            newMenu.id = menuId
        }
        builder.setView(dialogView)
            .setPositiveButton("Save") { dialogInterface, i ->
                newMenu.name  = dialogView.dialog_name.text.toString()
                newMenu.price  = dialogView.dialog_price.text.toString()
                newMenu.groupId = groupId
                viewModel.insertMenu(newMenu)
            }
            .setNegativeButton("Cancel") { dialogInterface, i -> }
            .show()
    }
    private fun insertMemberDialog(member: Member?) {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_insert, null)
        val memberId = member?.id
        val newMember = Member()
        dialogView.dialog_title.text = "인원 추가"
        if (member!=null){
            dialogView.dialog_name.setText(member.name)
            newMember.id = memberId
        }
        builder.setView(dialogView)
            .setPositiveButton("Save") { dialogInterface, i ->
                newMember.name  = dialogView.dialog_name.text.toString()
                newMember.groupId = groupId
                viewModel.insertMember(newMember)
            }
            .setNegativeButton("Cancel") { dialogInterface, i -> }
            .show()
    }
}

