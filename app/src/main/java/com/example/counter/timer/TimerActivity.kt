package com.example.counter.timer

import android.app.Dialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.*
import com.example.counter.MainActivity
import com.example.counter.R
import com.example.counter.databinding.ActivityTimerBinding
import java.util.*
import kotlin.concurrent.timer

class TimerActivity : AppCompatActivity() {

    private var timeSelected : Int = 0
    private var timeCountDown: CountDownTimer? = null
    private var timeProgress = 0
    private var pauseOffSet: Long = 0
    private var isStart = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_timer)

        val addBtn = findViewById<ImageButton>(R.id.addButton)
        addBtn.setOnClickListener {
            setTimeFunction()
        }

        val startBtn: Button = findViewById(R.id.btnPlayPause)
        startBtn.setOnClickListener {
            startTimerSetup()
        }

        val resetBtn: ImageButton = findViewById(R.id.btnReset)
        resetBtn.setOnClickListener {
            resetTime()
            val tipTv: TextView = findViewById(R.id.textviewtip)
            tipTv.visibility = View.GONE
        }

        val backBtn: ImageButton = findViewById(R.id.back)
        backBtn.setOnClickListener {
            finish()
        }
    }


    private fun resetTime() {
        if(timeCountDown!=null)
        {
            timeCountDown!!.cancel()
            timeProgress = 0
            timeSelected = 0
            pauseOffSet = 0
            timeCountDown = null
            val StartBtn : Button = findViewById(R.id.btnPlayPause)
            StartBtn.text = "시작"
            isStart = true
            val progressBar = findViewById<ProgressBar>(R.id.pbTimer)
            progressBar.progress = 0
            val timeLeftTv: TextView = findViewById(R.id.tvTimeLeft)
            timeLeftTv.text = "0"
            val tipTv: TextView = findViewById(R.id.textviewtip)
            tipTv.visibility = View.GONE
            val resetBtn: ImageButton = findViewById(R.id.btnReset)
            resetBtn.visibility = View.GONE
            val addBtn = findViewById<ImageButton>(R.id.addButton)
            addBtn.visibility = View.VISIBLE

        }
    }

    private fun timePause() {
        if(timeCountDown!=null) {
            timeCountDown!!.cancel()
        }
    }

    private fun startTimerSetup() {
        val startBtn: Button = findViewById(R.id.btnPlayPause)
        if(timeSelected>timeProgress) {
            if(isStart) {
                startBtn.text = "정지"
                startTimer(pauseOffSet)
                isStart = false
            }
            else {
                isStart = true
                startBtn.text = "재시작"
                timePause()
            }
        }
        else {
            Toast.makeText(this, "시간을 입력하세요", Toast.LENGTH_SHORT).show()
        }
    }

    private fun startTimer(pauseOffSetL: Long) {
        val progressBar = findViewById<ProgressBar>(R.id.pbTimer)
        progressBar.progress = timeProgress
        timeCountDown = object :CountDownTimer(
            (timeSelected*1000).toLong() - pauseOffSetL*1000, 1000)
        {
            override fun onTick(p0: Long) {
                timeProgress++
                pauseOffSet = timeSelected.toLong() - p0/1000
                progressBar.progress = timeSelected-timeProgress
                val timeLeftTv: TextView = findViewById(R.id.tvTimeLeft)
                timeLeftTv.text = (timeSelected - timeProgress).toString()
                val tipTv: TextView = findViewById(R.id.textviewtip)
                tipTv.visibility = View.VISIBLE
                val resetBtn: ImageButton = findViewById(R.id.btnReset)
                resetBtn.visibility = View.VISIBLE
            }

            override fun onFinish() {
                resetTime()
            }
        }.start()
    }

    private fun setTimeFunction() {
        val timeDialog = Dialog(this)
        timeDialog.setContentView(R.layout.add_dialog)
        val timeSet = timeDialog.findViewById<EditText>(R.id.etGetTime)
        val timeLeftTv: TextView = findViewById(R.id.tvTimeLeft)
        val btnStart: Button = findViewById(R.id.btnPlayPause)
        val processBar = findViewById<ProgressBar>(R.id.pbTimer)
        timeDialog.findViewById<Button>(R.id.btnOK).setOnClickListener {
            if(timeSet.text.isEmpty()) {
                Toast.makeText(this, "시간을 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else {
                resetTime()
                timeLeftTv.text = timeSet.text
                btnStart.text = "시작"
                timeSelected = timeSet.text.toString().toInt()
                processBar.max = timeSelected
            }
            timeDialog.dismiss()
        }
        timeDialog.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        if(timeCountDown!=null) {
            timeCountDown?.cancel()
            timeProgress = 0
        }
    }
}