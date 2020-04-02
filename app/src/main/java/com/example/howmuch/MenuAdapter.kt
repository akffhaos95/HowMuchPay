package com.example.howmuch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(val itemClick: (Menu) -> Unit, val delClick: (Menu) -> Unit) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private var menu: List<Menu> = listOf()
    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.menu_item,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return menu.size
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(menu[position])
    }
    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val menu_name = itemView?.findViewById<TextView>(R.id.txt_menu_name)
        val menu_price = itemView?.findViewById<TextView>(R.id.txt_menu_price)

        fun bind (menu: Menu) {
            menu_name?.text = menu.name
            menu_price?.text = menu.price

            itemView.setOnClickListener{ itemClick(menu) }
            itemView.setOnLongClickListener{
                delClick(menu)
                true
            }
        }
    }
    fun setMenu(menu: List<Menu>) {
        this.menu = menu
        notifyDataSetChanged()
    }
}