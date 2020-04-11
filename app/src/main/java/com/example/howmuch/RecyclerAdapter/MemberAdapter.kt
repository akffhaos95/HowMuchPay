package com.example.howmuch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MemberAdapter(val itemClick: (Member) -> Unit, val delClick: (Member) -> Unit) :
    RecyclerView.Adapter<MemberAdapter.ViewHolder>() {
    private var member: List<Member> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.member_item,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return member.size
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(member[position])
    }
    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val member_name = itemView?.findViewById<TextView>(R.id.txt_member_name)

        fun bind (member: Member) {
            member_name?.text = member.name

            itemView.setOnClickListener{ itemClick(member) }
            itemView.setOnLongClickListener{
                delClick(member)
                true
            }
        }
    }
    fun setMember(member: List<Member>) {
        this.member = member
        notifyDataSetChanged()
    }
    fun removeAt(position: Int): Member {
        notifyItemRemoved(position)
        return member[position]
    }
}