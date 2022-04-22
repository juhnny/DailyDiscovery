package com.juhnny.dailydiscovery

import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.juhnny.dailydiscovery.databinding.ActivityEditorBinding

class EditorActivity : AppCompatActivity() {

    val b by lazy { ActivityEditorBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(b.root)

        setSupportActionBar(b.toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        supportActionBar!!.setHomeAsUpIndicator(R.drawable.ic_close)

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

                        setResult(RESULT_OK, intent)
                        finish()
                        }
                    }))
                    .show()
            }
            R.id.submit -> {
                Toast.makeText(this, "완료", Toast.LENGTH_SHORT).show()
            }
        }
        return super.onOptionsItemSelected(item)

    }

    //Back버튼 눌렀을 때도 바로 닫히지 않고 안내 뜨게 해야함 - 한번 더 누르면 닫히고 내용은 저장되지 않는다고.


}