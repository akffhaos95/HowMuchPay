package com.example.howmuch

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class ResultAdapter(val itemClick: (Menu) -> Unit, val LongClick: (Member) -> Unit) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    private var menu: List<Menu> = listOf()
    private var member: List<Member> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.calcul_item,parent,false)
        return ViewHolder(view)
    }
    override fun getItemCount(): Int {
        return menu.size
    }
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(menu[position])
    }
    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView!!) {
        val menu_name = itemView?.findViewById<TextView>(R.id.txt_res_menu_name)
        val menu_price = itemView?.findViewById<TextView>(R.id.txt_res_menu_price)
        val res_part = itemView?.findViewById<CheckBox>(R.id.btn_res_part)
        val part_layout = itemView?.findViewById<LinearLayout>(R.id.part_layout)

        fun bind (menu: Menu) {
            menu_name?.text = menu.name
            menu_price?.text = menu.price
            res_part?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    if (res_part!!.isChecked) {
                        part_layout?.visibility = View.VISIBLE
                    }
                    else{
                        part_layout?.visibility = View.INVISIBLE
                    }
                }
            })

            itemView.setOnLongClickListener{
                //uncheck all
                true
            }
        }
    }
    fun setResult(menu: List<Menu>) {
        this.menu = menu
        notifyDataSetChanged()
    }
}