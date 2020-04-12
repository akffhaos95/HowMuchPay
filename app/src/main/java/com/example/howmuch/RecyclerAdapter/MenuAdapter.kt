package com.example.howmuch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MenuAdapter(val itemClick: (Menu) -> Unit) :
    RecyclerView.Adapter<MenuAdapter.ViewHolder>() {
    private var menu: List<Menu> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.recycler_item,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return menu.size
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(menu[position])
    }
    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val name = itemView?.findViewById<TextView>(R.id.item_name)
        val price = itemView?.findViewById<TextView>(R.id.item_price)

        fun bind (menu: Menu) {
            name?.text = menu.name
            price?.text = menu.price

            itemView.setOnClickListener{ itemClick(menu) }
        }
    }
    fun setMenu(menu: List<Menu>) {
        this.menu = menu
        notifyDataSetChanged()
    }
    fun removeAt(position: Int): Menu {
        notifyItemRemoved(position)
        return menu[position]
    }
}