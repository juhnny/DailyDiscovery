package com.juhnny.dailydiscovery

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.juhnny.dailydiscovery.databinding.FragmentBioMyBinding

class MyBioFragment : Fragment(){

    val appCompatActivity by lazy { requireActivity() as AppCompatActivity }
    val b by lazy {FragmentBioMyBinding.inflate(layoutInflater)}

    var photos = mutableListOf<Photo>()

   override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        setHasOptionsMenu(true) //잊지마
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.e("myParentFragment: ", "${parentFragment}")

        appCompatActivity.setSupportActionBar(b.toolbar)
        appCompatActivity.supportActionBar?.setDisplayShowTitleEnabled(false)
        appCompatActivity.supportActionBar?.setDisplayHomeAsUpEnabled(true)

        b.recycler.adapter = BioRecyclerAdapter(requireContext(), photos)

        loadPhotos()

    }

//    업 버튼(홈 메뉴)을 누르면 프래그먼트가 닫히도록
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                Toast.makeText(requireContext(), "MyBio - home", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction().remove(this).commit()
                return true
            }
        }
        Toast.makeText(requireContext(), "MyBio - home not clicked", Toast.LENGTH_SHORT).show()
        return super.onOptionsItemSelected(item)
    }

    //Activity에 onBackPressed 발생 시 Fragment에 콜백이 오도록 하고 그 때 handleOnBackPressed() 내 명령 실행
    private lateinit var callback: OnBackPressedCallback

    override fun onAttach(context: Context) {
        super.onAttach(context)
        callback = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                Toast.makeText(context, "MyBio - callback 받음", Toast.LENGTH_SHORT).show()
                parentFragmentManager.beginTransaction().remove(this@MyBioFragment).commit()
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, callback)
    }

    override fun onDetach() {
        super.onDetach()
        callback.remove()
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

        b.recycler.adapter?.notifyDataSetChanged()
    }

}