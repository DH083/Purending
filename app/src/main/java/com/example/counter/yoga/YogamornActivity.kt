package com.example.counter.yoga

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.MediaController
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.example.counter.R

class YogamornActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yoga)

        Toast.makeText(this@YogamornActivity, "재생 버튼을 눌러 동영상을 재생해주세요.", Toast.LENGTH_SHORT).show()

        val videoView = findViewById<VideoView>(R.id.videoView)

        videoView.setMediaController(MediaController(this))
        //videoView에 MediaController연결

        videoView.setVideoURI(Uri.parse("android.resource://" + packageName + "/"+R.raw.morning_stretch))
        //resource 폴더 안 raw 폴더 안에 있는 music이라는 동영상 연결

        val btnStart = findViewById<ImageView>(R.id.start)
        btnStart.setOnClickListener {
            videoView.start() // 동영상 재개
            btnStart.setVisibility(View.GONE)

        }
    }
}