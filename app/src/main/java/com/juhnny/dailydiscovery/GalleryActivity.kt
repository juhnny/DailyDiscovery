package com.juhnny.dailydiscovery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juhnny.dailydiscovery.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {

    //선택된 주제를 받아서
    //Firestore의 Photo 컬렉션에서 필드가 그 주제로 된 사진을 최신순으로 일정 개수 가져온다.
    //가져와서 Photo 데이터 클래스에 저장하고 리스트에 추가해 관리
    //리사이클러뷰로 보여주고
    //아래로 스크롤 시 추가 로딩
    //항목 선택 시 새 액티비티로 사진 크게 로딩

    val b by lazy { ActivityGalleryBinding.inflate(layoutInflater) }
    val topicName by lazy { intent.getStringExtra("topicName") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = topicName



    }
}