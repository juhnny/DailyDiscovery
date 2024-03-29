package com.juhnny.dailydiscovery

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.juhnny.dailydiscovery.databinding.FragmentBioMyBinding
import retrofit2.Call
import retrofit2.Callback

class MyBioFragment (val userEmail: String?,
                     val mode:Int = OTHERS_BIO_MODE) : Fragment(){

    constructor():this(userEmail=null){}

    companion object{
        //차라리 isTab4 = true/false로 바꿀까
        val MY_BIO_MODE = 1
        val OTHERS_BIO_MODE = 2
    }

    val appCompatActivity by lazy { requireActivity() as AppCompatActivity }
    val b by lazy {FragmentBioMyBinding.inflate(layoutInflater)}
    val auth by lazy { FirebaseAuth.getInstance() }
    val userEmail2:String by lazy { arguments?.getString("email") ?: "null@null.com" }

    var photos = mutableListOf<Photo>()

   override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setHasOptionsMenu(true) //잊지마
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("myParentFragmentManager: ", "${parentFragmentManager}")

        appCompatActivity.setSupportActionBar(b.toolbar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)

        /***** Bio 화면에서 해야할 작업 *****/
        //게스트인 경우
        //로그인 안내 뷰 띄우기

        //로그인 시 공통
        //프로필 정보, 작성글 목록 보여주기

        //내 프로필일 경우
        //  프로필 수정 버튼

        //타인 프로필인 경우
        //  구독/구독해제 버튼

        //액티비티에서 띄운 경우

        //파라미터로 받은 유저(이메일로 대체)의 로그인/게스트 상태에 따른 UI update
        //비로그인 상태에서 볼 때
        //로그인 상태에서 내 바이오를 볼 때
        //로그인 상태에서 다른사람 바이오를 볼 때
//        val user = FirebaseAuth.getInstance().currentUser
//        if(user == null){ //비로그인 상태
//            b.layoutProfile.visibility = View.GONE
//            b.layoutSigninNotice.visibility = View.VISIBLE
//        } else { //로그인 상태
//            b.layoutProfile.visibility = View.VISIBLE
//            b.layoutSigninNotice.visibility = View.GONE
//
//            b.recycler.adapter = BioRecyclerAdapter(requireContext(), photos)
//            showProfile(userEmail!!)
//            loadPhotos(userEmail!!)
//
//            //지금 보는 프로필이 내 프로필일 때 -> 구독 버튼 숨기기
//            //다른 사람 프로필일 때 -> 내가 구독중인 사람이면 '구독해제' 버튼, 아니면 '구독'버튼 보이기
//            updateUiOnFollow(userEmail!!)
//
//        }//로그인/게스트 상태에 따른 UI 업데이트

        if(G.user == null) {//비로그인 상태
            b.layoutProfile.visibility = View.GONE
            b.layoutSigninNotice.visibility = View.VISIBLE
        } else {//로그인 상태
            b.layoutProfile.visibility = View.VISIBLE
            b.layoutSigninNotice.visibility = View.GONE

            b.recycler.adapter = BioRecyclerAdapter(requireContext(), photos)

            showProfile(userEmail2)
            loadPhotos(userEmail2)

            //지금 보는 프로필이 내 프로필일 때 -> 구독 버튼 숨기기
            //다른 사람 프로필일 때 -> 내가 구독중인 사람이면 '구독해제' 버튼, 아니면 '구독'버튼 보이기
            updateUiOnFollow(userEmail2)
        }

        b.btnSignin.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }
        b.btnSubscribe.setOnClickListener { saveFollow(userEmail2) }
        b.btnUnsubscribe.setOnClickListener { saveUnfollow(userEmail2) }
    }// onViewCreated

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
//        inflater.inflate(R.menu.options_my_bio, menu)
    }

//    업 버튼(홈 메뉴)을 누르면 프래그먼트가 닫히도록
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                Log.e("MyBio - home", "")
//                parentFragmentManager.beginTransaction().remove(this).commit()
                return true
            }
            R.id.menu_my_bio_menu1 ->
                Log.e("MyBio - menu1", "")

            else ->
                Log.e("MyBio - else", "")
        }
        return super.onOptionsItemSelected(item)
    }

    //백 버튼. 현재는 업버튼에도 액티비티가 onBackPressed를 발동해 동작하고 있음..
    //Activity에 onBackPressed 발생 시 Fragment에 콜백이 오도록 하고 그 때 handleOnBackPressed() 내 명령 실행
    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is MainActivity) Log.e("MyBioFrag Test: Is context MainAc?", "Yes")
        if(context is LoginActivity) Log.e("MyBioFrag Test: Is context LoginAc?", "Yes")
        else Log.e("MyBioFrag Test: Is context LoginAc?", "No")
        if(requireActivity() is MainActivity) Log.e("MyBioFrag Test: Is requireActivity MainAc?", "Yes")
        if(requireContext() is MainActivity) Log.e("MyBioFrag Test: Is requireContext MainAc?", "Yes")
        if(requireContext() is LoginActivity) Log.e("MyBioFrag Test: Is requireContext LoginAc?", "Yes")

        //BackPressed 이벤트 발생 시 Activity로부터 콜백
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Log.e("MyBio - onBack callback 받음", "")
                parentFragmentManager.beginTransaction().remove(this@MyBioFragment).commit()
                Log.e("MyBio Frag removed", "")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }

    private fun showProfile(userEmail: String){
        val call:Call<Response<User>> = RetrofitHelper.getRetrofitInterface().loadProfile(userEmail)
        call.enqueue(object : Callback<Response<User>>{
            override fun onResponse(
                call: Call<Response<User>>,
                response: retrofit2.Response<Response<User>>
            ) { val myResponse = response.body()
                if(myResponse != null){
                    Log.e("MyBioFrag userEmail: $userEmail", "")
                    val userProfile:User = myResponse.responseBody.items[0]
                    Log.e("MyBioFrag loadProfile Success", "${userProfile.nickname}, ${userProfile.profileMsg}")

                    b.tvNickname.text = userProfile.nickname
                    b.tvIntroduce.text = userProfile.profileMsg
                }
            }

            override fun onFailure(call: Call<Response<User>>, t: Throwable) {
                Log.e("MyBioFrag loadProfile Failure", "${t.message}")
            }
        })
    }

    // 구독 버튼의 숨김/구독/구독해제 처리
    private fun updateUiOnFollow(targetEmail:String){
        val myEmail = G.user?.email
        Log.e("MyBioFrag updateUiOnFollow", "myEmail: $myEmail, targetEmail: $targetEmail")
        //기본적으로 다 가려놨다가..
        b.btnSubscribe.visibility = View.GONE
        b.btnUnsubscribe.visibility = View.GONE
        //띄워진 앨범이 내 앨범이면 그대로 놔두고,
        if(userEmail2.equals(myEmail)) return
        //다른사람 앨범이면
        val call = RetrofitHelper.getRetrofitInterface().isFollowing(myEmail!!, targetEmail)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                val booleanStr = response.body()
                Log.e("MyBioFrag isFollowing Success", "$booleanStr")
                val isFollowing:Boolean = booleanStr.toBoolean() //String이 "true"이면 true, 나머지는 어떤 값이든 false
                if(isFollowing){ //내가 팔로우 중이면
                    b.btnUnsubscribe.visibility = View.VISIBLE
                } else { //내가 팔로우 중이 아니거나 예상치 못한 응답 값이면
                    b.btnSubscribe.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("MyBioFrag isFollowing Failure", "${t.message}")
            }
        })

    }

    private fun saveFollow(targetEmail:String){
        val myEmail = FirebaseAuth.getInstance().currentUser?.email ?:return
        val call = RetrofitHelper.getRetrofitInterface().saveFollow(myEmail, targetEmail)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                val resultStr = response.body()
                Log.e("MyBioFrag saveFollow Success", " $resultStr")
                b.btnSubscribe.visibility = View.GONE
                b.btnUnsubscribe.visibility = View.VISIBLE
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("MyBioFrag saveFollow Failure", "${t.message}")
            }
        })
    }

    private fun saveUnfollow(targetEmail: String){
        val myEmail = FirebaseAuth.getInstance().currentUser?.email ?:return
        val call = RetrofitHelper.getRetrofitInterface().saveUnfollow(myEmail, targetEmail)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                val resultMsg = response.body()
                Log.e("MyBioFrag saveUnfollow Success", "$resultMsg")
                b.btnSubscribe.visibility = View.VISIBLE
                b.btnUnsubscribe.visibility = View.GONE
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("MyBioFrag saveUnfollow Failure", "${t.message}")
            }
        })

    }

    fun loadPhotos(queryEmail:String?){
        b.layoutNoResult.visibility = View.GONE
        if(queryEmail == null) return

        val retrofitInterface = RetrofitHelper.getRetrofitInterface()

//        val call:Call<String> = retrofitInterface.loadPostToAlbumString(queryEmail)
//        call.enqueue(object : Callback<String>{
//            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
//                val resultStr:String? = response.body()
//                if(resultStr != null){
//                    Log.e("loadPostToAlbumString Success", resultStr)
//                }
//            }
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                Toast.makeText(requireContext(), "loadPostToAlbum Failed : ${t.message}", Toast.LENGTH_SHORT).show()
//            }
//        })//loadPostToAlbumString

        val call2:Call<Response<Photo>> = retrofitInterface.loadPostToAlbum(queryEmail)
        call2.enqueue(object : Callback<Response<Photo>>{
            override fun onResponse(
                call: Call<Response<Photo>>,
                response: retrofit2.Response<Response<Photo>>
            ) {
                val myResponse = response.body()
                if(myResponse != null){
                    val header:ResponseHeader = myResponse.responseHeader
                    val body:ResponseBody<Photo> = myResponse.responseBody
                    val resultMsg:String = header.resultMsg

                    Log.e("loadPostToAlbum Success", "Header : $resultMsg")
                    photos.addAll(body.items)
                    Log.e("loadPostToAlbum Success", "Body : itemCount: ${body.itemCount}")
                    b.recycler.adapter?.notifyDataSetChanged()
                    if(body.itemCount == 0) b.layoutNoResult.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<Response<Photo>>, t: Throwable) {
                Log.e("loadPostToAlbum Failure", "${t.message}")
            }
        })

    }//loadPhotos

}