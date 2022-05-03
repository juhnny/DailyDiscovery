package com.juhnny.dailydiscovery

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.juhnny.dailydiscovery.databinding.ActivityEditorBinding
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Url
import java.text.SimpleDateFormat
import java.util.*

class EditorActivity : AppCompatActivity() {

    val b by lazy { ActivityEditorBinding.inflate(layoutInflater) }
    val storage by lazy { FirebaseStorage.getInstance() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)

        b.ivPhoto.setOnClickListener {
            val intent = Intent(Intent.ACTION_PICK)
            intent.type = "image/*"
            resultLauncher.launch(intent)
        }

    }//onCreate()

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.options_editor, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> {
                AlertDialog.Builder(this).setTitle("닫기")
                    .setMessage("닫으시겠습니까?\n작성 중이던 글은 저장되지 않습니다.")
                    .setPositiveButton("계속 작성", DialogInterface.OnClickListener {
                        dialogInterface, i -> Toast.makeText(this,"계속 작성", Toast.LENGTH_SHORT).show()})
                    .setNegativeButton("나가기", DialogInterface.OnClickListener( {
                        dialogInterface, i -> run{
                        Toast.makeText(this, "나가기", Toast.LENGTH_SHORT).show()

                        setResult(RESULT_CANCELED, intent)
                        finish()
                        }
                    }))
                    .show()
            }
            R.id.submit -> {
                if(imgUri == null){
                    Toast.makeText(this, "사진을 추가해주세요", Toast.LENGTH_SHORT).show()
                    return true
                }

                //입력 데이터 읽어와서
                var topic = b.etTopic.text.toString()
                var msg = b.etDesc.text.toString()

                if(topic.length !in 1..20){
                    Toast.makeText(this, "주제는 1~20자 사이로 입력해주세요", Toast.LENGTH_SHORT).show()
                    return true
                }
                if(msg.length !in 1..20){
                    Toast.makeText(this, "설명은 0~20자 사이로 입력해주세요", Toast.LENGTH_SHORT).show()
                    return true
                }

                val userId = FirebaseAuth.getInstance().currentUser?.email
                if(userId == null) {
                    Log.e("UserId is null","")
                    Toast.makeText(this, "유저 정보 가져오기 실패", Toast.LENGTH_SHORT).show()
                    return true
                }

                val format = SimpleDateFormat("yyyyMMdd-hhmmss", Locale.KOREA)
                val creationDate = format.format(Date())
                val updateDate = format.format(Date())

                //Firebase Storage에 사진 업로드
//                uploadImage()
                var fileName = "IMG_${SimpleDateFormat("yyyyMMdd_hhmmss", Locale.KOREA).format(Date())}_.png"
                imgRef = storage.getReference("uploads/photos/$fileName")
                imgRef.putFile(imgUri!!).addOnSuccessListener {
                    //파일 참조객체로부터 이미지 URL을 얻어오기 - URL을 얻어올 수 있다면 저장에 성공한 것
                    imgRef.downloadUrl.addOnCompleteListener {
                        //업로드 성공 시 액티비티 종료
                        //실패시 알림창 띄우고 재시도 하도록
                        if(it.isSuccessful){
                            imgFirebaseUrl = it.result.toString() //다운로드 주소 저장
                            Log.e("TAG 이미지 저장위치","${it.result}")
                            Toast.makeText(this, "사진 저장 완료", Toast.LENGTH_SHORT).show()

                            //Firebase Firestore에 전체 데이터 업로드
                            val builder:Retrofit.Builder = Retrofit.Builder()
                            builder.baseUrl("http://iwibest.dothome.co.kr")
                            builder.addConverterFactory(GsonConverterFactory.create())
                            val retrofit:Retrofit = builder.build()
                            val retrofitInterface:RetrofitInterface = retrofit.create(RetrofitInterface::class.java)

                            //요청 및 응답 처리
                            val call:Call<Photo> = retrofitInterface.saveWriting(topic, msg, userId, creationDate, updateDate, imgFirebaseUrl)
                            call.enqueue(object : Callback<Photo>{
                                override fun onResponse(call: Call<Photo>, response: Response<Photo>) {
                                    val photo:Photo? = response.body()
                                    if(photo != null){
                                        Log.e("TAG saveWriting Success", "${photo.no}, ${photo.topic}, " +
                                                "${photo.message}, ${photo.creationDate}, ${photo.updateDate}, " +
                                                "${photo.userId}, ${photo.imgUrl}")

                                        setResult(RESULT_OK, intent)
                                        finish()
                                    }
                                }

                                override fun onFailure(call: Call<Photo>, t: Throwable) {
                                    Log.e("TAG saveWriting Failure", "${t.message}")
                                    Log.e("TAG saveWriting Failure", "${t.localizedMessage}")
                                    Log.e("TAG saveWriting Failure", "${t.stackTrace}")
                                    Log.e("TAG saveWriting Failure", "${t.suppressed}")
                                    Log.e("TAG saveWriting Failure", "${t.cause}")
                                    Log.e("TAG saveWriting Failure", "${t.suppressedExceptions}")
                                }
                            })

                        } else {
                            Toast.makeText(this, "사진 저장 실패. 다시 시도해주세요.", Toast.LENGTH_SHORT).show()
                        }
                    }
                }





            }
        }
        return super.onOptionsItemSelected(item)

    }

    //Back버튼 눌렀을 때도 바로 닫히지 않고 안내 뜨게 해야함 - 한번 더 누르면 닫히고 내용은 저장되지 않는다고.

    //Firebase Storage에 업로드한 이미지의 URL
    lateinit var imgFirebaseUrl:String

    //갤러리에서 이미지 선택
    var imgUri:Uri? = null

    val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult(), ActivityResultCallback { result ->
        if(result.resultCode == RESULT_OK){
            val intent:Intent? = result.data
            imgUri = intent?.data
            Glide.with(baseContext).load(imgUri).into(b.ivPhoto)
        }
    })

    //이미지 업로드
    lateinit var imgRef:StorageReference //저장위치에 대한 포인터 같은 녀석

    private fun uploadImage(){
        var fileName = "IMG_${SimpleDateFormat("yyyyMMdd_hhmmss", Locale.KOREA).format(Date())}_.png"
        imgRef = storage.getReference("uploads/photos/$fileName")
        imgRef.putFile(imgUri!!).addOnSuccessListener {
            Toast.makeText(this, "사진 업로드 성공", Toast.LENGTH_SHORT).show()
        }

    }

}