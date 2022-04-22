package com.juhnny.dailydiscovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentBioMyBinding

class MyBioFragment : Fragment(){

    val b by lazy {FragmentBioMyBinding.inflate(layoutInflater)}

    //오버라이드 되도록 BioFragment의 b를 var로 바꿔보자
    //그러면 여기서 바꿔진 바인딩으로 상속받은 메소드들이 작동하지 않을까?

    //부모 프래그먼트에서 뷰들의 기능을 미리 정의해놓고
    //자식 프래그먼트에서

   override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

}