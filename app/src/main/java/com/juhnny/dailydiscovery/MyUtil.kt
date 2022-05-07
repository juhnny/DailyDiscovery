package com.juhnny.dailydiscovery

import android.content.Context
import androidx.appcompat.app.AlertDialog

class MyUtil() {

    companion object{
        public fun showSorryAlert(context: Context){
            AlertDialog.Builder(context).setMessage("준비 중인 기능입니다. \n\n빠른 시일 내에 준비하겠습니다. 문제가 발생한 경우 문의주시면 감사하겠습니다.")
                .setPositiveButton("확인", null).show()
        }
    }

}