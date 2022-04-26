package com.juhnny.dailydiscovery

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.juhnny.dailydiscovery.databinding.ActivityInvitationBinding

class InvitationActivity : AppCompatActivity() {

    val b by lazy { ActivityInvitationBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        b.pagerInvitation.adapter = InvitationPagerAdapter(this)

        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }
}