package com.juhnny.dailydiscovery

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
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
            android.R.id.home -> {
                Log.e("Notice - home", "")
                parentFragmentManager.beginTransaction()
                    .remove(this)
                    .show(parentFragmentManager.findFragmentByTag("MYBIO_FRAG")!!) //이걸 지워도 왜 MyBio가 보여지지?
                    .commit()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private lateinit var callback: OnBackPressedCallback
    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Log.e("Notice - onBack callback 받음", "")
                parentFragmentManager.beginTransaction().remove(this@NoticeFragment)
                    .show(parentFragmentManager.findFragmentByTag("MYBIO_FRAG")!!)
                    .commit()
                Log.e("Notice Frag removed, Mybio Frag showed", "")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

}