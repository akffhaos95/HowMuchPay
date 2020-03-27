package com.example.howmuch

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_plus.*

class PlusActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_plus)

        //임시 데이터
        var menu_List = arrayListOf<Menu>(
            Menu("밥", "1000"),
            Menu("국", "2000")
        )

        //RecyclerView
        val mAdapter = MenuAdapter(this, menu_List, { menu ->
            Toast.makeText(this,"Menu ${menu.name}, Price ${menu.price}", Toast.LENGTH_SHORT).show()
        }, { menu->
            Toast.makeText(this, "Delete ${menu.name}",Toast.LENGTH_SHORT).show()
        })
        mRecyclerView.adapter = mAdapter

        val lm = LinearLayoutManager(this)
        mRecyclerView.layoutManager = lm
        mRecyclerView.setHasFixedSize(true)

        //Result Button
        btn_result.setOnClickListener {
            var member = txt_member.text.toString()
            var money = txt_money.text.toString()
            if (""== member||""==money) {
                Toast.makeText(this, "빈칸이 있습니다.", Toast.LENGTH_SHORT).show()
            } else if ("0"==member||"0"==money) {
                Toast.makeText(this, "숫자 0이 있습니다.", Toast.LENGTH_SHORT).show()
            } else {
                txt_result.text = (Integer.parseInt(money)/Integer.parseInt(member)).toString()
            }
        }

        //Menu Plus Button
        btn_menu_plus.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            val dialogView = layoutInflater.inflate(R.layout.dialog_plus_menu, null)
            val dialogName = dialogView.findViewById<EditText>(R.id.txt_plus_menu_name)
            val dialogPrice = dialogView.findViewById<EditText>(R.id.txt_plus_menu_price)
            builder.setView(dialogView).setPositiveButton("저장") {
                    dialogInterface, i-> Toast.makeText(this,"이름 : ${dialogName.text.toString()}",Toast.LENGTH_SHORT).show()
            } .setNegativeButton("취소") {
                    dialogInterface, i-> Toast.makeText(this,"가격 : ${dialogPrice.text.toString()}",Toast.LENGTH_SHORT).show()
            }.show()
        }
    }
}
