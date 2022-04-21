package com.juhnny.dailydiscovery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import com.juhnny.dailydiscovery.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val b:ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)
        
// ccc
//        b.root.transitionName = "introExpand"

        setSupportActionBar(b.toolbar)

        val drawerToggle = ActionBarDrawerToggle(this, b.root, b.toolbar, R.string.openDrawerDesc, R.string.closeDrawerDesc)
        drawerToggle.syncState()
        b.root.addDrawerListener(drawerToggle)

        b.nav.setNavigationItemSelectedListener {
            when(it.itemId){
                R.id.nav_todays_topic -> {
                    Toast.makeText(this, "aa", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this, EditorActivity::class.java))
                }
                R.id.nav_search -> Toast.makeText(this, "bb", Toast.LENGTH_SHORT).show()
                R.id.nav_subs -> Toast.makeText(this, "cc", Toast.LENGTH_SHORT).show()
                R.id.nav_settings -> Toast.makeText(this, "dd", Toast.LENGTH_SHORT).show()
            }
            b.root.closeDrawer(b.nav, true)

            false
        }

        //DrawerNavigation의 header 수정
//        val headerName = b.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_header_name)
//        val headerInfo = b.nav.getHeaderView(0).findViewById<TextView>(R.id.tv_header_info)
//        headerName.setText("aaa")
//        headerInfo.setText("more info")

        b.tvGotoWrite.setOnClickListener {
            startActivity(Intent(this, EditorActivity::class.java))
        }

    }//onCreate()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {


        return super.onCreateOptionsMenu(menu)
    }
}
