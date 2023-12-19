package com.example.counter.Diary

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.DatePicker
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import com.example.counter.FirebaseAuthUtils
import com.example.counter.FirebaseRef
import com.example.counter.R
import com.example.counter.databinding.ActivityDiaryEditBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.*

class DiaryEditActivity : AppCompatActivity() {

    lateinit var binding: ActivityDiaryEditBinding

    private lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary_edit)

        key = intent.getStringExtra("key").toString()

        getDiaryData(key)

        binding.donebutton.setOnClickListener{
            editDiaryData(key)
        }

        binding.backbutton.setOnClickListener {
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
    }

    override fun onBackPressed() {
        finish()
    }

    private fun getDiaryData(key: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {
                val dataModel = datasnapshot.getValue(DiaryModel::class.java)

                try {
                    val dataModel = datasnapshot.getValue(DiaryModel::class.java)

                    binding.titlearea.setText(dataModel?.title)
                    binding.writearea.setText(dataModel?.content)
                    binding.datepickerarea.setText(dataModel?.time)

                    if(dataModel!!.mood == "행복") {
                        binding.happy.isChecked = true
                        binding.sad.isChecked = false
                        binding.angry.isChecked = false
                        binding.tired.isChecked = false
                        binding.surprised.isChecked = false
                    }
                    else if (dataModel!!.mood == "슬픔") {
                        binding.happy.isChecked = false
                        binding.sad.isChecked = true
                        binding.angry.isChecked = false
                        binding.tired.isChecked = false
                        binding.surprised.isChecked = false
                    }

                    else if (dataModel!!.mood == "화남") {
                        binding.happy.isChecked = false
                        binding.sad.isChecked = false
                        binding.angry.isChecked = true
                        binding.tired.isChecked = false
                        binding.surprised.isChecked = false
                    }

                    else if (dataModel!!.mood == "지침") {
                        binding.happy.isChecked = false
                        binding.sad.isChecked = false
                        binding.angry.isChecked = false
                        binding.tired.isChecked = true
                        binding.surprised.isChecked = false
                    }

                    else if (dataModel!!.mood == "놀람") {
                        binding.happy.isChecked = false
                        binding.sad.isChecked = false
                        binding.angry.isChecked = false
                        binding.tired.isChecked = false
                        binding.surprised.isChecked = true
                    }
                } catch (e: Exception) {

                }


            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FirebaseRef.diaryRef.child(key).addValueEventListener(postListener)
    }

    private fun editDiaryData(key: String) {

        if(binding.happy.isChecked == true) {
            FirebaseRef.diaryRef
                .child(key)
                .setValue(DiaryModel(binding.titlearea.text.toString(),
                    binding.writearea.text.toString(),
                    binding.datepickerarea.text.toString(),
                    "행복"))
        }
        else if(binding.sad.isChecked == true) {
            FirebaseRef.diaryRef
                .child(key)
                .setValue(DiaryModel(binding.titlearea.text.toString(),
                    binding.writearea.text.toString(),
                    binding.datepickerarea.text.toString(),
                    "슬픔"))
        }

        else if(binding.angry.isChecked == true) {
            FirebaseRef.diaryRef
                .child(key)
                .setValue(DiaryModel(binding.titlearea.text.toString(),
                    binding.writearea.text.toString(),
                    binding.datepickerarea.text.toString(),
                    "화남"))
        }

        else if(binding.tired.isChecked == true) {
            FirebaseRef.diaryRef
                .child(key)
                .setValue(DiaryModel(binding.titlearea.text.toString(),
                    binding.writearea.text.toString(),
                    binding.datepickerarea.text.toString(),
                    "지침"))
        }

        else if(binding.surprised.isChecked == true) {
            FirebaseRef.diaryRef
                .child(key)
                .setValue(DiaryModel(binding.titlearea.text.toString(),
                    binding.writearea.text.toString(),
                    binding.datepickerarea.text.toString(),
                    "놀람"))
        }

        Toast.makeText(this, "수정 완료", Toast.LENGTH_LONG).show()

        finish()
    }
}