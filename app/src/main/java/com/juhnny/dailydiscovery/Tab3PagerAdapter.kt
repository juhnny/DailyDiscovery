package com.juhnny.dailydiscovery

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.viewpager2.adapter.FragmentStateAdapter

class Tab3PagerAdapter(fragment: Fragment): FragmentStateAdapter(fragment) {
    //부모 생성자한테 FragmentActivity를 넘기면 Activity 레벨의 매니저를 사용하게 됨.
    //그런데 Fragment 안에서 자손 Fragment를 만들 때는 해당 Fragment 레벨의 매니저(childFragmentManager. 내 아를 낳아주는)를 사용하도록 만드는 게 좋다.
    //자기 자식 Fragment는 자기가 관리하게 해야 hierarchy도 꼬이지 않고.. 다른 탭으로 갔다가 와도 보여주던 화면(자손 Fragment의 정보)이 보존되기 때문.
    //그러니 Fragment 안에 Fragment를 보여줄 때의 Adapter는 childFragmentManager를 사용하게 해야 된다.
    //부모 생성자에 Fragment를 주면 생성자 내부적으로 getChildFragmentManager() 해서 사용)

    //정리하면.. Activity에서 관리할 Fragment는 FragmentActivity를 받는 생성자 사용,
    //Fragment에서 관리할 자손 Fragment는 Fragment를 받는 생성자 사용

//    val fragments = arrayOfNulls<Fragment>(0)
    val fragments = listOf(FollowingsFragment(), FollowersFragment())

    override fun getItemCount(): Int = fragments.size

    override fun createFragment(position: Int): Fragment {
//        return fragments[position]!!
        return fragments[position]
    }
}