package com.juhnny.dailydiscovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentBioOthersBinding

class OthersBioFragment : Fragment() {

    open lateinit var b:FragmentBioOthersBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        b = FragmentBioOthersBinding.inflate(layoutInflater)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.tv.setOnClickListener {
            b.tv.text = "${System.currentTimeMillis()}"
        }
    }
}