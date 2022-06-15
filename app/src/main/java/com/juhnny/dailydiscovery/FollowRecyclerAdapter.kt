package com.juhnny.dailydiscovery

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintSet
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.juhnny.dailydiscovery.databinding.RecyclerItemFollowBinding
import java.util.*

class FollowRecyclerAdapter(val context: Context, val fragment: Fragment, val follows:MutableList<Follow>):RecyclerView.Adapter<FollowRecyclerAdapter.VH>() {

    inner class VH(val binding:RecyclerItemFollowBinding) : RecyclerView.ViewHolder(binding.root){
        fun setData(followItem:Follow){
            binding.tvNickname.text = followItem.nickname
            binding.tvIntroduce.text = followItem.profileMsg

//            Glide.with(context).load(followItem.imgUrl1).placeholder(R.mipmap.ic_launcher).error(R.drawable.ic_launcher_foreground).into(binding.ivPhoto1)
//            Glide.with(context).load(followItem.imgUrl2).into(binding.ivPhoto2)
//            Glide.with(context).load(followItem.imgUrl3).into(binding.ivPhoto3)

            if(followItem.imgUrl1 != null) {
                Glide.with(context).load(followItem.imgUrl1).placeholder(R.drawable.ic_image_search_200_trans).error(R.drawable.ic_image_search_200_trans).into(binding.ivPhoto1)
                binding.frame1.visibility = View.VISIBLE
            } else binding.frame1.visibility = View.INVISIBLE
            if(followItem.imgUrl2 != null) {
                Glide.with(context).load(followItem.imgUrl2).placeholder(R.drawable.ic_image_search_200_trans).error(R.drawable.ic_image_search_200_trans).into(binding.ivPhoto2)
                binding.frame2.visibility = View.VISIBLE
            } else binding.frame2.visibility = View.INVISIBLE
            if(followItem.imgUrl3 != null) {
                Glide.with(context).load(followItem.imgUrl3).placeholder(R.drawable.ic_image_search_200_trans).error(R.drawable.ic_image_search_200_trans).into(binding.ivPhoto3)
                binding.frame3.visibility = View.VISIBLE
            } else binding.frame3.visibility = View.INVISIBLE

//            val constrainSet = ConstraintSet()
//            constrainSet.clone(binding.layoutPhotos)
//            constrainSet.setVerticalBias(R.id.frame1, Random().nextFloat())
//            constrainSet.setVerticalBias(R.id.frame2, Random().nextFloat())
//            constrainSet.setVerticalBias(R.id.frame3, Random().nextFloat())
//            constrainSet.applyTo(binding.layoutPhotos)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        return VH(RecyclerItemFollowBinding.inflate(LayoutInflater.from(context), parent, false))
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val follow = follows[position]
        holder.setData(follow)

        holder.binding.root.setOnClickListener{
            val appCompatActivity = context as AppCompatActivity //context의 정체는 adapter 생성자로 받아온 OOOActivity. 그러니 다운캐스팅 가능
//            appCompatActivity.supportFragmentManager.beginTransaction().replace(R.id.container_follwings_fragment, MyBioFragment()).addToBackStack(null).commit()
            fragment.childFragmentManager.beginTransaction().replace(R.id.follwings_fragment_root, MyBioFragment(follow.email)).addToBackStack(null).commit()
        }
    }

    override fun getItemCount(): Int = follows.size
}