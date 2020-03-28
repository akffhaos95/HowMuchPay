package com.example.howmuch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(val context: Context, val menu_List: List<Db_menu_Entity>, val itemClick: (Db_menu_Entity) -> Unit, val delClick: (Db_menu_Entity) -> Unit) :
    RecyclerView.Adapter<MenuAdapter.Holder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(context).inflate(R.layout.menu_item, parent, false)
        return Holder(view, itemClick, delClick)
    }
    override fun getItemCount(): Int {
        return menu_List.size
    }
    inner class Holder(itemView: View?, itemClick: (Db_menu_Entity) -> Unit, delClick: (Db_menu_Entity) -> Unit) : RecyclerView.ViewHolder(itemView!!) {
        val menu_name = itemView?.findViewById<TextView>(R.id.txt_menu_name)
        val menu_price = itemView?.findViewById<TextView>(R.id.txt_menu_price)
        fun bind (menu: Db_menu_Entity, context: Context) {
            menu_name?.text = menu.name
            menu_price?.text = menu.price

            itemView.setOnClickListener{ itemClick(menu)}
            itemView.setOnLongClickListener{ delClick(menu)
                return@setOnLongClickListener true }
        }
    }
    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder?.bind(menu_List[position], context)
    }
}