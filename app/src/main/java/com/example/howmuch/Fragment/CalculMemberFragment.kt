package com.example.howmuch

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.calcul_member.*
import kotlinx.android.synthetic.main.calcul_member.recyclerView
import kotlinx.android.synthetic.main.calcul_menu.*

class CalculMemberFragment: Fragment() {
    private lateinit var viewModel: ViewModel
    var groupId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calcul_member, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resId:Int = R.anim.layout_animation_fall_down
        val animCon: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context,resId)
        recyclerView.layoutAnimation = animCon

        groupId = arguments!!.getInt("groupId")
        btn_member_add.setOnClickListener{
            insertDialog(null)
        }
        val adapter = MemberAdapter({ member ->
            insertDialog(member)
        }, { member ->
            deleteDialog(member)
        })

        val layoutManager = LinearLayoutManager(getActivity())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val swipeHandler = object : SwipeToDelete(context!!) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val adapter = recyclerView.adapter as MemberAdapter
                val removeMember = adapter.removeAt(viewHolder.adapterPosition)
                viewModel.deleteMember(removeMember)
            }
        }
        val itemTouchHelper = ItemTouchHelper(swipeHandler)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getAllMember(groupId).observe(this, Observer<List<Member>> { member ->
            adapter.setMember(member!!)
        })
        viewModel.getMemberCnt(groupId).observe(this, Observer { cnt ->
            calcul_member_member_cnt.text = cnt.toString()
        })
    }

    private fun deleteDialog(member: Member) {
        val builder = AlertDialog.Builder(getContext()!!)
        builder.setMessage("Delete selected member?")
            .setNegativeButton("No") {_, _ ->}
            .setPositiveButton("Yes") {_, _ ->
                viewModel.deleteMember(member)
            }
        builder.show()
    }
    private fun deleteAllDialog(groupId:Int?) {
        val builder = AlertDialog.Builder(getContext()!!)
        builder.setMessage("Delete all are you sure?")
            .setNegativeButton("No") {_, _ ->}
            .setPositiveButton("Yes") {_, _ ->
                viewModel.deleteAll(groupId)
            }
        builder.show()
    }
    private fun insertDialog(member: Member?) {
        val builder = AlertDialog.Builder(getContext()!!)
        val dialogView = layoutInflater.inflate(R.layout.dialog_insert, null)
        val dialogName = dialogView.findViewById<EditText>(R.id.txt_insert_Name)
        if (member==null){
            builder.setView(dialogView)
                .setPositiveButton("Save") { dialogInterface, i ->
                    val newMember = Member()
                    newMember.name  = dialogName.text.toString()
                    newMember.groupId = groupId
                    viewModel.insertMember(newMember)
                }
                .setNegativeButton("Cancel") { dialogInterface, i -> }
                .show()
        } else {
            dialogName.setText(member.name)
            builder.setView(dialogView)
                .setPositiveButton("Save") { dialogInterface, i ->
                    val umember = Member()
                    umember.name  = dialogName.text.toString()
                    umember.groupId = groupId
                    viewModel.insertMember(umember)
                }
                .setNegativeButton("Cancel") { dialogInterface, i -> }
                .show()
        }

    }
}