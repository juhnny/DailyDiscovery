package com.juhnny.dailydiscovery

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bumptech.glide.Glide
import com.juhnny.dailydiscovery.databinding.RecyclerItemPhotoBinding

class PhotoPagerAdapter(val context: Context, val photos:ArrayList<Photo>): RecyclerView.Adapter<PhotoPagerAdapter.VH>() {

    inner class VH(val binding:RecyclerItemPhotoBinding):RecyclerView.ViewHolder(binding.root){
        fun setData(photo:Photo){
            Glide.with(context).load(photo.src).into(binding.ivPhoto)
            binding.tvTopicname.text = photo.topic
            binding.tvNickname.text = photo.nickname
            binding.tvMsg.text = photo.message
            binding.tvCreationDate.text = photo.creationDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RecyclerItemPhotoBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        Log.e("tag photos: ", photos.toString())
        var photo = photos[position]
        holder.setData(photo)

        holder.binding.tvNickname.setOnClickListener {
            val appCompatActivity = context as AppCompatActivity //맞다. 애초에 저 context의 정체가 adapter 생성자에 인수로 집어넣은 OOOActivity였지..
            appCompatActivity.supportFragmentManager.beginTransaction().add(R.id.view_root, MyBioFragment()).addToBackStack(null).commit()

        }
    }

    override fun getItemCount(): Int { return photos.size }
}