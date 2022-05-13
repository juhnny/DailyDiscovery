package com.juhnny.dailydiscovery

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
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
import com.google.firebase.auth.FirebaseUser
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
        //이메일 로그인
        //  인증메일 재발송
        //  DB에 마지막 로그인 시간 업데이트
        //회원가입
        //  회원 정보 DB에 insert
        //  인증메일 발송
        //  인증메일 재발송
        //간편로그인
        //  회원 정보 DB에 insert
        //둘러보기
        //  메인 화면으로 이동
        b.btnLogin.setOnClickListener{ signIn() }

        b.tvFindId.setOnClickListener { MyUtil.showSorryAlert(this) }

        b.tvFindPw.setOnClickListener { MyUtil.showSorryAlert(this) }

        b.tvSignup.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java )
            startActivity(intent)
        }

        b.btnGoogleLogin.setOnClickListener { googleLogin() }

        b.tvLookaround.setOnClickListener {
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        }

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
                val user:FirebaseUser? = auth.currentUser

                if(user != null){ //로그인 되어있을 경우
                    Log.e("TAG LoginAc", "currentUser != null, email:${user.email}")

                    updateLastLogInDatetime()
                    //인증이 돼있나 확인
                    if(user.isEmailVerified){ //인증 완료시에만 로그인 처리, 메인 액티비티 스타트
                        Log.e("TAG LoginAc", "인증 완료")
                        Toast.makeText(this, "로그인 성공", Toast.LENGTH_SHORT).show()
                        saveLoggedInUserData()
                        startActivity(Intent(this, MainActivity::class.java))
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
                            val user:FirebaseUser? = auth.currentUser
                            //Do note that the FirebaseUser object is cached within an app session,
                            // so if you want to check on the verification state of a user,
                            // it's a good idea to call .getCurrentUser().reload() for an update.
                            if(user != null){
                                user.reload().addOnCompleteListener {
                                    Log.e("TAG LoginAc checkEmailVerification", "${user.email}, ${user.isEmailVerified}")

                                    if(user.isEmailVerified){
                                        Toast.makeText(this, "인증 확인", Toast.LENGTH_SHORT).show()
                                        saveLoggedInUserData()
                                        startActivity(Intent(this, MainActivity::class.java))
                                    }else{
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

    fun updateLastLogInDatetime(){

    }


    private fun saveLoggedInUserData(){
        val user = auth.currentUser
        Log.e("user : ", "$user")
        if(user != null) { //로그인한 사용자가 있으면
            val email:String? = user.email
            val name:String? = user.displayName
            val photoUri: Uri? = user.photoUrl
            val creationTime:Long? = user.metadata?.creationTimestamp
            val lastSignInTime:Long? = user.metadata?.lastSignInTimestamp
            user.getIdToken(false).result.token

            // The user's ID, unique to the Firebase project. Do NOT use this value to
            // authenticate with your backend server, if you have one. Use
            // FirebaseUser.getIdToken() instead.
            val uid:String? = user.uid

            // Check if user's email is verified
            val emailVerified:Boolean = user.isEmailVerified

            //사용자에게 연결된 로그인 제공업체로부터 프로필 정보를 가져오려면 getProviderData 메서드를 사용합니다.

            Log.e("TAG currentUser: ", "email: $email, name: $name, photoUri: $photoUri, uid: $uid, emailVerified: $emailVerified")
            Log.e("TAG currentUser: ", "creationTime: $creationTime, lastSignInTime: $lastSignInTime")
            Log.e("TAG currentUser: ", ": ${user.getIdToken(false).result.token}, : ${user.getIdToken(false).result.claims.values}")
        }

    }
    //현재 사용자를 가져올 때 권장하는 방법은 getCurrentUser 메서드를 호출하는 것입니다.
    // 로그인한 사용자가 없으면 getCurrentUser는 null을 반환합니다.

    //getCurrentUser가 null이 아닌 FirebaseUser(얘가 여기서 말하는 토큰)를 반환하지만 기본 토큰이 유효하지 않은 경우가 있습니다.
    // 예를 들어 사용자가 다른 기기에서 삭제되었는데 로컬 토큰을 새로고침하지 않은 경우가 여기에 해당합니다.
    // 이 경우 유효한 사용자 getCurrentUser를 가져올 수 있지만, 인증 리소스에 대한 후속 호출이 실패합니다.
    //인증 객체의 초기화가 완료되지 않은 경우에도 getCurrentUser가 null을 반환할 수 있습니다.

    //AuthStateListener를 연결하면 기본 토큰의 상태가 변경될 때마다 콜백이 호출됩니다.
    // 이를 통해 위에서 설명한 것과 같은 특이한 사례에 대처할 수 있습니다.
    //Listener called when there is a change in the authentication state.
    //- Right after the listener has been registered
    //- When a user is signed in
    //- When the current user is signed out
    //- When the current user changes

    private fun googleLogin(){
        val signInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                                .requestEmail()
                                .build()

        val client:GoogleSignInClient = GoogleSignIn.getClient(this, signInOptions)
        val intent = client.signInIntent
        resultLauncher.launch(intent)

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