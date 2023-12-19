package com.example.counter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import com.example.counter.Model.UserDataModel
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.ktx.Firebase
import com.google.firebase.auth.ktx.auth

class JoinActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth

    private var email = ""
    private var password = ""
    private var passchk = ""
    private var uid  = ""

    override fun onCreate(savedInstanceState: Bundle?) {

        auth = Firebase.auth

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_join)

        val btnSign = findViewById<Button>(R.id.btn_sign)
        btnSign.setOnClickListener {

            email = findViewById<TextInputEditText>(R.id.area_email).text.toString()
            password = findViewById<TextInputEditText>(R.id.area_pass).text.toString()
            passchk = findViewById<TextInputEditText>(R.id.area_passchk).text.toString()

            auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this) { task ->
                    if (email == "" || password == "" || passchk == "") Toast.makeText(
                        this, "회원정보를 전부 입력해주세요.",
                        Toast.LENGTH_SHORT
                    ).show() else {
                        if (password == passchk) {
                            // 회원가입 성공시
                            val user = auth.currentUser
                            uid = user?.uid.toString()

                            //유저 데이터 저장
                            val userModel = UserDataModel(
                                uid
                            )
                            FirebaseRef.userInfoRef.child(uid).setValue(userModel)

                            val intent = Intent(this, MainActivity::class.java)
                            finishAffinity()
                            startActivity(intent)
                            Toast.makeText(this, "회원가입 성공", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(
                                this, "비밀번호가 일치하지 않습니다.",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }

        //뒤로가기
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            finish()
        }
    }
}