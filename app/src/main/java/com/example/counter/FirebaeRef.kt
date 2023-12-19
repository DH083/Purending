package com.example.counter

import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.database.ktx.database

class FirebaseRef {

    companion object {

        private val database = Firebase.database
        
        val userInfoRef = database.getReference("userInfo")

        //다이어리
        val diaryRef = database.getReference("diary").child(Firebase.auth.currentUser!!.uid)

        val happyRef = database.getReference("diary").child(Firebase.auth.currentUser!!.uid).child("happy")
        val sadRef = database.getReference("diary").child(Firebase.auth.currentUser!!.uid).child("sad")
        val angryRef = database.getReference("diary").child(Firebase.auth.currentUser!!.uid).child("angry")
        val tiredRef = database.getReference("diary").child(Firebase.auth.currentUser!!.uid).child("tired")
        val surprisedRef = database.getReference("diary").child(Firebase.auth.currentUser!!.uid).child("surpirsed")
    }
}