package com.example.howmuch

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.calcul_member.*

class CalculResultFragment : Fragment() {
    private lateinit var viewModel: ViewModel
    var groupId = 0

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.calcul_result, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        groupId = arguments!!.getInt("groupId")

        val adapter = MemberAdapter({ member ->

        }, { member ->

        })
        val layoutManager = LinearLayoutManager(getActivity())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = layoutManager
        recyclerView.setHasFixedSize(true)

        viewModel = ViewModelProviders.of(this).get(ViewModel::class.java)
        viewModel.getAllMember(groupId).observe(this, Observer<List<Member>> { member ->

        })
        viewModel.getMemberCnt(groupId).observe(this, Observer { cnt ->

        })
    }
}