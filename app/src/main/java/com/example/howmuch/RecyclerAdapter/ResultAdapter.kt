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

        val check = itemView?.findViewById<FixedGridLayout>(R.id.item_check)
        val cnt = itemView?.findViewById<TextView>(R.id.item_cnt)
        var spiner = itemView?.findViewById<Spinner>(R.id.item_spiner)

        fun bind (menu: Menu) {
            spiner?.visibility = View.VISIBLE
            name?.text = menu.name
            price?.text = menu.price
            cnt?.text = (menu.price!!.toInt()/member.size.toInt()).toString()

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
                    Toast.makeText(context,index.name,Toast.LENGTH_SHORT).show()
                }
                check?.addView(checkBox)
                val arrayadapter = ArrayAdapter(context!!, android.R.layout.simple_spinner_item, item)
                spiner!!.adapter = arrayadapter
            }

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
    fun setMenu(menu: List<Menu>) {
        this.menu = menu
        notifyDataSetChanged()
    }
    fun setMember(member: List<Member>){
        this.member = member
        notifyDataSetChanged()
    }
}