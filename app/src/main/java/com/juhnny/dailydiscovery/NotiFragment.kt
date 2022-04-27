package com.juhnny.dailydiscovery

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentNotiBinding

class NotiFragment : Fragment() {

    val appCompatActivity by lazy { requireActivity() as AppCompatActivity }
    val b by lazy { FragmentNotiBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appCompatActivity.setSupportActionBar(b.toolbar)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
    }

    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Toast.makeText(context, "callback 받음", Toast.LENGTH_SHORT).show()
                Log.e("myTag", "관리 중인 프래그먼트 목록: ${parentFragmentManager.fragments}")
                //현재 프래그먼트는 닫게 하고
                parentFragmentManager.beginTransaction().remove(this@NotiFragment).commit()
                //원래 있던 프래그먼트는 보여달라고 하기
                val result = parentFragmentManager.beginTransaction().show(parentFragmentManager.findFragmentByTag("MYBIO_FRAG")!!).commit()
                Log.e("myResult", "${result}")
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
    }
}