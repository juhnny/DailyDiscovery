package com.juhnny.dailydiscovery

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.juhnny.dailydiscovery.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    val b by lazy { ActivitySettingsBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)


    }
}