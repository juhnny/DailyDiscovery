package com.juhnny.dailydiscovery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.juhnny.dailydiscovery.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val b by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    val auth by lazy { FirebaseAuth.getInstance() }

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
            startActivity(intent)
        }

        b.btnGoToSignup.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java )
            startActivity(intent)
        }

        b.btnLoginKakao.setOnClickListener { loginWithKakao() }
        b.btnLoginGoogle.setOnClickListener { loginWithGoogle() }
        b.btnLoginKakao.setOnClickListener { loginWithNaver() }


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

    private fun loginWithKakao(){
        Toast.makeText(this, "Kakao login", Toast.LENGTH_SHORT).show()
    }

    private fun loginWithGoogle(){
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .build()
        val client:GoogleSignInClient = GoogleSignIn.getClient(this, signInOptions)
        val intent = client.signInIntent
        resultLauncher.launch(intent)
    }

    private fun loginWithNaver(){
        Toast.makeText(this, "Naver login", Toast.LENGTH_SHORT).show()
    }

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), object: ActivityResultCallback<ActivityResult>{
        override fun onActivityResult(result: ActivityResult?) {
            Toast.makeText(baseContext, "resultLauncer came back", Toast.LENGTH_SHORT).show()
            try {
                //로그인 창을 그냥 닫아버리면 예외 발생
                val intent = result?.data
                val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
                val account = task.getResult()

                val email = account.email
                Log.e("TAG Google sign in account", "$email")

                startActivity(Intent(baseContext, MainActivity::class.java))
                finish()
            } catch (e:ApiException){
                Log.w("TAG Google Login Failed", "failure code : ${e.statusCode}")
            }
        }
    })

}