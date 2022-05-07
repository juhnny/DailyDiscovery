package com.juhnny.dailydiscovery

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
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
    val topicSelected by lazy { intent.getStringExtra("topicSelected") }

    val photos = mutableListOf<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        b.tvBarTitle.text = topicSelected

        b.recycler.adapter = GalleryRecyclerAdapter(this, photos)

        loadPhotos()
        loadPhotosStub()
    }//onCreate()


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadPhotos(){
//        val builder: Retrofit.Builder = Retrofit.Builder()
//        builder.baseUrl("http://iwibest.dothome.co.kr")
//        builder.addConverterFactory(GsonConverterFactory.create())
//        val retrofit:Retrofit = builder.build()
        val retrofit:Retrofit = Retrofit.Builder().baseUrl("http://iwibest.dothome.co.kr")
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val retrofitInterface = retrofit.create(RetrofitInterface::class.java)

        val call:Call<Response<Photo>> = retrofitInterface.loadPost("gogogo", 1)
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
                    val post = posts[0]
                    Log.e("loadPost Success", "Header : $resultMsg")
                    Log.e("loadPost Success", "Body variables: $posts, $itemCount, ${post.topic}")

                    photos.addAll(posts)
                }
            }
            override fun onFailure(call: Call<Response<Photo>>, t: Throwable) {
            Log.e("loadPost Failed", "message : ${t.message}")
            }
        })

//        val call:Call<Photo> = retrofitInterface.loadPost("gogogo", 1)
//        call.enqueue(object: Callback<Photo>{
//        override fun onResponse(
//            call: Call<Photo>,
//            response: retrofit2.Response<Photo>) {
//            val response:Photo? = response.body()
//            if(response != null) {
//                val topic = response.topic
//                Log.e("loadPost", "${topic}")
//            }
//        }

//        override fun onFailure(call: Call<Photo>, t: Throwable) {
//            Log.e("loadPost", "Failed : ${t.message}")
//        }
//        })

        val call2:Call<String> = retrofitInterface.loadPostString("gogogo", 1)
        call2.enqueue(object: Callback<String> {
            override fun onResponse(
                call: Call<String>,
                response: retrofit2.Response<String>
            ) {
                Log.e("loadPostString", "Success : ${response.body()}")
            }

            override fun onFailure(call: Call<String>, t: Throwable) {
                Log.e("loadPostString", "Failed : ${t.message}")
            }
        })

        b.recycler.adapter?.notifyDataSetChanged()
    }

    fun loadPhotosStub(){
        photos.add(Photo("1", "주제명", "A material metaphor is the unifying theory of a rationalized space and a system of motion.\n" +
                "\n" +
                "        Components with\n" +
                "        responsive elevations\n" +
                "        may encounter other components\n" +
                "        as they move between.", "aaa@naver.com", "Nicholas", "20220101", "20220101",
            "https://images.pexels.com/photos/1001682/pexels-photo-1001682.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
//        photos.add(Photo("2", "주제명", "주제에 대한 설명", "hong2", "20220101", "20220101",
//            "https://images.pexels.com/photos/1802268/pexels-photo-1802268.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
//        photos.add(Photo("3", "주제명", "주제에 대한 설명", "hong3", "20220101", "20220101",
//            "https://images.unsplash.com/photo-1439405326854-014607f694d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8c2VhfGVufDB8fDB8fA%3D%3D&w=1000&q=80"))
//        photos.add(Photo("4", "주제명", "주제에 대한 설명", "hong4", "20220101", "20220101",
//            "https://cdn.pixabay.com/photo/2016/12/17/14/33/wave-1913559__480.jpg"))
//        photos.add(Photo("5", "주제명", "주제에 대한 설명", "hong5", "20220101", "20220101",
//            "https://image.bugsm.co.kr/album/images/500/151085/15108518.jpg"))
//        photos.add(Photo("6", "주제명", "주제에 대한 설명", "hong6", "20220101", "20220101",
//            "https://as2.ftcdn.net/v2/jpg/01/29/27/99/1000_F_129279909_yZfwKBPu4o4c6Mdt1fgXIX1tJY59258r.jpg"))
//        photos.add(Photo("7", "주제명", "주제에 대한 설명", "hong7", "20220101", "20220101",
//            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpfuXPYRo0AoLkM1sr8lTfu7hXxJzh9KQ-rQ&usqp=CAU"))
//        photos.add(Photo("1", "주제명", "주제에 대한 설명", "hong1", "20220101", "20220101",
//            "https://images.pexels.com/photos/1001682/pexels-photo-1001682.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
//        photos.add(Photo("2", "주제명", "주제에 대한 설명", "hong2", "20220101", "20220101",
//            "https://images.pexels.com/photos/1802268/pexels-photo-1802268.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
//        photos.add(Photo("3", "주제명", "주제에 대한 설명", "hong3", "20220101", "20220101",
//            "https://images.unsplash.com/photo-1439405326854-014607f694d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8c2VhfGVufDB8fDB8fA%3D%3D&w=1000&q=80"))
//        photos.add(Photo("4", "주제명", "주제에 대한 설명", "hong4", "20220101", "20220101",
//            "https://cdn.pixabay.com/photo/2016/12/17/14/33/wave-1913559__480.jpg"))
//        photos.add(Photo("5", "주제명", "주제에 대한 설명", "hong5", "20220101", "20220101",
//            "https://image.bugsm.co.kr/album/images/500/151085/15108518.jpg"))
//        photos.add(Photo("6", "주제명", "주제에 대한 설명", "hong6", "20220101", "20220101",
//            "https://as2.ftcdn.net/v2/jpg/01/29/27/99/1000_F_129279909_yZfwKBPu4o4c6Mdt1fgXIX1tJY59258r.jpg"))
//        photos.add(Photo("7", "주제명", "주제에 대한 설명", "hong7", "20220101", "20220101",
//            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpfuXPYRo0AoLkM1sr8lTfu7hXxJzh9KQ-rQ&usqp=CAU"))

        b.recycler.adapter?.notifyDataSetChanged()
    }
}