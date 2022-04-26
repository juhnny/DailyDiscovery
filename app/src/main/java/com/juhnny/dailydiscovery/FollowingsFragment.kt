package com.juhnny.dailydiscovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentFollowingsBinding

class FollowingsFragment : Fragment() {

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

        val appCompatActivity = requireActivity() as AppCompatActivity
        appCompatActivity.setSupportActionBar(b.toolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)

        b.recycler.adapter = FollowRecyclerAdapter(requireContext(), this, follows)

        loadFollows()

        b.btnAdd.setOnClickListener{
            parentFragmentManager.beginTransaction().add(R.id.container_bnv, FollowingsFragment()).addToBackStack(null).commit()
        }
        b.btnReplace.setOnClickListener{
            parentFragmentManager.beginTransaction().replace(R.id.container_bnv, FollowingsFragment()).addToBackStack(null).commit()
        }
    }

    fun loadFollows(){
        for(i in 1..20){
            follows.add(Follow("name${i}", "I am like... ${i}",
                "https://a.cdn-hotels.com/gdcs/production87/d499/8196849a-5862-4e4f-86ec-a2c362157f74.jpg",
                "https://www.qmul.ac.uk/media/qmul/London-Bridge.jpg",
                "https://i.natgeofe.com/n/99790646-c5a4-4637-8f10-1d1c41ce3705/london_travel_2x3.jpg"))
        }
        b.recycler.adapter?.notifyDataSetChanged()
    }
}