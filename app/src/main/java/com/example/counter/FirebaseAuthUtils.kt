package com.example.counter

import com.google.firebase.auth.FirebaseAuth
import java.text.SimpleDateFormat
import java.util.*

class FirebaseAuthUtils {

    companion object {
        private lateinit var auth : FirebaseAuth

        fun getUid() : String {
            auth = FirebaseAuth.getInstance()

            return auth.currentUser?.uid.toString()
        }

        fun getTime() : String {
            val currentDateTime = Calendar.getInstance().time
            val dateFormat = SimpleDateFormat("yyyy년 MM월 dd일", Locale.KOREA).format(currentDateTime)

            return dateFormat
        }
    }
}