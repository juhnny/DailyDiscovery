package com.juhnny.dailydiscovery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juhnny.dailydiscovery.databinding.RecyclerItemBioBinding
import com.juhnny.dailydiscovery.databinding.RecyclerItemGalleryBinding

class BioRecyclerAdapter(val context:Context, var photos:MutableList<Photo>): RecyclerView.Adapter<BioRecyclerAdapter.VH>() {

//    inner class VH(val binding: RecyclerItemGalleryBinding):RecyclerView.ViewHolder(binding.root){
//        fun bind(photo:Photo){
//            Glide.with(context).load(photo.imgUrl).into(binding.ivPhoto)
//            binding.tvTopicname.text = photo.topic
//            binding.tvMsg.text = photo.message
////            binding.tvNickname.text = photo.nickname
////            binding.tvCreationDate.text = photo.creationDate
//        }
//    }
    inner class VH(itemView:View):RecyclerView.ViewHolder(itemView){
        val binding = RecyclerItemBioBinding.bind(itemView) //itemView로 넘어온 뷰를 내 binding으로 삼겠다.
    }

//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
//        val binding = RecyclerItemGalleryBinding.inflate(LayoutInflater.from(context), parent, false)
//        return VH(binding)
//    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val itemView = LayoutInflater.from(context).inflate(R.layout.recycler_item_bio, parent, false)
        return VH(itemView)
    }

//    override fun onBindViewHolder(holder: VH, position: Int) {
//        var photo = photos[position]
//        holder.bind(photo)
//
//        holder.binding.root.setOnClickListener{
//            val intent = Intent(context, PhotoActivity::class.java)
//            intent.putParcelableArrayListExtra("photos", ArrayList(photos))
//            intent.putExtra("position", position)
//            context.startActivity(intent)
//
//        }
//    }
    override fun onBindViewHolder(holder: VH, position: Int) {
        var photo = photos[position]

        Glide.with(context).load(photo.imgUrl).into(holder.binding.ivPhoto)
        holder.binding.tvTopicname.text = photo.topic
        holder.binding.tvMsg.text = photo.message
//            binding.tvNickname.text = photo.nickname
//            binding.tvCreationDate.text = photo.creationDate

        holder.binding.root.setOnClickListener{
            val intent = Intent(context, PhotoActivity::class.java)
            intent.putParcelableArrayListExtra("photos", ArrayList(photos))
            intent.putExtra("position", position)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = photos.size
}