package com.juhnny.dailydiscovery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentTodayBinding

class TodayFragment : Fragment() {

    val mainActivity by lazy { requireActivity() as MainActivity }
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

        mainActivity.setSupportActionBar(b.toolbar)
        mainActivity.supportActionBar?.setDisplayShowTitleEnabled(false)

        b.tvGotoWrite.setOnClickListener {
            val intent = Intent(context, EditorActivity::class.java)
//            intent.putExtra("topic", )
            mainActivity.editorResultLauncher.launch(intent)
        }

        b.tvTopic.setOnClickListener {
            mainActivity.b.bnv.menu.getItem(1).isChecked = true
            parentFragmentManager.beginTransaction().hide(mainActivity.fragments[0]).commit()
            val trans = parentFragmentManager.beginTransaction()
            if( ! parentFragmentManager.fragments.contains(mainActivity.fragments[1])) trans.add(R.id.container_bnv, mainActivity.fragments[1])
            trans.show(mainActivity.fragments[1])
            trans.commit()
        }
    }

    //오늘의 주제를 발행하는 방법은?

}