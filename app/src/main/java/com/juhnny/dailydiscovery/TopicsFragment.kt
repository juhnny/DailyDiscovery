package com.juhnny.dailydiscovery

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.firebase.auth.FirebaseAuth
import com.juhnny.dailydiscovery.databinding.FragmentTopicsBinding
import retrofit2.Call
import retrofit2.Callback
import java.time.OffsetDateTime

class TopicsFragment : Fragment() {

    val mainActivity by lazy { requireActivity() as MainActivity }
    val b by lazy {FragmentTopicsBinding.inflate(layoutInflater)}
    val fragmets = mutableListOf<Fragment>()

    val topics = mutableListOf<Topic>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setSupportActionBar(b.toolbar)
        mainActivity.supportActionBar?.setDisplayShowTitleEnabled(false)

        if(FirebaseAuth.getInstance().currentUser?.email == "iwibest@naver.com") {
            b.ivAddTopic.visibility = View.VISIBLE
            b.ivAddTopic.setOnClickListener { addTopic() }
        }

        b.recycler.adapter = TopicsRecyclerAdapter(requireContext(), topics)
        loadTopicStub()
        loadTopic()
//        b.recycler.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))

    }

    private fun addTopic(){
        val time = OffsetDateTime.now()
        val newTopic = "새 주제"
        val call:Call<String> = RetrofitHelper.getRetrofitInterface().addTopic(newTopic)
        call.enqueue(object : Callback<String>{
            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
                val resultMsg = response.body()
                Log.e("TopicFrag addTopic Success", "$resultMsg")
                Toast.makeText(requireContext(), "added topic '$newTopic'", Toast.LENGTH_SHORT).show()
            }

            override fun onFailure(call: Call<String>, t: Throwable) { Log.e("TopicFrag addTopic Failure", "${t.message}") }
        })
    }

    private fun loadTopic(){
        val call:Call<Response<Topic>> = RetrofitHelper.getRetrofitInterface().loadTopic()
        call.enqueue(object : Callback<Response<Topic>>{
            override fun onResponse(
                call: Call<Response<Topic>>,
                response: retrofit2.Response<Response<Topic>>
            ) { val header = response.body()?.responseHeader
                val body = response.body()?.responseBody
                if(header != null && body != null){
                    val newTopics = body.items
                    Log.e("TopicFrag loadTopic Success", "${header.resultMsg} \n${body.itemCount} items : $newTopics")
                    topics.addAll(newTopics)
                    b.recycler.adapter?.notifyDataSetChanged()
                }
            }

            override fun onFailure(call: Call<Response<Topic>>, t: Throwable) {
                Log.e("TopicFrag loadTopic Failure", "${t.message}")
            }
        })
    }

    private fun loadTopicStub(){
        for(i in 0..2){
            var n = i + 1
            topics.add(Topic("$n", "주제$n", true, "2022/1/${n}", "2022/1/${n}"))
        }
        b.recycler.adapter?.notifyDataSetChanged()
    }

}