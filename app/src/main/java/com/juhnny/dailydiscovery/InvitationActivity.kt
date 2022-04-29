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