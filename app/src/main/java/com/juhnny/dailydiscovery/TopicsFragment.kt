package com.juhnny.dailydiscovery

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.juhnny.dailydiscovery.databinding.FragmentTopicsBinding

class TopicsFragment : Fragment() {

    val b by lazy {FragmentTopicsBinding.inflate(layoutInflater)}
    val fragmets = mutableListOf<Fragment>()

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
//            val galleryFragment = GalleryFragment()
//            fragmets.add(galleryFragment)
            parentFragmentManager.beginTransaction().add(R.id.container_bnv, GalleryFragment()).addToBackStack(null).commit()
            Toast.makeText(context, "" + parentFragmentManager.fragments.size, Toast.LENGTH_SHORT).show()
        }

    }

}