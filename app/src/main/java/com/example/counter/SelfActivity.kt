package com.example.counter

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import com.example.counter.Diary.DiaryListViewActivity
import com.example.counter.Walk.UserSetup
import com.example.counter.timer.TimerActivity
import com.example.counter.yoga.YogaActivity

class SelfActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self)

        //뒤로가기
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }

        val diary = findViewById<LinearLayout>(R.id.diary)
        diary.setOnClickListener {
            val intent = Intent(this, DiaryListViewActivity::class.java)
            startActivity(intent)
        }

        val timer = findViewById<LinearLayout>(R.id.timer)
        timer.setOnClickListener {
            val intent2 = Intent(this, TimerActivity::class.java)
            startActivity(intent2)
        }

        val yoga = findViewById<LinearLayout>(R.id.yoga)
        yoga.setOnClickListener {
            val intent = Intent(this, YogaActivity::class.java)
            startActivity(intent)
        }

        val step = findViewById<LinearLayout>(R.id.step)
        step.setOnClickListener {
            val intent = Intent(this, UserSetup::class.java)
            startActivity(intent)
        }
    }
}