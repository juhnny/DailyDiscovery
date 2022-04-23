package com.juhnny.dailydiscovery

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.juhnny.dailydiscovery.databinding.ActivityFollowBinding

class FollowActivity: AppCompatActivity() {

    val b by lazy { ActivityFollowBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)


    }
}