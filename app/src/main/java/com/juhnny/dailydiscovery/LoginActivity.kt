package com.juhnny.dailydiscovery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.preference.PreferenceManager
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.juhnny.dailydiscovery.databinding.ActivityLoginBinding
import retrofit2.Call
import retrofit2.Callback

class LoginActivity : AppCompatActivity() {

    val b by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    val auth by lazy { FirebaseAuth.getInstance() }
    val prefs by lazy { PreferenceManager.getDefaultSharedPreferences(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        //로그인 화면에서 할 일
        //둘러보기
        //  메인 화면으로 이동
        //이메일 로그인
        //  인증메일 재발송
        //  DB에 마지막 로그인 시간 업데이트
        //회원가입
        //  회원 정보 DB에 insert
        //  인증메일 발송
        //  인증메일 재발송
        //간편로그인
        //  회원 정보 DB에 insert
        b.tvLookaround.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

        b.btnGoToEmailLogin.setOnClickListener{
            val intent = Intent(this, LoginEmailActivity::class.java)
            emailLoginResultLauncher.launch(intent)
        }

        b.btnGoToSignup.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java )
            startActivity(intent)
        }

        b.btnLoginKakao.setOnClickListener { loginWithKakao() }
        b.btnLoginGoogle.setOnClickListener { loginWithGoogle() }
        b.btnLoginNaver.setOnClickListener { loginWithNaver() }

//        b.btnLogout.visibility = View.VISIBLE
        b.btnLogout.setOnClickListener{
            auth.signOut()

            //구글 로그아웃
//            val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN).build()
//            val client = GoogleSignIn.getClient(this, signInOptions)
//            client.signOut().addOnCompleteListener {
//                Toast.makeText(this, "구글 로그아웃", Toast.LENGTH_SHORT).show()
//            }
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    val emailLoginResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object:ActivityResultCallback<ActivityResult>{
        override fun onActivityResult(result: ActivityResult?) {
            if(result?.resultCode == RESULT_CANCELED) return
            else if(result?.resultCode == RESULT_OK){
                val intent = result.data
                if(intent == null) Toast.makeText(this@LoginActivity, "LoginAc intent가 null", Toast.LENGTH_SHORT).show()
                val didLogInSuccessed:Boolean? = intent?.getBooleanExtra("didLogInSuccessed", false)
                val email = intent?.getStringExtra("email") ?:"null@emailLoginResultLauncher"
                Toast.makeText(this@LoginActivity, "이메일 로그인 성공여부: ${didLogInSuccessed}, email: $email", Toast.LENGTH_SHORT).show()
                val userId = "Stub~"

                //서버에 로그인 날짜 업데이트
                updateLastLogInDatetime(email)
            }
        }
    })

    private fun loginWithKakao(){
        Toast.makeText(this, "Kakao login", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithNaver(){
        Toast.makeText(this, "Naver login", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithGoogle(){
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val client:GoogleSignInClient = GoogleSignIn.getClient(this, signInOptions)
        val intent = client.signInIntent
        googleLoginResultLauncher.launch(intent)
    }

    val googleLoginResultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object: ActivityResultCallback<ActivityResult>{
        override fun onActivityResult(result: ActivityResult?) {
            try {
                //로그인 창을 그냥 닫아버리면 예외 발생
                if(result?.resultCode == RESULT_CANCELED) return

                result?.run{
                    val intent = this.data
                    val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
                    val account = task.result
                    Log.e("LoginAc googleLoginResultLauncher", "${account.email}, ${account.id}, ${account.idToken}")

                    updateLastLogInDatetime(account.email ?:"null@googleLoginResultLauncher")
                }
            } catch (e:ApiException){
                Log.w("LoginAc googleLoginResultLauncher error", "${e.statusCode}")
            }
        }
    })

    private fun updateLastLogInDatetime(email: String){
        RetrofitHelper.getRetrofitInterface().updateLastLoginDatetime("Stub", email).enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                Log.e("LoginAc updateLastLogInDatetime() response", "${response.body()}")
                //DB에서 유저 정보 가져오기
                loadUserData(email)
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("LoginAc updateLastLogInDatetime() failure", "${t.message}")
            }
        })
    }

    //이메일 로그인 시
    fun loadUserData(email:String){
        //email을 기준으로 SELECT
        RetrofitHelper.getRetrofitInterface().loadMember(email).enqueue(object : Callback<Response<User>> {
            override fun onResponse(call: Call<Response<User>>, response: retrofit2.Response<Response<User>>) {
                val myResponse:Response<User>? = response.body()
                if(myResponse != null){
                    val resultMsg = myResponse.responseHeader.resultMsg
                    val body:ResponseBody<User>? = myResponse.responseBody
                    body?.run{
                        val user:User = this.items[0]
                        Log.e("LoginAc loadUserData resultMsg", resultMsg)
                        Log.e("LoginAc loadUserData user", user.toString())
                        //SharedPreference에 회원 정보 저장
                        saveUserDataToLocal(user)
                    }
                }
            }

            override fun onFailure(call: Call<Response<User>>, t: Throwable) {
                Log.e("LoginAc loadUserData Failure", "${t.message}")
            }
        })
    }//loadUserData

    private fun saveUserDataToLocal(user:User){
        //val prefs = getSharedPreferences("user", MODE_PRIVATE) //덮어쓰기
        val prefs = PreferenceManager.getDefaultSharedPreferences(this@LoginActivity) //덮어쓰기
        prefs.edit().putBoolean("isLoggedIn", false)
            .putString("memNo", user.memNo)
            .putString("memId", user.memId)
            .putString("email", user.email)
            .putString("nickname", user.nickname)
            .putString("profileMsg", user.profileMsg)
            .putString("signUpDatetime", user.signUpDatetime)
            .putString("lastLoginDatetime", user.lastLoginDatetime)
            .putString("snsType", user.snsType)
            .putString("snsId", user.snsId)
            .putString("snsConnectDate", user.snsConnectDate)
            .apply() //apply()를 쓰면 메모리에선 즉시 변경, 디스크에선 별도 쓰레드에서 비동기로 쓰기 작업
                    //어차피 네트워크 작업하는 별도 스레드에서 일게 할 함수라 commit()해도 되지만 그냥 apply()로 가보자. 대신 intro에서 검증하고 있으니까.
        Log.e("LoginAc saveUserDataToLocal() prefs",
            "${prefs.getString("email", "load email failed")}, ${prefs.getString("nickname", "load nickname failed")}")
        G.user = user
        Log.e("LoginAc saveUserDataToLocal() G.user", G.user.toString())

        goToMainActivity()
    }

    private fun goToMainActivity(){
        val intent2 = Intent(this@LoginActivity, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        startActivity(intent2)
        finish()
    }

}