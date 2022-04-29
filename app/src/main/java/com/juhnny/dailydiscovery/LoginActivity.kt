package com.juhnny.dailydiscovery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.juhnny.dailydiscovery.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    val b by lazy { ActivityLoginBinding.inflate(layoutInflater) }
    val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        b.btnLogin.setOnClickListener{
            signIn()
        }

        b.tvFindId.setOnClickListener {
            MyUtil.showAlert(this)
        }

        b.tvFindPw.setOnClickListener {
            MyUtil.showAlert(this)
        }

        b.tvSignup.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java )
            startActivity(intent)
        }

        b.btnLogout.visibility = View.VISIBLE
        b.btnLogout.setOnClickListener{
            auth.signOut()
            Toast.makeText(this, "로그아웃 되었습니다", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun signIn(){
        val email = b.etEmail.text.toString()
        val pw = b.etPw.text.toString()

        auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener{
            if(it.isSuccessful){
                // Sign in success, update UI with the signed-in user's information
                val user = auth.currentUser
                Log.e("TAG", "signIn Success: ${it.result}")
                Toast.makeText(baseContext, "로그인 성공", Toast.LENGTH_SHORT).show()

//                updateUI(user)
                startActivity(Intent(baseContext, MainActivity::class.java))
            } else {
                // If sign in fails, display a message to the user.
                Log.e("TAG", "signIn failed: ${it.exception?.message}")
                Toast.makeText(baseContext, "Authentication failed - ${it.exception}",
                    Toast.LENGTH_SHORT).show()
//                updateUI(null)
            }
        }


    }
}