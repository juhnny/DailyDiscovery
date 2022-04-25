package com.juhnny.dailydiscovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentFollowingsBinding

class FollowingsFragment : Fragment() {

    val mainActivity by lazy { requireActivity() as MainActivity }
    val b by lazy { FragmentFollowingsBinding.inflate(layoutInflater) }

    val follows = mutableListOf<Follow>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.recycler.adapter = FollowRecyclerAdapter(requireContext(), follows)

        loadFollows()
    }

    fun loadFollows(){
        for(i in 1..20){
            follows.add(Follow("name${i}", "I am like... ${i}", "http://iwibest.dothome.co.kr/profile2.jpg", "http://iwibest.dothome.co.kr/profile2.jpg", "http://iwibest.dothome.co.kr/profile2.jpg"))
        }
        b.recycler.adapter?.notifyDataSetChanged()
    }
}