package com.juhnny.dailydiscovery

data class Topic(var no:String,
                 var topicName:String,
                 var isIssued:Boolean,
                 var issueDate:String,
                 var updatedDate:String) {
    //no, 주제명, 공식발행여부, 발행일, 게시물업데이트일
}