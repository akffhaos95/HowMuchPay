package com.example.howmuch

import android.content.Context
import android.database.Cursor
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import kr.co.epublic.flowlayouttest.FixedGridLayout


class CheckAdapter(val context:Context?) :
    RecyclerView.Adapter<CheckAdapter.ViewHolder>() {
    private var menu: List<Menu> = listOf()
    private var member: List<Member> = listOf()

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
        private val name = itemView?.findViewById<TextView>(R.id.item_name)
        private val price = itemView?.findViewById<TextView>(R.id.item_price)
        private val linear = itemView?.findViewById<LinearLayout>(R.id.recycler_linear)
        private val check = itemView?.findViewById<FixedGridLayout>(R.id.item_check)
        private val cnt = itemView?.findViewById<TextView>(R.id.item_cnt)
        private var spinner = itemView?.findViewById<Spinner>(R.id.item_spinner)
        private val spinner_des = itemView?.findViewById<TextView>(R.id.item_spinner_des)

        fun bind (menu: Menu) {
            linear?.weightSum = 11.0f
            spinner?.visibility = View.VISIBLE
            spinner_des?.visibility = View.VISIBLE
            name?.text = menu.name
            price?.text = menu.price

            val item = arrayOfNulls<String>(member.size)
            var i = 0
            for(index in member) {
                item[i] = index.name
                i++
                val checkBox = CheckBox(context)
                checkBox.id = index.id!!
                checkBox.text = index.name
                checkBox.gravity = 1
                checkBox.setButtonDrawable(null)
                checkBox.setBackgroundResource(R.drawable.checkbox_selector)
                checkBox.setPadding(50,20,50,20)

                checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
                    if(isChecked){
                        Toast.makeText(context,index.name,Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(context,index.name+"test",Toast.LENGTH_SHORT).show()
                    }
                }
                check?.addView(checkBox)

                val arrayadapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, item)
                spinner!!.adapter = arrayadapter

                itemView.setOnLongClickListener{
                    if (check?.visibility==View.GONE) {
                        check?.visibility = View.VISIBLE
                    }
                    else{
                        check?.visibility = View.GONE
                    }
                    true
                }
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