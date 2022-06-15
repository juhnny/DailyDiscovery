package com.juhnny.dailydiscovery

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
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
    val activityOptions by lazy { ActivityOptionsCompat.makeScaleUpAnimation(b.entrance, b.entrance.width/2, b.entrance.height/2, 0, 0) }
    //둘째, 셋째 파라미터 : //The coordinates of the beginning of stretching
    //넷째, 다섯째 파라미터 : //The size of the area at which the stretch begins, where (0, 0) is used to indicate from scratch to full screen

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
        var isFirstRun = prefs.getBoolean("isFirstRun", true)
        Log.e("isFirstRun IntroAc: ", "$isFirstRun")

//        isFirstRun = true
        //첫 실행이면 Invitation Activity 실행
        if(isFirstRun) {
            val intent = Intent(this, InvitationActivity::class.java)
            startActivity(intent, activityOptions.toBundle())
            finish()
        }
        /***** 이상 확인사항 *****/

        //작업에 딜레이를 주는 방법들
        //TimerTask 사용
        //handlerToMain.sendEmptyMessageDelayed(123, 1000)
        //AlarmManager 사용

        //위 방식들은 모두 별도스레드로 동작
        //Thread.sleep()은 스레드를 멈춰서 화면이 멈춰버리기 때문에 안돼

        //TimeUnit.SECONDS.sleep(5)도 가능한 거 같은데?

        //handler에게 일정 시간 뒤에 스레드로 빈 메시지를 전해달라고 하는 방법
        //이것과 로그인 검증을 어떻게 합쳐야 좋을까? 해결!

    }//onCreate()

    private inner class OpenActivityHandler(looper:Looper, val activityClassToOpen:Class<*>):Handler(looper){
        override fun handleMessage(msg: Message) {
            val intent = Intent(baseContext, activityClassToOpen)
            val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(b.entrance, b.entrance.width/2, b.entrance.height/2, 0, 0)
            startActivity(intent, optionsCompat.toBundle())
            finish()
        }
    }

    lateinit var activityClass:Class<*>

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
                activityClass = MainActivity::class.java
            } else { //인증 미완료 시 로그인 화면으로 연결
                Log.e("TAG IntroAc", "인증 미완료")
                activityClass = LoginActivity::class.java
            }
        } else{ //로그인 안돼있을 시에도 로그인 화면으로 연결
            Log.e("TAG IntroAc", "로그인 X")
            activityClass = LoginActivity::class.java
//            handlerToMain.sendEmptyMessageDelayed(123, 1500)
        }

        val handler = OpenActivityHandler(Looper.getMainLooper(), activityClass)
        handler.sendEmptyMessageDelayed(123, 1500)
    }//onStart()



}