package com.juhnny.dailydiscovery

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.juhnny.dailydiscovery.databinding.ActivityLoginEmailBinding

class LoginEmailActivity : AppCompatActivity() {

    val b by lazy { ActivityLoginEmailBinding.inflate(layoutInflater) }
    val auth by lazy { FirebaseAuth.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        b.tvGuide.visibility = View.GONE
        b.tvGuideEmailVerification.visibility = View.GONE
        b.layoutEmailVerification.visibility = View.GONE

        b.btnLogin.setOnClickListener{ signIn() }

        b.tvFindId.setOnClickListener { MyUtil.showSorryAlert(this) }

        b.tvFindPw.setOnClickListener { MyUtil.showSorryAlert(this) }

    }

    fun signIn(){
        val email = b.etEmail.text.toString()
        val pw = b.etPw.text.toString()

        //기존에 받아둔 currentUser 토큰이 더이상 유효하지 않은 경우(아래 코멘트의 상황들)에 대한 처리
        //FirebaseAuth 서버를 감시
        //리스너를 프로세스(액티비티)마다 달아줘야 하나?
        //여기서 달아놓고 액티비티가 종료돼도 액티비티에서 작동하는지 알아보자
        //로그인 시도가 있을 때마다도 불려짐
        auth.addAuthStateListener { auth ->
            Toast.makeText(baseContext, "AuthStateListener called", Toast.LENGTH_SHORT).show()
            //로그인부터 로그아웃 때까지만 달아놓자
            //로그아웃 시키고 안내 띄우고 MainActivity 새로 띄우고
            if(auth.currentUser == null){

            }
            //- Right after the listener has been registered
            //- When a user is signed in
            //- When the current user is signed out
            //- When the current user changes

        }

        auth.signInWithEmailAndPassword(email, pw).addOnCompleteListener{
            if(it.isSuccessful){
                // Sign in success, update UI with the signed-in user's information
                val user: FirebaseUser? = auth.currentUser

                if(user != null){ //로그인 되어있을 경우
                    Log.e("TAG LoginAc", "currentUser != null, email:${user.email}")

                    //인증이 돼있나 확인
                    if(user.isEmailVerified){ //인증 완료시에만 로그인 처리, 메인 액티비티 스타트
                        Log.e("TAG LoginAc", "인증 완료")
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                        //save Logged In User Data
                        intent.putExtra("didLogInSuccessed", true)
                        intent.putExtra("email", true)
                        setResult(RESULT_OK, intent)
                        finish()
                    } else { //인증 미완료 시 인증메일 발송 버튼 띄우고 로그아웃 처리. 안내 띄우기.
                        Log.e("TAG LoginAc", "인증 미완료")
                        Toast.makeText(this, "로그인을 위해 이메일 인증을 완료해주세요", Toast.LENGTH_SHORT).show()
                        b.tvGuideEmailVerification.visibility = View.VISIBLE
                        b.layoutEmailVerification.visibility = View.VISIBLE

                        b.tvGuideEmailVerification.text = "로그인을 위해 이메일 인증을 완료해주세요. 인증 메일의 링크를 클릭하시면 인증이 완료됩니다."
                        b.tvSendEmailVerification.setOnClickListener {
                            auth.currentUser?.sendEmailVerification()?.addOnCompleteListener{
                                if(it.isSuccessful){
                                    Toast.makeText(this, "인증메일 발송 완료", Toast.LENGTH_SHORT).show()
                                } else {
                                    Toast.makeText(this, "인증메일 발송 실패", Toast.LENGTH_SHORT).show()
                                }
                            }
                        }
                        b.tvCheckEmailVerification.setOnClickListener{
                            val user: FirebaseUser? = auth.currentUser
                            //Do note that the FirebaseUser object is cached within an app session,
                            // so if you want to check on the verification state of a user,
                            // it's a good idea to call .getCurrentUser().reload() for an update.
                            if(user != null){
                                user.reload().addOnCompleteListener {
                                    Log.e("TAG LoginAc checkEmailVerification", "${user.email}, ${user.isEmailVerified}")

                                    if(user.isEmailVerified){
                                        Toast.makeText(this, "인증 확인", Toast.LENGTH_SHORT).show()
                                    } else {
                                        Toast.makeText(this, "이메일 인증이 완료되지 않았습니다. 인증 메일의 링크를 클릭하시면 인증이 완료됩니다.", Toast.LENGTH_LONG).show()
                                    }
                                }
                            } else Toast.makeText(this, "유저 정보를 확인할 수 없습니다. \n다시 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }//이메일 인증여부 검사
                } else{ //로그인은 성공했는데 null인 경우. 안내만 띄우자. 거의 없을 듯.
                    Log.e("TAG LoginAc", "currentUser == null")
                    Toast.makeText(this, "유저 정보를 가져올 수 없습니다.\n다시 로그인 해주세요.", Toast.LENGTH_SHORT).show()
                    auth.signOut()
                }
            } else {
                // If sign in fails, display a message to the user.
                Log.e("TAG LoginAc", "signIn failed: ${it.exception}")
                Toast.makeText(baseContext, "로그인 실패 \n ${it.exception}", Toast.LENGTH_SHORT).show()
//                updateUI(null)
            }
        }
    }//signIn

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

}