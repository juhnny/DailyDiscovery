package com.juhnny.dailydiscovery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.DividerItemDecoration
import com.juhnny.dailydiscovery.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {

    val b by lazy {FragmentTopicsBinding.inflate(layoutInflater)}
    val fragmets = mutableListOf<Fragment>()

    val topics = mutableListOf<Topic>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.recycler.adapter = TopicsRecyclerAdapter(requireContext(), topics)
        loadData()
//        b.recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    }

    fun loadData(){
        for(i in 0..99){
            var n = i + 1
            topics.add(Topic("$n", "주제$n", true, "2022/1/${n}", "2022/1/${n}"))
        }
        b.recycler.adapter?.notifyDataSetChanged()
    }

}