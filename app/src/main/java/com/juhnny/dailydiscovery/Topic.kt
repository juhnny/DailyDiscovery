package com.juhnny.dailydiscovery

data class Topic(var no:String,
                 var topicName:String,
                 var isIssued:Boolean,
                 var issueDatetime:String,
                 var lastPostDatetime:String) {
    //no, 주제명, 공식발행여부, 발행일, 게시물업데이트일
    //주제명도 유니크값이지만 no로 쿼리하는 게 빠를 거 같으니 no도 일단 받아두자.
}