package com.juhnny.dailydiscovery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class InvitationPagerAdapter(fragmentActivity:FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    val fragments = mutableListOf(Invitation1Fragment(), Invitation2Fragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
        return fragments[position]
    }
}