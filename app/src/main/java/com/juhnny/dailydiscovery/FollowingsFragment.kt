package com.juhnny.dailydiscovery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.juhnny.dailydiscovery.databinding.FragmentFollowingsBinding
import retrofit2.Call
import retrofit2.Callback

class FollowingsFragment : Fragment() {

    val b by lazy { FragmentFollowingsBinding.inflate(layoutInflater) }

    val follows = mutableListOf<Follow>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root

        //바인딩을 inflate 해서 root뷰를 넘기는 방법
//        val binding = FragmentFollowingsBinding.inflate(layoutInflater)
//        binding.btnAdd.setOnClickListener{
//            parentFragmentManager.beginTransaction().add(R.id.container_bnv, FollowingsFragment()).addToBackStack(null).commit()
//        }
//        binding.btnReplace.setOnClickListener{
//            parentFragmentManager.beginTransaction().replace(R.id.container_bnv, FollowingsFragment()).addToBackStack(null).commit()
//        }
//        binding.recycler.adapter = FollowRecyclerAdapter(requireContext(), this, follows)
//
//        return binding.root

        //기존 방법 - inflater로 뷰를 만들어 리턴
//        val view = inflater.inflate(R.layout.fragment_followings, container, false)
//        view.findViewById<RecyclerView>(R.id.recycler).adapter = FollowRecyclerAdapter(requireContext(), this, follows)
//        view.findViewById<Button>(R.id.btn_add).setOnClickListener{
//            parentFragmentManager.beginTransaction().replace(R.id.container_bnv, FollowingsFragment()).addToBackStack(null).commit()
//        }
//        view.findViewById<Button>(R.id.btn_replace).setOnClickListener{
//            parentFragmentManager.beginTransaction().replace(R.id.container_bnv, FollowingsFragment()).addToBackStack(null).commit()
//        }
//        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        /***** 팔로잉 화면에서 할 일 *****/
        //게스트 상태일 때
        //  로그인 안내 뷰 띄우기
        //로그인 상태일 때
        //  내가 구독한 사람들 DB에서 읽어서 다시 그들의 최근 사진 세 장을 가져오기

        val appCompatActivity = requireActivity() as AppCompatActivity
        appCompatActivity.setSupportActionBar(b.toolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)

        if(G.user == null){ //비로그인 상태
            b.layoutContent.visibility = View.GONE
            b.layoutSigninNotice.visibility = View.VISIBLE
        } else { //로그인 상태
            b.layoutContent.visibility = View.VISIBLE
            b.layoutSigninNotice.visibility = View.GONE

            b.layoutNoResult.visibility = View.GONE
            b.recycler.adapter = FollowRecyclerAdapter(requireContext(), this, follows)
            loadFollow(G.user?.email!!)
        }

        b.btnSignin.setOnClickListener {
            startActivity(Intent(requireContext(), LoginActivity::class.java))
            requireActivity().finish()
        }

    }// onViewCreated

    private fun loadFollow(userEmail:String){
        val call = RetrofitHelper.getRetrofitInterface().loadFollowing(userEmail)
        call.enqueue(object : Callback<Response<Follow>>{
            override fun onResponse(
                call: Call<Response<Follow>>,
                response: retrofit2.Response<Response<Follow>>
            ) {
                val header = response.body()?.responseHeader
                val body = response.body()?.responseBody
                if(header != null && body != null){
                    val newFollows = body.items
                    Log.e("FollowingsFrag loadFollow Success", "userEmail: $userEmail, itemCount: ${body.itemCount}, ${body.items}")
                    if(body.itemCount != 0) {
                        follows.addAll(newFollows)
                        b.recycler.adapter?.notifyDataSetChanged()
                    } else b.layoutNoResult.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<Response<Follow>>, t: Throwable) {
                Log.e("FollowingsFrag loadFollow Failure", "${t.message}")
            }
        })
    }

    fun loadFollowsStub(){
        for(i in 1..1){
            follows.add(Follow("bcde@naver.co", "name${i}", "I am like... ${i}",
                "https://a.cdn-hotels.com/gdcs/production87/d499/8196849a-5862-4e4f-86ec-a2c362157f74.jpg",
                "https://www.qmul.ac.uk/media/qmul/London-Bridge.jpg",
                "https://i.natgeofe.com/n/99790646-c5a4-4637-8f10-1d1c41ce3705/london_travel_2x3.jpg"))
        }
        b.recycler.adapter?.notifyDataSetChanged()
    }
}