package com.juhnny.dailydiscovery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val b by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val fragments = listOf(TodayFragment(), TopicsFragment(), Tab3Fragment(), Tab4Fragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

//        b.root.transitionName = "introExpand"

        val fragmentManager = supportFragmentManager //Activity 레벨 매니저
        fragmentManager.beginTransaction().add(R.id.container_bnv, fragments[0]).commit()

        b.bnv.setOnItemSelectedListener {
            Log.e("myTag", "이 매니저가 관리하던 Fragment 개수: ${fragmentManager.fragments.size}")
            Log.e("myTag", "이 매니저가 관리하던 Fragment 종류: ${fragmentManager.fragments.toString()}")

            fragmentManager.fragments.forEach {
                fragmentManager.beginTransaction().hide(it).commit()
            }

            val trans = fragmentManager.beginTransaction()
            when(it.itemId){
                R.id.bnv_tab1 -> {
                    trans.show(fragments[0])
                }
                R.id.bnv_tab2 -> {
                    if( ! fragmentManager.fragments.contains(fragments[1])) trans.add(R.id.container_bnv, fragments[1])
                    trans.show(fragments[1])
                }
                R.id.bnv_tab3 -> {
                    if( ! fragmentManager.fragments.contains(fragments[2])) trans.add(R.id.container_bnv, fragments[2])
                    trans.show(fragments[2])
                }
                R.id.bnv_tab4 -> {
                    if( ! fragmentManager.fragments.contains(fragments[3])) trans.add(R.id.container_bnv, fragments[3])
                    trans.show(fragments[3])
                }
            }
            trans.commit()

            true
        }

//        b.bnv.setOnItemSelectedListener {
//            Log.e("myTag", "이 매니저가 관리하는 Fragment 개수: ${fragmentManager.fragments.size}")
//
//            val trans = fragmentManager.beginTransaction()
//            when(it.itemId){
//                R.id.bnv_tab1 -> {
//                    if( ! fragmentManager.fragments.contains(fragments[0])) trans.replace(R.id.container_bnv, fragments[0])
//                }
//                R.id.bnv_tab2 -> {
//                    if( ! fragmentManager.fragments.contains(fragments[1])) trans.replace(R.id.container_bnv, fragments[1])
//                }
//                R.id.bnv_tab3 -> {
//                    if( fragmentManager.fragments.contains(fragments[2])) Log.e("myTag", "이 프래그먼트가 포함돼있음")
//                    if( ! fragmentManager.fragments.contains(fragments[2])) trans.replace(R.id.container_bnv, fragments[2])
//                }
//                R.id.bnv_tab4 -> {
//                    if( ! fragmentManager.fragments.contains(fragments[3])) trans.replace(R.id.container_bnv, fragments[3])
//                }
//            }
//            trans.commit()
//            true
//        }

    }//onCreate()

//    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        return super.onCreateOptionsMenu(menu)
//    }//onCreateOptionsMenu

    //글쓰기가 완료되면 두번째 탭 열기
    val editorResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
        Toast.makeText(this, "resultLauncher came back", Toast.LENGTH_SHORT).show()
        if(it.resultCode == RESULT_OK) {
            supportFragmentManager.fragments.forEach{
                supportFragmentManager.beginTransaction().hide(it)
            }
            val trans = supportFragmentManager.beginTransaction()
            if( ! supportFragmentManager.fragments.contains(fragments[1])) trans.add(R.id.container_bnv, fragments[1])
            trans.show(fragments[1])
            trans.commit()

            b.bnv.menu.getItem(1).isChecked = true
        } else {
            Toast.makeText(this, "결과 못 받음", Toast.LENGTH_SHORT).show()
        }
    })

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Toast.makeText(this, "MainActivity - home clicked", Toast.LENGTH_SHORT).show()
                onBackPressed()
            }
        }
        Toast.makeText(this, "MainActivity - item clicked", Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }

//    override fun onBackPressed() {
//        val frag = supportFragmentManager.findFragmentById(R.id.tab3_fragment_root)
//        if(frag != null) {
//            if(frag.parentFragmentManager.backStackEntryCount > 0){
//                Toast.makeText(this, "1", Toast.LENGTH_SHORT).show()
//                frag.parentFragmentManager.popBackStack()
//            }else{
//                Toast.makeText(this, "2", Toast.LENGTH_SHORT).show()
//                super.onBackPressed()
//            }
//        }
//        Toast.makeText(this, "3", Toast.LENGTH_SHORT).show()
//    }


}
