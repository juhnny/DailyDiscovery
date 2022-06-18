package com.juhnny.dailydiscovery

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.*
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.juhnny.dailydiscovery.databinding.FragmentTab4Binding

class Tab4Fragment : Fragment(){

    val mainActivity by lazy { requireActivity() as MainActivity }
    val b by lazy {FragmentTab4Binding.inflate(layoutInflater)}


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View? {
        setHasOptionsMenu(true)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        Log.e("myTag", "onViewCreated")
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setSupportActionBar(b.toolbar)
        mainActivity.supportActionBar?.setDisplayShowTitleEnabled(false)

//        //DrawerToggle 자체가 리스너를 상속
        val drawerToggle = ActionBarDrawerToggle(mainActivity, b.root, b.toolbar, R.string.openDrawerDesc, R.string.closeDrawerDesc)
        drawerToggle.syncState() //toggle 아이콘을 툴바에 띄우고 누르면 열리게 함
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

//        val tvHeaderName = b.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_header_name)
//        val tvHeaderInfo = b.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_header_info)
//
//        val btnLogin = b.nav.getHeaderView(0).findViewById<Button>(R.id.btn_login)
//        val btnLogout = b.nav.getHeaderView(0).findViewById<Button>(R.id.btn_logout)
//        //추가하기. 로그인 돼있는 상태면 버튼 숨기고 다른 헤더 정보 띄우기
//        if(FirebaseAuth.getInstance().currentUser == null) {
//            btnLogin.visibility = View.VISIBLE
//            btnLogout.visibility = View.GONE
//        } else {
//            btnLogin.visibility = View.GONE
//            btnLogout.visibility = View.VISIBLE
//        }
//        btnLogin.setOnClickListener {
//            b.root.closeDrawer(b.nav) //animation은 기본이 true
//            startActivity(Intent(context, LoginActivity::class.java))
//        }
//        btnLogout.setOnClickListener {
//            b.root.closeDrawer(b.nav)
//            FirebaseAuth.getInstance().signOut()
//            Toast.makeText(requireContext(), "로그아웃 완료", Toast.LENGTH_SHORT).show()
//            startActivity(Intent(context, MainActivity::class.java))
//            requireActivity().finish()
//        }


        //교체할 container가 내 layout 안에 있다면 childFragmentManager를 쓴다.
        val bundle = Bundle()
        bundle.putString("email", G.user?.email)
        childFragmentManager.beginTransaction().add(R.id.container_tab4, MyBioFragment::class.java, bundle, "MYBIO_FRAG").addToBackStack(null).commit() //기본 화면

        b.nav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_notice -> {
                    Toast.makeText(context, "Tab4 - aa", Toast.LENGTH_SHORT).show()
                    //공지사항 화면 열기. 프래그먼트로 만들어보자.
                    //MyBioFragment는 닫고, NoticeFragment는 열고
                    childFragmentManager.beginTransaction()
                        .hide(childFragmentManager.findFragmentByTag("MYBIO_FRAG")!!)
                        .add(R.id.tab4_fragment_root, NoticeFragment(), "NOTICE_FRAG")
                        .addToBackStack(null)
                        .commit()
                }
                R.id.nav_appstore -> {
                    Toast.makeText(context, "Tab4 - bb", Toast.LENGTH_SHORT).show()
                    //리뷰/평점 남기기 위해 플레이스토어 앱페이지 열기
                    //market:// 프로토콜은 playstore 뿐만 아니라 다른 마켓 앱에서도 반응한다.
                    //다른 마켓 말고 플레이스토어를 먼저 타겟하겠다면.. https://stackoverflow.com/a/28090925
                    val packageName = context?.packageName
                    try {
                        val rateIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=${packageName}"))
                        startActivity(rateIntent)
                    }catch (e: ActivityNotFoundException){
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + packageName)))
                    }
                }
                R.id.nav_opinion -> {
                    Toast.makeText(context, "Tab4 - cc", Toast.LENGTH_SHORT).show()
                    //이메일 앱 띄우기
                    sendEmailToAdmin(requireContext(), arrayOf("opnrstudio@gmail.com"), "개발자에게 메일 보내기")
                }
                R.id.nav_settings -> {
                    Toast.makeText(context, "Tab4 - dd", Toast.LENGTH_SHORT).show()
                    //설정 액티비티 열기
                    startActivity(Intent(context, SettingsActivity::class.java))
                    //설정 프래그먼트 열기
//                    childFragmentManager.beginTransaction()
//                        .hide(fragments[0])
//                        .add(R.id.tab4_fragment_root, SettingsFragment(), "SETTINGS_FRAG")
//                        .addToBackStack(null)
//                        .commit()
                }
            }
            b.root.closeDrawer(b.nav, true)

            false
        }

        //DrawerNavigation의 header 수정
//        val headerName = b.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_header_name)
//        val headerInfo = b.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_header_info)
//        headerName.setText("aaa")
//        headerInfo.setText("more info")

    }//onViewCreated

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
//        inflater.inflate(R.menu.options_tab4, menu) //나는 내가 쓸 메뉴 만들고
        super.onCreateOptionsMenu(menu, inflater) //부모는 기본적인 메뉴 만들고
    }

    override fun onPrepareOptionsMenu(menu: Menu) { //뭐 하는 단계?
        super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_tab4_notification -> Toast.makeText(requireContext(), "Tab4 - 알림", Toast.LENGTH_SHORT).show()
        }
        return super.onOptionsItemSelected(item)
    }

    fun sendEmailToAdmin(context: Context, receivers:Array<String>, title:String){
        val intent = Intent(Intent.ACTION_SEND)
        intent.putExtra(Intent.EXTRA_EMAIL, receivers)
        intent.putExtra(Intent.EXTRA_SUBJECT, title)

        val deviceModel = Build.MODEL
        val deviceId = Settings.Secure.getString(context.contentResolver, Settings.Secure.ANDROID_ID)
        val deviceOs = Build.VERSION.RELEASE.toString()
        val appVersion = BuildConfig.VERSION_NAME
        val appVersionCode = BuildConfig.VERSION_CODE
        intent.putExtra(Intent.EXTRA_TEXT, "Device Model : ${deviceModel}\n" +
                "SSAID: ${deviceId}\n" +
                "OS : ${deviceOs}\n" +
                "App Version(SDK) : ${appVersion}(${appVersionCode}) \n" +
                "내용 : ")
        intent.type = "message/rfc822"
        startActivity(intent)

    }
    //device id => 기기의 고유한 하드웨어 식별자 Android ID (SSAID)
    //device model => 어떤 제품인지 (안드로이드 기기의 제품 모델명)
    //device os => 안드로이드 버전 몇인지
    //app version => 해당 앱의 버전이 몇인지

    //Drawer Navigation에서 메뉴를 클릭하면 해당하는 프래그먼트를 띄우는 커스텀 리스너
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