package com.juhnny.dailydiscovery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juhnny.dailydiscovery.databinding.RecyclerItemGalleryBinding

class GalleryRecyclerAdapter(val context:Context, var photos:MutableList<Photo>): RecyclerView.Adapter<GalleryRecyclerAdapter.VH>() {

    inner class VH(val binding: RecyclerItemGalleryBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(photo:Photo){
            Glide.with(context).load(photo.imgUrl)
                .placeholder(R.drawable.ic_image_search_200_trans)
                .error(R.drawable.ic_image_search_200_trans)
                .into(binding.ivPhoto)
            binding.tvTopicname.visibility = View.GONE
            binding.tvMsg.text = photo.message
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