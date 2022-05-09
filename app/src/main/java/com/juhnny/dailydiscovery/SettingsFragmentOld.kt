package com.juhnny.dailydiscovery

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentSettingsBinding

class SettingsFragmentOld : Fragment() {

    val mainActivity by lazy { requireActivity() as MainActivity }
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
            val accountFragment = AccountFragment()
            requireActivity().supportFragmentManager.beginTransaction()
                .add(R.id.container_bnv, accountFragment).addToBackStack(null).commit()
        }
        //처음엔 SettingsFragment 안에 있는 container 영역에 AccountFragment를 띄웠는데
        //AccountFragment에서 설정한 툴바까지도 그 영역 안에 만들어져서 툴바가 바깥에 하나 안에 하나씩 두개가 돼버리는 문제가..
        //아예 SettingsFragment가 만들어지는 동일한 container에 AccountFragment를 만들어 덮어씌우는 게 정답이었다.

        b.tvActivity.setOnClickListener(){
            requireActivity().startActivity(Intent(context, AccountActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()

    }
}