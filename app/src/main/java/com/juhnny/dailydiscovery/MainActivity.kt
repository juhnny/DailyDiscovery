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
import androidx.preference.PreferenceManager
import com.juhnny.dailydiscovery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val b by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val fragments = listOf(TodayFragment(), TopicsFragment(), Tab3Fragment(), Tab4Fragment())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        PreferenceManager.getDefaultSharedPreferences(this).edit().putBoolean("isFirstRun", false).commit() //있는 값 위에 쓰면 덮어쓰기
        Log.e("isFirstRun from MainActivity :", "${PreferenceManager.getDefaultSharedPreferences(this).getBoolean("isFirstRun", false)}")

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

    }//onCreate()


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

    // Activity 에도, Fragment 에도 OptionMenu 를 설정했다면, 둘이 ActionBar 에 섞여서 나타난다.
    // Activity 것이 먼저 나오고 Fragment 것이 뒤에 나온다.
    // 여러 Fragment 에서 OptionMenu 를 설정했다면, add 되는 순서대로 나온다.
    // 이 순서는 바굴 수 있는데, <item> 의 android:orderInCategory 옵션을 잘 조정하면 된다.
    //출처: https://aroundck.tistory.com/727 [돼지왕 놀이터]

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                Log.e("MainActivity - home", "")
                onBackPressed()
                return true //해당 (클릭) 이벤트를 다 소비했으면 true, 아직 다른 뷰들에서 사용해야 하면 false
            }
            else ->{
                Log.e("MainActivity - else", "")
                return false
            }
        }
        return super.onOptionsItemSelected(item)
    }
    // onOptionsItemSelected() 에서 return 값이 중요하다.
    // super 는 default 로 false 를 return 한다.
    // Fragment 에도 option menu 가 있는 경우에는 Activity 의 onOptionsItemSelected() 가 먼저 불리고,
    // return 값이 true 가 될때까지, activity 에 붙은 순서대로 fragment들의 onOptionsItemSelected() 가 호출된다.
    //출처: https://aroundck.tistory.com/727 [돼지왕 놀이터]

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
