package com.juhnny.dailydiscovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentTab1Binding
import com.juhnny.dailydiscovery.databinding.FragmentTab2Binding

class Tab2Fragment : Fragment() {

    val mainActivity by lazy { requireActivity() as MainActivity }
    val b by lazy { FragmentTab2Binding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mainActivity.setSupportActionBar(b.toolbar)
    }

    override fun onResume() {
        super.onResume()

        Toast.makeText(context, "Tab2 parent: "+ parentFragmentManager.fragments.size, Toast.LENGTH_SHORT).show()
        Toast.makeText(context, "Tab2 child: "+ childFragmentManager.fragments.size, Toast.LENGTH_SHORT).show()
    }
}