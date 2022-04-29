package com.juhnny.dailydiscovery

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import com.juhnny.dailydiscovery.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    val b:ActivityIntroBinding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.entrance.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
//            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, b.entrance, "introExpand")
            val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(b.entrance, 42, 75, 1, 1)
//            val optionsCompat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(b.entrance, )
            startActivity(intent, optionsCompat.toBundle())
            finish()
        }


        //앱 설치 후 처음 한번만 Invitation Activity를 띄우기
        //sharedPreference에 저장해놓자
        val prefs:SharedPreferences =


        val intent = Intent(this, InvitationActivity::class.java)
        val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(b.entrance, 42, 75, 1, 1)
        startActivity(intent, optionsCompat.toBundle())
        finish()
    }//onCreate()
}