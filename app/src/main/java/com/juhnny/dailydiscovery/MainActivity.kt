package com.juhnny.dailydiscovery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val b:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    val fragments = listOf<Fragment>(TodayFragment(), TopicFragment(), TodayFragment(), TopicFragment())
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

//        b.root.transitionName = "introExpand"
        supportFragmentManager.beginTransaction().add(R.id.container, fragments[0]).commit()

        setSupportActionBar(b.toolbar)

        val drawerToggle = ActionBarDrawerToggle(this, b.root, b.toolbar, R.string.openDrawerDesc, R.string.closeDrawerDesc)
        drawerToggle.syncState()
        b.root.addDrawerListener(drawerToggle)

        b.nav.setNavigationItemSelectedListener {
            supportFragmentManager.fragments.forEach {
                supportFragmentManager.beginTransaction().hide(it).commit()
            }

            val trans = supportFragmentManager.beginTransaction()
            when(it.itemId){
                R.id.nav_todays_topic -> {
                    Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show()
                    trans.show(fragments[0])
                }
                R.id.nav_search -> {
                    Toast.makeText(this, "bb", Toast.LENGTH_SHORT).show()
                    if( ! supportFragmentManager.fragments.contains(fragments[1])) trans.add(R.id.container, fragments[1])
                    trans.show(fragments[1])
                }
                R.id.nav_subs -> {
                    Toast.makeText(this, "cc", Toast.LENGTH_SHORT).show()
                    Intent(this, EditorActivity::class.java)
                    editorResultLauncher.launch(intent)
                }
                R.id.nav_settings -> Toast.makeText(this, "dd", Toast.LENGTH_SHORT).show()
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



    }//onCreate()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        return super.onCreateOptionsMenu(menu)
    }//onCreateOptionsMenu

    val editorResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback {
        Toast.makeText(this, "resultLauncher came back", Toast.LENGTH_SHORT).show()
        if(it.resultCode == RESULT_OK) {
            supportFragmentManager.fragments.forEach{
                supportFragmentManager.beginTransaction().hide(it)
            }
            val trans = supportFragmentManager.beginTransaction()
            if( ! supportFragmentManager.fragments.contains(fragments[1])) trans.add(R.id.container, fragments[1])
            trans.show(fragments[1])
            trans.commit()
        }
    })
}
