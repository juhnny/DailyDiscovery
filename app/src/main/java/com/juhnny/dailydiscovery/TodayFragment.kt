package com.juhnny.dailydiscovery

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.Task
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.juhnny.dailydiscovery.databinding.FragmentTodayBinding
import java.text.ParsePosition
import java.text.SimpleDateFormat
import java.util.*

class TodayFragment : Fragment() {

    val mainActivity by lazy { requireActivity() as MainActivity }
    val b by lazy { FragmentTodayBinding.inflate(layoutInflater) }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return b.root
        //이 프래그먼트는 replace를 당해도 addToBackStack() 이후 다시 Back 해서 확인해보면 뷰들이 그대로 남아있다.
        //원래 replace는 remove & add 이기 때문에 프래그먼트가 remove되면서 그에 속한 view들도 container로부터 remove 되는 게 일반적.
        //알고보니 그 이유는 return b.root로 넘긴 뷰들이 멤버변수에 속해있는 뷰들이기 때문
        //onCreateView에서 LayoutInflator로 만든 뷰 객체들은 Fragment가 remove될 때 같이 remove되어 더 이상 참조되지 않게 되면 제거됨
        //헌데 멤버변수인 바인딩에 소속된 뷰 객체들은 remove 되어도 멤버변수 소속이므로 계속 참조상태로 남아있기 때문에 제거되지 않음
        //이런 원리를 알고 쓴다면 b.root를 사용해도 된다.
        //만약 binding 객체가 이 메소드 안에서 로컬로 만들어졌다면 remove 된 이후 결국 사라질 것이다.
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivity.setSupportActionBar(b.toolbar)
        mainActivity.supportActionBar?.setDisplayShowTitleEnabled(false)

        loadTopic()

        b.ivNoti.setOnClickListener{
            childFragmentManager.beginTransaction().add(R.id.today_fragment_root, NotiFragment()).addToBackStack(null).commit()
        }

        b.tvGotoWrite.setOnClickListener {
            val intent = Intent(context, EditorActivity::class.java)
//            intent.putExtra("topic", )
            mainActivity.editorResultLauncher.launch(intent)
        }

        b.tvTopic.setOnClickListener {
            val auth:FirebaseAuth = FirebaseAuth.getInstance()
            auth.signOut()
            Toast.makeText(requireContext(), "signOut", Toast.LENGTH_SHORT).show()
        }

//        //해당 탭을 닫고 다른 탭으로 이동하는 방법
//        b.tvTopic.setOnClickListener {
//            mainActivity.b.bnv.menu.getItem(1).isChecked = true
//            parentFragmentManager.beginTransaction().hide(mainActivity.fragments[0]).commit()
//            val trans = parentFragmentManager.beginTransaction()
//            if( ! parentFragmentManager.fragments.contains(mainActivity.fragments[1])) trans.add(R.id.container_bnv, mainActivity.fragments[1])
//            trans.show(mainActivity.fragments[1])
//            trans.commit()
//        }

    }//onViewCreated



    //오늘의 주제를 발행하는 방법은?
    private fun loadTopic(){
        Log.e("TAG Today 8 am", "${Date()}")
        Log.e("TAG Today 8 am", "${Date()}")
        Log.e("TAG Today 8 am", "${Date()}")
        Log.e("TAG Today 8 am", "${Date()}")
        Log.e("TAG Today 8 am", "${Date()}")
        Log.e("TAG Today 8 am", "${Date()}")

        val firestore:FirebaseFirestore = FirebaseFirestore.getInstance()
        val collectionTopics = firestore.collection("topics")
        collectionTopics.get().addOnCompleteListener(object: OnCompleteListener<QuerySnapshot>{
            override fun onComplete(task: Task<QuerySnapshot>) {
                if(task.isSuccessful){
                    val querySnapshots = task.getResult() //하나의 QuerySnapshot 안에 여러 개의 Document Snapshot이 들어있다.
                    for(snapshot:DocumentSnapshot in querySnapshots){
                        val topic:Map<String, Any>? = snapshot.data
                        val no:String = topic!!["no"].toString()
                        val topicName:String = topic!!["id"].toString()
                        val issueDateTimestamp:Timestamp = topic!!["issue_date"] as Timestamp
                        val issueDateSecs = issueDateTimestamp.toDate()

                        val format = SimpleDateFormat("yyyyMMdd_hhmmss", Locale.KOREA)
                        format.timeZone = TimeZone.getTimeZone("Asia/Seoul") //setTimeZone
                        val issueDate = format.format(issueDateSecs)

                        //매일 오전 08시를 Date 객체로 만들어서 비교
                        var pos = ParsePosition(0)
                        val parsedDate = format.parse("20220502_100000", pos)

                        Log.e("TAG parsedDate", "$parsedDate")
                        Log.e("TAG topic", "$no, $topicName, $issueDate")
                    }

                } else{
                    Log.e("TAG loadTopic", "get() 실패 ${task.exception}")
                }
            }
        })
    }//loadTopic()

}