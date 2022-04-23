package com.juhnny.dailydiscovery

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.juhnny.dailydiscovery.databinding.FragmentFollowersBinding

class FollowersFragment : Fragment() {

    val b by lazy { FragmentFollowersBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

}