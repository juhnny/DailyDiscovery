package com.juhnny.dailydiscovery

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityOptionsCompat
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.juhnny.dailydiscovery.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    val b:ActivityIntroBinding by lazy { ActivityIntroBinding.inflate(layoutInflater) }
    val auth by lazy { FirebaseAuth.getInstance() }
    //FirebaseApp이라는 앱 실행과 함께 자동실행되는 Content Provider의 객체를 소환
    //비슷한 애로 FirebaseInitProvider라는 Content Provider도 있다.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        /***** 앱 실행 시 확인사항 *****/
        //필수 업데이트 여부(서버에서 읽어오기) - 더 이상 진행되지 않게 안내 후 앱 종료
        //앱 첫 실행 여부 - 첫 실행에서만 초대장 화면 띄우기
        //로그인 여부 - 돼있으면 DB에서 회원 정보 읽어와 User 객체에 저장 후 메인 화면으로, 안돼있으면 로그인 화면으로..


        //앱 설치 후 처음 한번만 Invitation Activity 띄우기
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
        if( ! prefs.contains("isFirstRun")) editor.putBoolean("isFirstRun", true).commit() //처음엔 항목이 없을테니..
        val isFirstRun = prefs.getBoolean("isFirstRun", true)
        Log.e("isFirstRun IntroAc: ", "$isFirstRun")

        //첫 실행이면 Invitation Activity 실행
        if(isFirstRun) {
            val intent = Intent(this, InvitationActivity::class.java)
            val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(b.entrance, 42, 75, 1, 1)
            startActivity(intent, optionsCompat.toBundle())
            finish()
        }
        /////////마무리////////


        //MainActivity로 바로 연결.
        val openMainActivity = {
            val intent = Intent(this, MainActivity::class.java)
//            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, b.entrance, "introExpand")
            val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(b.entrance, 42, 75, 1, 1)
//            val optionsCompat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(b.entrance, )
            startActivity(intent, optionsCompat.toBundle())
            finish()
        }

        b.entrance.setOnClickListener {
            openMainActivity()
        }


    }//onCreate()

    override fun onStart() {
        super.onStart()
        //로그인 여부 확인 후 회원 데이터 로드 및 화면 전환

        //한국어로 설정 - 인증메일 언어 로컬화
        auth.setLanguageCode("ko")
        //액티비티를 초기화할 때 사용자가 현재 로그인되어 있는지(nun-null) 확인하고 걸맞는 작업 해주기
        //FirebaseUser 객체는 앱 세션 내에서 캐시를 사용하므로 변경이 있을 수 있으니 reload() 권장
        //어차피 여기는 앱 실행 후 첫 화면이라 기존 캐시 데이터가 없다.
        //인터넷이 끊긴 상황에서는? 로그인을 풀지는 말아보자. 어차피 글 업로드나 사진 로딩에 실패할 것..
        //그러려면 여기서는 reload()를 안 쓰는 게 낫겠네..
        val currentUser:FirebaseUser? = auth.currentUser
        if(currentUser != null){ //로그인 되어있을 경우
            Log.e("TAG IntroAc", "로그인 O, email:${currentUser.email}")
            //인증이 돼있나 확인
            if(currentUser.isEmailVerified){ //인증 완료시에만 메인 액티비티 스타트
                Log.e("TAG IntroAc", "인증 완료")
                startActivity(Intent(this, MainActivity::class.java))
                finish()
            } else { //인증 미완료 시 로그인 화면으로 연결
                Log.e("TAG IntroAc", "인증 미완료")
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
        } else{ //로그인 안돼있을 시에도 로그인 화면으로 연결
            Log.e("TAG IntroAc", "로그인 X")
            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }

}