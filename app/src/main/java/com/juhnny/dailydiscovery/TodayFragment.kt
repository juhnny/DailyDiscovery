package com.juhnny.dailydiscovery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentTodayBinding

class TodayFragment : Fragment() {

    val b by lazy { FragmentTodayBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.tvGotoWrite.setOnClickListener {
            val intent = Intent(context, EditorActivity::class.java)
//            intent.putExtra("topic", )
            val ac = activity as MainActivity
            ac.editorResultLauncher.launch(intent)
        }
    }

    //오늘의 주제를 발행하는 방법은?

}