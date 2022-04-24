package com.juhnny.dailydiscovery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class Tab1PagerAdapter(fragmentActivity:FragmentActivity) : FragmentStateAdapter(fragmentActivity) {

    val fragments = listOf(GalleryFragment(), TopicsFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}