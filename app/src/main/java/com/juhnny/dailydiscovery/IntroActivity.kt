package com.juhnny.dailydiscovery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.app.ActivityOptionsCompat
import com.juhnny.dailydiscovery.databinding.ActivityIntroBinding

class IntroActivity : AppCompatActivity() {

    val b:ActivityIntroBinding by lazy { ActivityIntroBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.entrance.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
//            val optionsCompat = ActivityOptionsCompat.makeSceneTransitionAnimation(this, b.entrance, "introExpand")
            val optionsCompat = ActivityOptionsCompat.makeScaleUpAnimation(b.entrance, 42, 75, 1, 1)
//            val optionsCompat = ActivityOptionsCompat.makeThumbnailScaleUpAnimation(b.entrance, )
            startActivity(intent, optionsCompat.toBundle())
            finish()
        }
    }
}