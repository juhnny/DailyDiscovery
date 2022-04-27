package com.juhnny.dailydiscovery

import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentNoticeBinding

class NoticeFragment:Fragment() {

    val appCompatActivity by lazy { requireActivity() as AppCompatActivity }
    val b by lazy { FragmentNoticeBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        setHasOptionsMenu(true)
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appCompatActivity.setSupportActionBar(b.toolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> parentFragmentManager.beginTransaction().remove(this).commit()
        }
        return super.onOptionsItemSelected(item)
    }

}