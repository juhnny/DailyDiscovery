package com.juhnny.dailydiscovery

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.juhnny.dailydiscovery.databinding.ActivitySignupBinding

class SignupActivity : AppCompatActivity() {

    val b by lazy { ActivitySignupBinding.inflate(layoutInflater) }
    val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        b.btnSignup.setOnClickListener{ signUp() }
    }

    override fun onStart() {
        super.onStart()
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        Toast.makeText(this, "currentUser: $currentUser", Toast.LENGTH_SHORT).show()
        if(currentUser != null){
            //로그인 되어있을 경우 UI 업데이트
            //reloadUI()
        }
    }

    //iwibesr@naver.com
    fun signUp(){
        var isOk = false
        //이메일 및 비밀번호 인증 방식의 회원가입 - 입력된 이메일로 [인증확인]메일이 보내지고 사용자가 확인했을때 가입이 완료되는 방식
        val email = b.etEmail.text.toString()
        val pw = b.etPw.text.toString()
        val nickname = b.etNickname.text.toString()

//        if(email.equals("")) {
//            Toast.makeText(this, "이메일을 입력해주세요", Toast.LENGTH_SHORT).show()
//            return
//        }

//        fun isEmailDuplicated(email:String):Boolean{
//            //DB에서 중복 검사
//            TODO()
//            return false
//        }
//        if(isEmailDuplicated(email)){
//            Toast.makeText(this, "이미 가입된 이메일입니다", Toast.LENGTH_SHORT).show()
//        }

//        if(pw.equals("")) {
//            Toast.makeText(this, "비밀번호를 입력해주세요", Toast.LENGTH_SHORT).show()
//            return
//        }//email, pw는 Firebase 시스템이 검증해줄 거고..
        //닉네임은 여기서 검사
        if(nickname.equals("")) {
            Toast.makeText(this, "작가명을 입력해주세요", Toast.LENGTH_SHORT).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener {
            if (it.isSuccessful) { //이렇게만 해도 Firebase Auth에는 회원등록까지 되지만 인증이 안되어 있는 상태
                //입력된 이메일로 [인증확인] 메일 전송 및 전송 성공여부 확인
                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    if(it.isSuccessful) Toast.makeText(this, "전송된 이메일을 확인하고 인증에 성공하면 가입이 완료됩니다", Toast.LENGTH_SHORT).show()
                    else {
                        Toast.makeText(this, "인증용 이메일 전송에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                        Log.e("TAG", "createUser failed: ", it.exception)
                    }
                }
            } else {
                Log.e("TAG", "createUser failed", it.exception)
                Toast.makeText(this, "가입 실패 : ${it.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

}