package com.juhnny.dailydiscovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentTab3Binding

class Tab3Fragment : Fragment() {

    val mainActivity by lazy { requireActivity() as MainActivity }
    val b by lazy { FragmentTab3Binding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        mainActivity.setSupportActionBar(b.toolbar)
//        mainActivity.supportActionBar?.setDisplayShowTitleEnabled(false)

        b.pagerTab3.adapter = Tab3PagerAdapter(requireActivity())

    }



}