package com.juhnny.dailydiscovery

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentBioMyBinding

class MyBioFragment : Fragment(){

    val mainActivity by lazy { requireActivity() as MainActivity }
    val appCompatActivity by lazy { requireActivity() as AppCompatActivity }
    val b by lazy {FragmentBioMyBinding.inflate(layoutInflater)}

    var photos = mutableListOf<Photo>()

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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        appCompatActivity.setSupportActionBar(b.toolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        b.recycler.adapter = BioRecyclerAdapter(requireContext(), photos)

        loadPhotos()

    }



    fun loadPhotos(){
        photos.add(Photo("1", "주제명", "A material metaphor is the unifying theory of a rationalized space and a system of motion.\n" +
                "\n" +
                "        Components with\n" +
                "        responsive elevations\n" +
                "        may encounter other components\n" +
                "        as they move between.", "hong1", "20220101", "20220101",
            "https://images.pexels.com/photos/1001682/pexels-photo-1001682.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
        photos.add(Photo("2", "주제명", "주제에 대한 설명", "hong2", "20220101", "20220101",
            "https://images.pexels.com/photos/1802268/pexels-photo-1802268.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
        photos.add(Photo("3", "주제명", "주제에 대한 설명", "hong3", "20220101", "20220101",
            "https://images.unsplash.com/photo-1439405326854-014607f694d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8c2VhfGVufDB8fDB8fA%3D%3D&w=1000&q=80"))
        photos.add(Photo("4", "주제명", "주제에 대한 설명", "hong4", "20220101", "20220101",
            "https://cdn.pixabay.com/photo/2016/12/17/14/33/wave-1913559__480.jpg"))
        photos.add(Photo("5", "주제명", "주제에 대한 설명", "hong5", "20220101", "20220101",
            "https://image.bugsm.co.kr/album/images/500/151085/15108518.jpg"))
        photos.add(Photo("6", "주제명", "주제에 대한 설명", "hong6", "20220101", "20220101",
            "https://as2.ftcdn.net/v2/jpg/01/29/27/99/1000_F_129279909_yZfwKBPu4o4c6Mdt1fgXIX1tJY59258r.jpg"))
        photos.add(Photo("7", "주제명", "주제에 대한 설명", "hong7", "20220101", "20220101",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpfuXPYRo0AoLkM1sr8lTfu7hXxJzh9KQ-rQ&usqp=CAU"))
        photos.add(Photo("1", "주제명", "주제에 대한 설명", "hong1", "20220101", "20220101",
            "https://images.pexels.com/photos/1001682/pexels-photo-1001682.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
        photos.add(Photo("2", "주제명", "주제에 대한 설명", "hong2", "20220101", "20220101",
            "https://images.pexels.com/photos/1802268/pexels-photo-1802268.jpeg?auto=compress&cs=tinysrgb&dpr=1&w=500"))
        photos.add(Photo("3", "주제명", "주제에 대한 설명", "hong3", "20220101", "20220101",
            "https://images.unsplash.com/photo-1439405326854-014607f694d7?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8c2VhfGVufDB8fDB8fA%3D%3D&w=1000&q=80"))
        photos.add(Photo("4", "주제명", "주제에 대한 설명", "hong4", "20220101", "20220101",
            "https://cdn.pixabay.com/photo/2016/12/17/14/33/wave-1913559__480.jpg"))
        photos.add(Photo("5", "주제명", "주제에 대한 설명", "hong5", "20220101", "20220101",
            "https://image.bugsm.co.kr/album/images/500/151085/15108518.jpg"))
        photos.add(Photo("6", "주제명", "주제에 대한 설명", "hong6", "20220101", "20220101",
            "https://as2.ftcdn.net/v2/jpg/01/29/27/99/1000_F_129279909_yZfwKBPu4o4c6Mdt1fgXIX1tJY59258r.jpg"))
        photos.add(Photo("7", "주제명", "주제에 대한 설명", "hong7", "20220101", "20220101",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpfuXPYRo0AoLkM1sr8lTfu7hXxJzh9KQ-rQ&usqp=CAU"))

//        b.recycler.adapter?.notifyDataSetChanged()
    }

}