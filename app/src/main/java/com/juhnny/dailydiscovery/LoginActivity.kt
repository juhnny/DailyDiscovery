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
                val email = intent?.getStringExtra("email")
                Toast.makeText(this@LoginActivity, "이메일 로그인 성공여부: ${didLogInSuccessed}, email: $email", Toast.LENGTH_SHORT).show()
                //로그인 날짜 업데이트

                val userId = "Stub~"
                //성공 시
                prefs.edit().putBoolean("isLoggedIn", true)
                    .putString("email", email)
                    .putString("memId", userId)
                    .putString("snsType", "")
                    .commit()
                if(prefs.getBoolean("isLoggedIn", false)) {
                    G.user = User("1", "asdf", "$email", "nick", "Hello", "19891111", "19891201", "", "", "19891231")
                }
                val intent2 = Intent(this@LoginActivity, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent2)
                finish()
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
            Toast.makeText(baseContext, "resultLauncer came back", Toast.LENGTH_SHORT).show()
            try {
                //로그인 창을 그냥 닫아버리면 예외 발생
                if(result?.resultCode == RESULT_CANCELED) return

                val intent = result?.data
                val task:Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(intent)
                val account = task.getResult()

                Log.e("TAG Google sign in account", "${account.email}, ${account.id}, ${account.idToken}")
                val intent2 = Intent(baseContext, MainActivity::class.java).apply {
                    addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
                    addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                }
                startActivity(intent)
                finish()
            } catch (e:ApiException){
                Log.w("TAG Google Login Failed", "failure code : ${e.statusCode}")
            }
        }
    })

    //이메일 로그인 시
    //유저 정보를 DB로부터 로드해 SharedPreference에 저장해놓는 메소드
    fun loadAndSaveUserData(user: FirebaseUser){
        //email을 기준으로 SELECT
//        val email = "bcde@naver.co"
        val email = user.email
        Log.e("user email", "$email")

        val retrofitInterface = RetrofitHelper.getRetrofitInterface()

        val call = retrofitInterface.loadMember(email!!)
        call.enqueue(object : Callback<Response<User>> {
            override fun onResponse(call: Call<Response<User>>, response: retrofit2.Response<Response<User>>) {
                val myResponse:Response<User>? = response.body()
                if(myResponse != null){
                    val resultMsg = myResponse.responseHeader.resultMsg
                    val body:ResponseBody<User>? = myResponse.responseBody
                    if (body != null) {
                        val user:User = body.items[0]
                        //SharedPreference에 회원 정보 저장
//                        val prefs = getSharedPreferences("user", MODE_PRIVATE) //덮어쓰기
                        val prefs = PreferenceManager.getDefaultSharedPreferences(this@LoginActivity) //덮어쓰기
                        prefs.edit().putString("no", user.memNo)
                            .putString("idToken", user.memId)
                            .putString("email", user.email)
                            .putString("nickname", user.nickname)
                            .putString("profileMsg", user.profileMsg)
                            .putString("signUpDatetime", user.signUpDatetime)
                            .putString("lastLoginDatetime", user.lastLoginDatetime)
                            .commit()
                        Log.e("MainAc loadMember Success",
                            "$resultMsg: ${prefs.getString("email", "load email failed")}, ${prefs.getString("nickname", "load nickname failed")}")
                    }
                }
            }

            override fun onFailure(call: Call<Response<User>>, t: Throwable) {
                Log.e("MainAc loadMember Failure", "${t.message}")
            }
        })//loadMember

    }//loadUserData

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

            //내 DB에 마지막 로그인 시각 업데이트 & 회원 정보 읽어오기
            //성공 시 prefs 와 G.user에 저장

            val prefs = PreferenceManager.getDefaultSharedPreferences(this)
            prefs.edit().putBoolean("isLoggedIn", true)
                .commit()

            G.user = User("1", "asdf", "asdf@naver.com", "nick", "Hello", "19891111", "19891201", "", "", "19891231")

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

}