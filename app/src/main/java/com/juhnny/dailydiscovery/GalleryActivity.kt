package com.juhnny.dailydiscovery

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.juhnny.dailydiscovery.databinding.ActivityGalleryBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory

class GalleryActivity : AppCompatActivity() {

    //선택된 주제를 받아서
    //Firestore의 Photo 컬렉션에서 필드가 그 주제로 된 사진을 최신순으로 일정 개수 가져온다.
    //가져와서 Photo 데이터 클래스에 저장하고 리스트에 추가해 관리
    //리사이클러뷰로 보여주고
    //아래로 스크롤 시 추가 로딩
    //항목 선택 시 PhotoActivity로 사진 크게 로딩

    val b by lazy { ActivityGalleryBinding.inflate(layoutInflater) }
    val topicSelected by lazy { intent.getStringExtra("topicSelected") ?: "오류" }

    val photos = mutableListOf<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        b.tvBarTitle.text = topicSelected
        b.layoutNoResult.visibility = View.GONE

        b.recycler.adapter = GalleryRecyclerAdapter(this, photos)

        loadPhotos(topicSelected)
//        loadPhotosStub()

    }//onCreate()


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadPhotos(queryTopic:String){
        b.progressCircular.visibility = View.VISIBLE
        val retrofit:Retrofit = Retrofit.Builder().baseUrl("http://iwibest.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        val call:Call<Response<Photo>> = retrofitInterface.loadPost(queryTopic, 1)
        call.enqueue(object: Callback<Response<Photo>>{
            override fun onResponse(
                call: Call<Response<Photo>>,
                response: retrofit2.Response<Response<Photo>>) {
                Log.e("loadPost Success", "Body : ${response.body()}")

                val myResponse:Response<Photo>? = response.body()
                if(myResponse != null) {
                    val resultMsg = myResponse.responseHeader.resultMsg
                    val posts = myResponse.responseBody.items
                    val itemCount = myResponse.responseBody.itemCount
                    Log.e("loadPost Success", "Header : $resultMsg")
                    Log.e("loadPost Success", "Body itemCount : $itemCount")

                    b.progressCircular.visibility = View.GONE
                    if(posts.isEmpty()) b.layoutNoResult.visibility = View.VISIBLE
                    photos.addAll(posts)
                    b.recycler.adapter?.notifyDataSetChanged()
                }
            }
            override fun onFailure(call: Call<Response<Photo>>, t: Throwable) {
            Log.e("loadPost Failed", "message : ${t.message}")
            }
        })

        val call2:Call<String> = retrofitInterface.loadPostString(queryTopic, 1)
        call2.enqueue(object: Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: retrofit2.Response<String>
            ) { Log.e("loadPostString", "Success : ${response.body()}") }

            override fun onFailure(call: Call<String>, t: Throwable) { Log.e("loadPostString", "Failed : ${t.message}") }
        })

    }


    fun loadPhotosStub(){
        photos.add(Photo("1", "주제명", "A material metaphor is the unifying theory of a rationalized space and a system of motion.",
            "aaa@naver.com", "Nicholas", "20220101", "20220101",
            "https://images.pexels.com/photos/1001682/pexels-photo-1001682.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))

        b.recycler.adapter?.notifyDataSetChanged()
    }
}