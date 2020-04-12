package com.example.howmuch

import android.content.Intent
import android.os.Bundle
import android.view.View
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
import kotlinx.android.synthetic.main.activity_insert.*

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
        val layoutMemberManager = LinearLayoutManager(this)
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
        val dialogName = dialogView.findViewById<EditText>(R.id.txt_insert_Name)
        val dialogPrice = dialogView.findViewById<EditText>(R.id.txt_insert_Price)
        val menuId = menu?.id
        val newMenu = Menu()
        if(menu!=null){
            dialogName.setText(menu.name)
            dialogPrice.setText(menu.price)
            newMenu.id = menuId
        }
        builder.setView(dialogView)
            .setPositiveButton("Save") { dialogInterface, i ->
                newMenu.name  = dialogName.text.toString()
                newMenu.price  = dialogPrice.text.toString()
                newMenu.groupId = groupId
                viewModel.insertMenu(newMenu)
            }
            .setNegativeButton("Cancel") { dialogInterface, i -> }
            .show()
    }
    private fun insertMemberDialog(member: Member?) {
        val builder = AlertDialog.Builder(this)
        val dialogView = layoutInflater.inflate(R.layout.dialog_insert, null)
        val dialogName = dialogView.findViewById<EditText>(R.id.txt_insert_Name)
        val memberId = member?.id
        val newMember = Member()
        if (member!=null){
            dialogName.setText(member.name)
            newMember.id = memberId
        }
        builder.setView(dialogView)
            .setPositiveButton("Save") { dialogInterface, i ->
                newMember.name  = dialogName.text.toString()
                newMember.groupId = groupId
                viewModel.insertMember(newMember)
            }
            .setNegativeButton("Cancel") { dialogInterface, i -> }
            .show()
    }
}

