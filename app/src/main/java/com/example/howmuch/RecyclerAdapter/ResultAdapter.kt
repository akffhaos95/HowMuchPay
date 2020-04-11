package com.example.howmuch

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.core.view.marginRight
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.internal.FlowLayout
import org.w3c.dom.Text

class ResultAdapter(val context:Context?) :
    RecyclerView.Adapter<ResultAdapter.ViewHolder>() {
    private lateinit var viewModel: ViewModel
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
        val part_layout = itemView?.findViewById<LinearLayout>(R.id.part_layout)
        val price_now = itemView?.findViewById<TextView>(R.id.txt_res_menu_now)

        fun bind (menu: Menu) {
            menu_name?.text = menu.name
            menu_price?.text = menu.price
            for(index in member) {
                val checkBox = CheckBox(context)
                checkBox.text = index.name
                checkBox.gravity = 1
                checkBox.setButtonDrawable(null)
                checkBox.setBackgroundResource(R.drawable.checkbox_selector)
                checkBox.setPadding(50,20,50,20)

                //checkBox.minWidth = 200

                /*<item name = "android:button">@null</item>
                <item name = "android:gravity">center</item>
                <item name = "android:background">@drawable/checkbox_selector</item>
                <item name = "android:textColor">@color/colorPrimary</item>
                <item name = "android:textStyle">bold</item>
                <item name = "android:padding">5dp</item>
                <item name = "android:paddingRight">8dp</item>
                <item name = "android:paddingLeft">8dp</item>
                <item name = "android:minWidth">50dp</item>*/
                //https://brunch.co.kr/@babosamo/57

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
    fun setMenu(menu: List<Menu>) {
        this.menu = menu
        notifyDataSetChanged()
    }
    fun setMember(member: List<Member>){
        this.member = member
        notifyDataSetChanged()
    }
}