package com.juhnny.dailydiscovery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juhnny.dailydiscovery.databinding.RecyclerItemGalleryBinding

class BioRecyclerAdapter(val context:Context, var photos:MutableList<Photo>): RecyclerView.Adapter<BioRecyclerAdapter.VH>() {

    inner class VH(val binding: RecyclerItemGalleryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(photo:Photo){
            Glide.with(context).load(photo.imgUrl).into(binding.ivPhoto)
            binding.tvTopicname.text = photo.topic
            binding.tvMsg.text = photo.message
//            binding.tvNickname.text = photo.nickname
//            binding.tvCreationDate.text = photo.creationDate
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RecyclerItemGalleryBinding.inflate(LayoutInflater.from(context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        var photo = photos[position]
        holder.bind(photo)

        holder.binding.root.setOnClickListener{
            val intent = Intent(context, PhotoActivity::class.java)
            intent.putParcelableArrayListExtra("photos", ArrayList(photos))
            intent.putExtra("position", position)
            context.startActivity(intent)

        }
    }

    override fun getItemCount(): Int = photos.size
}