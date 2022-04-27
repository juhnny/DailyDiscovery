package com.juhnny.dailydiscovery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import com.juhnny.dailydiscovery.databinding.ActivityPhotoBinding

class PhotoActivity : AppCompatActivity() {

    val b by lazy { ActivityPhotoBinding.inflate(layoutInflater) }
    var photos = ArrayList<Photo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)

        photos = intent.getParcelableArrayListExtra<Photo>("photos")!! //얘한테 null 안들어오게 해줘라
        val currentPos = intent.getIntExtra("position", 0)
        Log.e("myPhotos" , photos.toString()) //[Photo(no=1, topic=주제명, message=주제에 대한 설명, nickname=hong1, creationDate=20220101, updateDate=20220101, src=2131165346), Photo(생략), Photo(생략) ... ]

        b.pagerPhoto.adapter = PhotoPagerAdapter(this, photos)
        b.pagerPhoto.currentItem = currentPos

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        //이걸 추가해주지 않으면 업버튼을 눌러도 동작 안한다.
        when (item.itemId) {
            android.R.id.home -> onBackPressed() //혹은 finish()
        }
        return super.onOptionsItemSelected(item) //부모의 메소드는 앱 manifest에 명시한 부모 액티비티로 이동하도록 처리한다.
                                                //부모 액티비티로 지정한 액티비티가 백스택에 없으면 안 열리는 듯

    }


}