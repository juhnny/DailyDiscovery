package com.juhnny.dailydiscovery

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.juhnny.dailydiscovery.databinding.RecyclerItemTopicsBinding

class TopicsRecyclerAdapter(val context: Context, var topics:MutableList<Topic>) : RecyclerView.Adapter<TopicsRecyclerAdapter.VH>() {

    class VH(val binding:RecyclerItemTopicsBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(topic:Topic){
            binding.tvTopicname.text = topic.topicName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val binding = RecyclerItemTopicsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return VH(binding)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val topic = topics[position]
        holder.bind(topic)

        holder.binding.root.setOnClickListener {
            val intent = Intent(context, GalleryActivity::class.java)
            intent.putExtra("topicSelected", topic.topicName)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = topics.size

}