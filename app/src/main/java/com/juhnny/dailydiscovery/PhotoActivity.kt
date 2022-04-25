package com.juhnny.dailydiscovery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.juhnny.dailydiscovery.databinding.ActivityPhotoBinding

class PhotoActivity : AppCompatActivity() {

    val b by lazy { ActivityPhotoBinding.inflate(layoutInflater) }
    var photos = ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        photos = intent.getParcelableArrayListExtra<Photo>("photos")!! //얘한테 null 안들어오게 해줘라
        val currentPos = intent.getIntExtra("position", 0)
        Log.e("myPhotos" , photos.toString()) //[Photo(no=1, topic=주제명, message=주제에 대한 설명, nickname=hong1, creationDate=20220101, updateDate=20220101, src=2131165346), Photo(생략), Photo(생략) ... ]

        b.pagerPhoto.adapter = PhotoPagerAdapter(this, photos)
        b.pagerPhoto.currentItem = currentPos

    }


}