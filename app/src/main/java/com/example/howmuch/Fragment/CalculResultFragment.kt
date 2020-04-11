package com.example.howmuch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.animation.LayoutAnimationController
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.calcul_member.recyclerView
import kotlinx.android.synthetic.main.calcul_result.*

class CalculResultFragment : Fragment() {
    private lateinit var viewModel: ViewModel
    var groupId = 0
    var price = 0
    var cnt = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calcul_result, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val resId:Int = R.anim.layout_animation_fall_down
        val animCon: LayoutAnimationController = AnimationUtils.loadLayoutAnimation(context,resId)
        recyclerView.layoutAnimation = animCon

        groupId = arguments!!.getInt("groupId")

        val adapter = ResultAdapter(context)
        val layoutManager = LinearLayoutManager(getActivity())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

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
        btn_res.setOnClickListener {
            txt_res.text = (price/cnt).toString()
        }
    }
}