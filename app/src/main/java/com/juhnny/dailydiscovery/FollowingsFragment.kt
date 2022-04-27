package com.juhnny.dailydiscovery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.juhnny.dailydiscovery.databinding.FragmentFollowingsBinding
import com.juhnny.dailydiscovery.databinding.FragmentTodayBinding

class FollowingsFragment : Fragment() {

    val b by lazy { FragmentFollowingsBinding.inflate(layoutInflater) }

    val follows = mutableListOf<Follow>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
                return b.root

        //바인딩을 inflate 해서 root뷰를 넘기는 방법
//        val binding = FragmentFollowingsBinding.inflate(layoutInflater)
//        binding.btnAdd.setOnClickListener{
//            parentFragmentManager.beginTransaction().add(R.id.container_bnv, FollowingsFragment()).addToBackStack(null).commit()
//        }
//        binding.btnReplace.setOnClickListener{
//            parentFragmentManager.beginTransaction().replace(R.id.container_bnv, FollowingsFragment()).addToBackStack(null).commit()
//        }
//        binding.recycler.adapter = FollowRecyclerAdapter(requireContext(), this, follows)
//
//        return binding.root

        //기존 방법 - inflater로 뷰를 만들어 리턴
//        val view = inflater.inflate(R.layout.fragment_followings, container, false)
//        view.findViewById<RecyclerView>(R.id.recycler).adapter = FollowRecyclerAdapter(requireContext(), this, follows)
//        view.findViewById<Button>(R.id.btn_add).setOnClickListener{
//            parentFragmentManager.beginTransaction().replace(R.id.container_bnv, FollowingsFragment()).addToBackStack(null).commit()
//        }
//        view.findViewById<Button>(R.id.btn_replace).setOnClickListener{
//            parentFragmentManager.beginTransaction().replace(R.id.container_bnv, FollowingsFragment()).addToBackStack(null).commit()
//        }
//        return view
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