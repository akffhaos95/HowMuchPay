package com.example.howmuch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_check.*

class CheckActivity : AppCompatActivity() {
    private lateinit var viewModel: ViewModel
    var groupId = 0
    var price = 0
    var cnt = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check)

        if (intent.hasExtra("groupId")) {
            groupId = intent.getIntExtra("groupId",0)
        }

        val adapter = ResultAdapter(this)
        val layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        val resId:Int = R.anim.layout_animation_fall_down
        val animCon: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(this,resId)
        recyclerView.layoutAnimation = animCon

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getAllMenu(groupId).observe(this, Observer<List<Menu>> { menu ->
            adapter.setMenu(menu!!)
        })
        viewModel.getAllMember(groupId).observe(this, Observer { member ->
            adapter.setMember(member!!)
        })
        viewModel.getPrice(groupId).observe(this, Observer<String> { price ->
            this.price = price.toInt()
        })
        viewModel.getMemberCnt(groupId).observe(this, Observer<Int> { cnt ->
            this.cnt = cnt
        })
        res.setOnClickListener {
            title = (price/cnt).toString()
        }
    }
}
