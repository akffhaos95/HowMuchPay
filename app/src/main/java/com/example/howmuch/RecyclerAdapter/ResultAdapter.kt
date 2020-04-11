package com.example.howmuch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kr.co.epublic.flowlayouttest.FixedGridLayout


class ResultAdapter(val context:Context?) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    private var menu: List<Menu> = listOf()
    private var member: List<Member> = listOf()

    override fun onCreateViewHolder(parent: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.result_menu_item,parent,false)
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
        val part_layout = itemView?.findViewById<FixedGridLayout>(R.id.part_layout)
        val price_now = itemView?.findViewById<TextView>(R.id.txt_res_menu_now)

        fun bind (menu: Menu) {
            menu_name?.text = menu.name
            menu_price?.text = menu.price
            for(index in member) {
                val checkBox = CheckBox(context)
                checkBox.id = index.id!!
                checkBox.text = index.name
                checkBox.gravity = 1
                checkBox.setButtonDrawable(null)
                checkBox.setBackgroundResource(R.drawable.checkbox_selector)
                checkBox.setPadding(50,20,50,20)

                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    Toast.makeText(context,index.name,Toast.LENGTH_SHORT).show()
                }
                part_layout?.addView(checkBox)
            }

            res_part?.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
                override fun onCheckedChanged(buttonView: CompoundButton, isChecked: Boolean) {
                    if (res_part!!.isChecked) {
                        part_layout?.visibility = View.VISIBLE
                    }
                    else{
                        part_layout?.visibility = View.GONE
                    }
                }
            })

            itemView.setOnLongClickListener{
                //uncheck all
                true
            }
        }
    }
    fun setMenu(menu: List<Menu>) {
        this.menu = menu
        notifyDataSetChanged()
    }
    fun setMember(member: List<Member>){
        this.member = member
        notifyDataSetChanged()
    }
}