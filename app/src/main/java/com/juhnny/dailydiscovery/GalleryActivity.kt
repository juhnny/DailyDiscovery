package com.juhnny.dailydiscovery

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.juhnny.dailydiscovery.databinding.ActivityGalleryBinding

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
        supportActionBar?.title = topicSelected

        b.recycler.adapter = GalleryRecyclerAdapter(this, photos)

        loadPhotos()


    }

    //

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    fun loadPhotos(){
        photos.add(Photo("1", "주제명", "A material metaphor is the unifying theory of a rationalized space and a system of motion.\n" +
                "\n" +
                "        Components with\n" +
                "        responsive elevations\n" +
                "        may encounter other components\n" +
                "        as they move between.", "hong1", "20220101", "20220101",
            "https://images.pexels.com/photos/1001682/pexels-photo-1001682.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
        photos.add(Photo("2", "주제명", "주제에 대한 설명", "hong2", "20220101", "20220101",
            "https://images.pexels.com/photos/1802268/pexels-photo-1802268.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
        photos.add(Photo("3", "주제명", "주제에 대한 설명", "hong3", "20220101", "20220101",
            "https://images.unsplash.com/photo-1439405326854-014607f694d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8c2VhfGVufDB8fDB8fA%3D%3D&w=1000&q=80"))
        photos.add(Photo("4", "주제명", "주제에 대한 설명", "hong4", "20220101", "20220101",
            "https://cdn.pixabay.com/photo/2016/12/17/14/33/wave-1913559__480.jpg"))
        photos.add(Photo("5", "주제명", "주제에 대한 설명", "hong5", "20220101", "20220101",
            "https://image.bugsm.co.kr/album/images/500/151085/15108518.jpg"))
        photos.add(Photo("6", "주제명", "주제에 대한 설명", "hong6", "20220101", "20220101",
            "https://as2.ftcdn.net/v2/jpg/01/29/27/99/1000_F_129279909_yZfwKBPu4o4c6Mdt1fgXIX1tJY59258r.jpg"))
        photos.add(Photo("7", "주제명", "주제에 대한 설명", "hong7", "20220101", "20220101",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpfuXPYRo0AoLkM1sr8lTfu7hXxJzh9KQ-rQ&usqp=CAU"))
        photos.add(Photo("1", "주제명", "주제에 대한 설명", "hong1", "20220101", "20220101",
            "https://images.pexels.com/photos/1001682/pexels-photo-1001682.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
        photos.add(Photo("2", "주제명", "주제에 대한 설명", "hong2", "20220101", "20220101",
            "https://images.pexels.com/photos/1802268/pexels-photo-1802268.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
        photos.add(Photo("3", "주제명", "주제에 대한 설명", "hong3", "20220101", "20220101",
            "https://images.unsplash.com/photo-1439405326854-014607f694d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8c2VhfGVufDB8fDB8fA%3D%3D&w=1000&q=80"))
        photos.add(Photo("4", "주제명", "주제에 대한 설명", "hong4", "20220101", "20220101",
            "https://cdn.pixabay.com/photo/2016/12/17/14/33/wave-1913559__480.jpg"))
        photos.add(Photo("5", "주제명", "주제에 대한 설명", "hong5", "20220101", "20220101",
            "https://image.bugsm.co.kr/album/images/500/151085/15108518.jpg"))
        photos.add(Photo("6", "주제명", "주제에 대한 설명", "hong6", "20220101", "20220101",
            "https://as2.ftcdn.net/v2/jpg/01/29/27/99/1000_F_129279909_yZfwKBPu4o4c6Mdt1fgXIX1tJY59258r.jpg"))
        photos.add(Photo("7", "주제명", "주제에 대한 설명", "hong7", "20220101", "20220101",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpfuXPYRo0AoLkM1sr8lTfu7hXxJzh9KQ-rQ&usqp=CAU"))

        b.recycler.adapter?.notifyDataSetChanged()
    }
}