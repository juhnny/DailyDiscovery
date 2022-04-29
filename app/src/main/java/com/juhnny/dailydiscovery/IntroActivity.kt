package com.juhnny.dailydiscovery

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityOptionsCompat
import androidx.preference.PreferenceManager
import com.juhnny.dailydiscovery.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    val b:ActivityIntroBinding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        //앱 설치 후 처음 한번만 Invitation Activity를 띄우기
        //sharedPreference에 저장해놓자
        //Main Activity가 만들어지면(둘러보기를 누르거나 로그인에 성공하면) isFirstRun = true로 변경

        //참고
        //1. PreferenceManager.getDefaultSharedPreference() - app 전체의 기본 환경설정
        //androidx 버전의 PreferenceManager를 써야함. android 패키지의 클래스는 deprecated in api level 29
        //preference로 검색해서 그룹ID androidx.preference 라이브러리 추가
        //2. context.getSharedPreferences() - app 전체에서 사용되는 환경설정이 여러개 필요한 경우 식별자를 붙여 사용
        //3. getPreferences() - 해당 Activity에 속하는 환경설정
        val prefs:SharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        val editor =  prefs.edit()
        val isFirstRun = prefs.getString("isFirstRun", "false").toBoolean()
        Log.e("isFirstRun: ", "$isFirstRun")

        if(isFirstRun) {
            val intent = Intent(this, InvitationActivity::class.java)
            val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(b.entrance, 42, 75, 1, 1)
            startActivity(intent, optionsCompat.toBundle())
            finish()
        }

        val openMainActivity = {
            val intent = Intent(this, MainActivity::class.java)
//            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, b.entrance, "introExpand")
            val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(b.entrance, 42, 75, 1, 1)
//            val optionsCompat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(b.entrance, )
            startActivity(intent, optionsCompat.toBundle())
            finish()
        }

        //TEST!!!!!!!!!!!!!!!!!!
        startActivity(Intent(this, SignupActivity::class.java))

        b.entrance.setOnClickListener {
            openMainActivity()
        }


    }//onCreate()
}