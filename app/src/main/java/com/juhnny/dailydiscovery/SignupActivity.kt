package com.juhnny.dailydiscovery

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.juhnny.dailydiscovery.databinding.ActivitySingupBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {

    val b by lazy { ActivitySingupBinding.inflate(layoutInflater) }
    val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        b.tvGuide.visibility = View.GONE

        /***** 회원가입 화면에서 할 일 *****/
        //이메일/비번 회원가입 시
        //  닉네임 입력받기(중복 가능)
        //  회원 정보 DB에 insert
        //  인증메일 발송
        //  인증메일 재발송

        //간편로그인으로 회원가입 시
        //  닉네임 입력받기
        //  회원정보 DB에 insert

        b.btnSignup.setOnClickListener{ signUp() }
    }

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
            Toast.makeText(this, "작가명을 입력해주세요", Toast.LENGTH_LONG).show()
            return
        }

        auth.createUserWithEmailAndPassword(email, pw).addOnCompleteListener {
            if (it.isSuccessful) { //이렇게만 해도 Firebase Auth에는 회원등록이 완료.
                //입력된 이메일로 [인증확인] 메일 전송 및 전송 성공여부 확인
                auth.currentUser?.sendEmailVerification()?.addOnCompleteListener {
                    if(it.isSuccessful) {
                        Toast.makeText(this, "전송된 이메일을 확인하고 인증에 성공하면 가입이 완료됩니다", Toast.LENGTH_LONG).show()

                        //회원 정보 DB에 저장
                        val user = auth.currentUser
                        user?.getIdToken(false)?.addOnSuccessListener {
                            val token = it.token //나중에 추가하던가 빼버릴 것
                            val uid = user.uid
                            Log.e("SignupAc", "uid: $uid, token: $token")

                            RetrofitHelper.getRetrofitInterface()
                                .saveMember("email", uid, email, nickname)
                                .enqueue(object : Callback<String>{
                                    override fun onResponse(
                                        call: Call<String>,
                                        response: Response<String>
                                    ) {
                                        val resultStr = response.body()
                                        Log.e("saveMember Success", "resultStr : $resultStr")
                                        finish()
                                    }

                                    override fun onFailure(call: Call<String>, t: Throwable) {
                                        Log.e("saveMember Failure", "${t.message}")
                                    }
                                })//saveMember
                        }
                    }
                    else {
                        Toast.makeText(this, "인증용 이메일 전송에 실패했습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                        Log.e("TAG", "createUser failed: ", it.exception)
                    }
                }
            } else {
                Log.e("TAG", "createUser failed", it.exception)
                Toast.makeText(this, "가입 실패 : ${it.exception?.message}", Toast.LENGTH_LONG).show()
            }
        }
        //인증을 마친 사용자는 기본적으로 Firebase 실시간 데이터베이스와 Cloud Storage에서 데이터를 읽고 쓸 수 있습니다.
        // 사용자의 액세스 권한을 제어하려면 Firebase 실시간 데이터베이스 및 Cloud Storage 보안 규칙을 수정하면 됩니다.
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }

        return super.onOptionsItemSelected(item)
    }

}