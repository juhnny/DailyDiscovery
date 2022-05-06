package com.juhnny.dailydiscovery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.juhnny.dailydiscovery.databinding.ActivityInvitationBinding

class InvitationActivity : AppCompatActivity() {

    val b by lazy { ActivityInvitationBinding.inflate(layoutInflater) }
    val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        /*****초대장 화면에서 할 일****/
        //초대장 이미지 보여주기
        //이용방법 설명
        //로그인/회원가입/둘러보기 버튼

        b.tvLogin.setOnClickListener {
            startActivity(Intent(this, LoginActivity::class.java))
        }

        b.tvSignup.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        b.tvLookaround.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }
    }
}