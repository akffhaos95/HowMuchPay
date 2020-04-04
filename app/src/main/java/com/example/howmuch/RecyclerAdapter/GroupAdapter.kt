package com.example.howmuch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GroupAdapter(val itemClick: (Group) -> Unit, val delClick: (Group) -> Unit) :
    RecyclerView.Adapter<GroupAdapter.ViewHolder>() {
    private var group: List<Group> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return group.size
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(group[position])
    }
    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val group_name = itemView?.findViewById<TextView>(R.id.txt_menu_name)

        fun bind (group: Group) {
            group_name?.text = group.name

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
}