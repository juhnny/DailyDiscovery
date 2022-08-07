package com.juhnny.dailydiscovery

import androidx.annotation.Keep

// release build에서 Retrofit2가 json을 변환하지 못하는 문제 발생
// 서버로부터 들어오는 데이터는 String으로 찍어보니 문제 없었음
// build.grade(app)의 buildTypes의 release에서 minifyEnabled true를 해제하니 전환 성공
// minify는 빌드 시 쓰이지 않는 클래스들을 제외시키는 것
// 즉, 이 데이터클래스들이 쓰이지 않는다고 판단해서 빌드에 포함이 되지 않아 Retrofit2에서 파싱하지 못한 것
// 클래스에 @Keep annotation을 사용하면 minify 시 무조건 빌드에 포함됨 -> 해결!
@Keep
data class ResponseBody<T> (val itemCount:Int,
                            val items:List<T>) {

}