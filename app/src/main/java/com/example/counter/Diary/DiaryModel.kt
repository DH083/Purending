package com.example.counter.Diary

data class DiaryModel(
    val title: String = "",
    val content : String = "",
    val time : String = "",
    var mood: String = ""
)