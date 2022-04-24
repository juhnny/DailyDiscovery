package com.juhnny.dailydiscovery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentGalleryBinding
import com.juhnny.dailydiscovery.databinding.FragmentTopicsBinding

class GalleryFragment : Fragment() {

    val b by lazy {FragmentGalleryBinding.inflate(layoutInflater)}

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        b.tv.setOnClickListener{
            if(parentFragmentManager.fragments.size == 1)
                parentFragmentManager.beginTransaction().add(R.id.container_bnv, TopicsFragment()).addToBackStack(null).commit()
            else parentFragmentManager.beginTransaction().replace(R.id.container_bnv, TopicsFragment()).addToBackStack(null).commit()
            Toast.makeText(context, "" + parentFragmentManager.fragments.size, Toast.LENGTH_SHORT).show()

        }

    }

    override fun onDestroy() {
        super.onDestroy()
//        parentFragmentManager.beginTransaction().show(parentFragmentManager.)
    }

}