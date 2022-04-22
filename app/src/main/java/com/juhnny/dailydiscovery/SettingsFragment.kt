package com.juhnny.dailydiscovery

import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    val b by lazy { FragmentSettingsBinding.inflate(layoutInflater)}

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
            requireActivity().supportFragmentManager.beginTransaction().add(R.id.container, AccountFragment()).addToBackStack(null).commit()
        }
    }

    override fun onResume() {
        super.onResume()

    }
}