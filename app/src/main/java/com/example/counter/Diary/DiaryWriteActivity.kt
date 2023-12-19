package com.example.counter.Diary

import android.app.DatePickerDialog
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.DatePicker
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.counter.FirebaseAuthUtils
import com.example.counter.FirebaseRef
import com.example.counter.R
import com.example.counter.databinding.ActivityDiaryWriteBinding
import com.google.firebase.ktx.Firebase
import java.io.ByteArrayOutputStream
import java.util.Calendar
import java.util.GregorianCalendar

class DiaryWriteActivity : AppCompatActivity() {

    lateinit var binding: ActivityDiaryWriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary_write)

        binding.donebutton.setOnClickListener {
            val title = binding.titlearea.text.toString()
            val content = binding.writearea.text.toString()
            val time = binding.datepickerarea.text.toString()

            if(binding.happy.isChecked == true) {
                FirebaseRef.diaryRef
                    .push()
                    .setValue(DiaryModel(title, content, time, "행복"))
            }

            else if(binding.sad.isChecked == true) {
                FirebaseRef.diaryRef
                    .push()
                    .setValue(DiaryModel(title, content, time, "슬픔"))
            }

            else if (binding.angry.isChecked == true) {
                FirebaseRef.diaryRef
                    .push()
                    .setValue(DiaryModel(title, content, time, "화남"))
            }

            else if(binding.tired.isChecked == true) {
                FirebaseRef.diaryRef
                    .push()
                    .setValue(DiaryModel(title, content, time, "지침"))
            }

            else if(binding.surprised.isChecked == true) {
                FirebaseRef.diaryRef
                    .push()
                    .setValue(DiaryModel(title, content, time, "놀람"))
            }

            Toast.makeText(this, "저장 완료", Toast.LENGTH_SHORT).show()

            finish()
        }


        var dateText = ""

        binding.datepickerarea.setOnClickListener {
            val today = GregorianCalendar()
            val year : Int = today.get(Calendar.YEAR)
            val month : Int = today.get(Calendar.MONTH)
            val date : Int = today.get(Calendar.DATE)

            val dlg = DatePickerDialog(this, object : DatePickerDialog.OnDateSetListener {
                override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
                    if(month < 9 && dayOfMonth < 10) {
                        binding.datepickerarea.setText("${year}. 0${month + 1}. 0${dayOfMonth}")


                        dateText = "${year}. 0${month + 1}. 0${dayOfMonth}"
                    } else if(month < 9 && dayOfMonth >= 10) {
                        binding.datepickerarea.setText("${year}. 0${month + 1}. ${dayOfMonth}")


                        dateText = "${year}. 0${month + 1}. ${dayOfMonth}"
                    }
                    else if(month >= 9 && dayOfMonth < 10){
                        binding.datepickerarea.setText("${year}. ${month + 1}. 0${dayOfMonth}")


                        dateText = "${year}. ${month + 1}. 0${dayOfMonth}"
                    } else {
                        binding.datepickerarea.setText("${year}. ${month + 1}. ${dayOfMonth}")


                        dateText = "${year}. ${month + 1}. ${dayOfMonth}"
                    }
                }
            }, year, month, date)
            dlg.show()
        }

        binding.backbutton.setOnClickListener {
            finish()
        }

    }

    override fun onBackPressed() {
        finish()
    }
}