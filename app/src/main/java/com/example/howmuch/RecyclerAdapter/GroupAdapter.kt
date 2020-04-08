package com.example.howmuch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupAdapter(val context: Context?, val itemClick: (Group) -> Unit, val delClick: (Group) -> Unit) :
    RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    private var group: List<Group> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.main_item,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return group.size
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
            viewHolder.bind(group[position])
    }
    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val group_name = itemView?.findViewById<TextView>(R.id.txt_group_name)
        val group_cnt = itemView?.findViewById<TextView>(R.id.txt_member_cnt)

        fun bind (group: Group) {
            group_name?.text = group.name
            group_cnt?.text = group.id.toString()

            itemView.setOnClickListener{ itemClick(group) }
            itemView.setOnLongClickListener{
                delClick(group)
                true
            }
        }
    }
    fun setGroup(group: List<Group>) {
        this.group = group
        notifyDataSetChanged()
    }
    fun removeAt(position: Int): List<Group> {
        notifyItemRemoved(position)
        return group
    }
}