package com.juhnny.dailydiscovery

import android.os.Parcel
import android.os.Parcelable

//data 클래스와 일반 클래스의 차이는 toString()과 equals()
//일반 클래스에서 toString()이 객체 정보를 알려준다면 data 클래스에서는 주생성자의 멤버변수명과 값을 나열해준다.
//일반 클래스에서 equals()가 동일한 인스턴스인지 여부를 알려준다면 data 클래스에서는 멤버변수의 값이 같은지 비교해준다. String 클래스에서처럼.
//단, 주생성자의 멤버변수들에 한해서!
data class Photo(var no:String,
                 var topic:String,
                 var message:String,
                 val userEmail:String,
                 val nickname:String,
                 var creationDate:String,
                 var updateDate:String,
                 var imgUrl:String) : Parcelable{

    //Parcel을 Photo 객체로 변환하기 위한 보조생성자
    //콜론 뒤에는 주생성자
    //constructor() : this()
    constructor(parcel: Parcel) : this(
        parcel.readString().toString(), //혹시 파라미터 값으로 null이 들어왔어도 그냥 문자로 변환해 보여줘
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString(),
        parcel.readString().toString()
    ) {
    }

    //Parcel protocol - Parcel을 사용하는 통신
    //먼저 빈 Parcel 안에 이 객체 정보를 담을 수 있어야 하고..
    //상대방이 정보가 담긴 Parcel이 왔을 때 쓰여진 정보를 가지고 이 객체를 만들어낼 수 있어야 한다.
    //마치 레고를 분해해 박스에 순서대로 담아 보내면 상대방이 다시 순서대로 조립해내는 느낌

    //아래 describeContents()와 writeToParcel()이 빈 상자에 전달하려는 객체의 정보를 쓰는 과정 같고

    //참고: https://archijude.tistory.com/284 [글을 잠깐 혼자 써봤던 진성 프로그래머]
    override fun describeContents(): Int {
        return 0
    }


    override fun writeToParcel(p0: Parcel?, p1: Int) { //p1은 Photo 객체(의 정보)를 몇 개 보내는지 알려주는 건가? 사용하지 않아도 알아서 ArrayList로 넘어가는데?
        p0?.writeString(no)
        p0?.writeString(topic)
        p0?.writeString(message)
        p0?.writeString(userEmail)
        p0?.writeString(nickname)
        p0?.writeString(creationDate)
        p0?.writeString(updateDate)
        p0?.writeString(imgUrl)
    }

    //Creator 객체는 다시 받은 Parcel 정보를 읽어서 Photo 객체를 만들어낸다.
    companion object CREATOR : Parcelable.Creator<Photo> {
        override fun createFromParcel(parcel: Parcel): Photo {
            return Photo(parcel) //위에서 구현한 보조생성자 이용
        }

        override fun newArray(size: Int): Array<Photo?> {
            return arrayOfNulls(size)
        }
    }


}