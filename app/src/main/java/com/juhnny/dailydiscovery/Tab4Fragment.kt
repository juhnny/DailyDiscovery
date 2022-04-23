package com.juhnny.dailydiscovery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentTab4Binding

class Tab4Fragment : Fragment(){

    val mainActivity by lazy { requireActivity() as MainActivity }
    val b by lazy {FragmentTab4Binding.inflate(layoutInflater)}
    val fragments = listOf(TodayFragment(), TopicsFragment(), SubsFragment(), SettingsFragment())

    //오버라이드 되도록 BioFragment의 b를 var로 바꿔보자
    //그러면 여기서 바꿔진 바인딩으로 상속받은 메소드들이 작동하지 않을까?

    //부모 프래그먼트에서 뷰들의 기능을 미리 정의해놓고
    //자식 프래그먼트에서

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setSupportActionBar(b.toolbar)

//        //DrawerToggle 자체가 리스너를 상속
        val drawerToggle = ActionBarDrawerToggle(mainActivity, b.root, b.toolbar, R.string.openDrawerDesc, R.string.closeDrawerDesc)
        drawerToggle.syncState()
//        //아래 왜 안 먹힐까..
        drawerToggle.setDrawerIndicatorEnabled(true)
////        Toast.makeText(this, "" + drawerToggle.toolbarNavigationClickListener.toString(), Toast.LENGTH_SHORT).show()
////        drawerToggle.setToolbarNavigationClickListener {object : View.OnClickListener{
////            override fun onClick(p0: View?) {
////                Log.e("myTag2", "aaaaaaaaaaaaaa")
////                Toast.makeText(this@MainActivity, "ayo", Toast.LENGTH_SHORT).show()
////            }
////        }
////        }
////        Log.e("myListener", drawerToggle.toolbarNavigationClickListener.toString())
////        drawerToggle.toolbarNavigationClickListener.onClick(null)
        b.root.addDrawerListener(drawerToggle)


        val tvHeaderName = b.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_header_name)
        tvHeaderName.setOnClickListener {
            childFragmentManager.fragments.forEach{
                childFragmentManager.beginTransaction().hide(it).commit()
            }
            val trans = childFragmentManager.beginTransaction()
            if( ! childFragmentManager.fragments.contains(fragments[3])) trans.add(R.id.container_tab4, fragments[3])
            trans.show(fragments[3])
            trans.commit()
            b.root.closeDrawer(b.nav, true)
            Toast.makeText(context, "${it.id}", Toast.LENGTH_SHORT).show()
        }

        val tvHeaderInfo = b.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_header_info)
        tvHeaderInfo.setOnClickListener(object : OnHeaderClickListener(fragment = mainActivity.fragments[3]){
            override fun onClick(p0: View?) {
                super.onClick(p0)
            }
        })

        val btnLogin = b.nav.getHeaderView(0).findViewById<Button>(R.id.btn_login)
        //로그인 돼있는 상태면 버튼 숨기고 다른 헤더 정보 띄우기
        btnLogin.setOnClickListener {
            b.root.closeDrawer(b.nav)
            startActivity(Intent(context, LoginActivity::class.java))
        }


        val fragmentManager = childFragmentManager //교체할 container가 내 layout 안에 있다면 childFragmentManager를 쓴다.
//        val fragmentManager = mainActivity.supportFragmentManager
        fragmentManager.beginTransaction().add(R.id.container_tab4, fragments[0]).commit()

        b.nav.setNavigationItemSelectedListener {
//            fragmentManager.fragments.forEach {
//                fragmentManager.beginTransaction().hide(it).commit()
//            }

            val trans = fragmentManager.beginTransaction()
            when(it.itemId){
                R.id.nav_todays_topic -> {
                    Toast.makeText(context, "aa", Toast.LENGTH_SHORT).show()
//                    trans.show(fragments[0])
                    trans.replace(R.id.container_tab4, fragments[0])
                }
                R.id.nav_search -> {
                    Toast.makeText(context, "bb", Toast.LENGTH_SHORT).show()
//                    if( ! fragmentManager.fragments.contains(fragments[1])) trans.add(R.id.container_tab4, fragments[1])
//                    trans.show(fragments[1])
                    trans.replace(R.id.container_tab4, fragments[1])
                }
                R.id.nav_subs -> {
                    Toast.makeText(context, "cc", Toast.LENGTH_SHORT).show()
//                    if( ! fragmentManager.fragments.contains(fragments[2])) trans.add(R.id.container_tab4, fragments[2])
//                    trans.show(fragments[2])
                    trans.replace(R.id.container_tab4, fragments[2])
                }
                R.id.nav_settings -> {
                    Toast.makeText(context, "dd", Toast.LENGTH_SHORT).show()
//                    if( ! fragmentManager.fragments.contains(fragments[3])) trans.add(R.id.container_tab4, fragments[3])
//                    trans.show(fragments[3])
                    trans.replace(R.id.container_tab4, fragments[3])
                }
            }
            trans.commit()
            b.root.closeDrawer(b.nav, true)

            false
        }

        //DrawerNavigation의 header 수정
//        val headerName = b.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_header_name)
//        val headerInfo = b.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_header_info)
//        headerName.setText("aaa")
//        headerInfo.setText("more info")

    }//onViewCreated

    //수정 필요. childFragmentManager를 써야 할지, parentFragmentManager를 써야 할지 정해서 바꿔야 함
    open inner class OnHeaderClickListener(val fragment: Fragment) : View.OnClickListener{
        override fun onClick(p0: View?) {
            childFragmentManager.fragments.forEach{
                childFragmentManager.beginTransaction().hide(it).commit()
            }
            val trans = childFragmentManager.beginTransaction()
            if( ! childFragmentManager.fragments.contains(fragment)) trans.add(R.id.container_tab4, fragment)
            trans.show(fragment)
            trans.commit()
            b.root.closeDrawer(b.nav, true)
        }
    }

//    override fun onBackPressed() {
////        super.onBackPressed()
//        if(b.root.isDrawerOpen(b.nav)){
//            b.root.closeDrawer(b.nav, true)
//        } else {
//            super.onBackPressed()
//        }
//    }

//    override fun onSupportNavigateUp(): Boolean {
//        Toast.makeText(this, "Up", Toast.LENGTH_SHORT).show()
//
//        return super.onSupportNavigateUp()
//    }

}