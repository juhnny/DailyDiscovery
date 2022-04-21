package com.juhnny.dailydiscovery

import com.juhnny.dailydiscovery.databinding.FragmentBioMyBinding

class MyBioFragment : BioFragment() {

    //오버라이드 되도록 BioFragment의 b를 var로 바꿔보자
    //그러면 여기서 바꿔진 바인딩으로 상속받은 메소드들이 작동하지 않을까?
    val b by lazy { FragmentBioMyBinding.inflate(layoutInflater) }

}