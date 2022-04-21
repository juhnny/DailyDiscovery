package com.juhnny.dailydiscovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentBioBinding

open class BioFragment : Fragment() {

    val b by lazy { FragmentBioBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.tv.setOnClickListener {
            b.tv.text = "${System.currentTimeMillis()}"
        }
    }
}