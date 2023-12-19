package com.example.counter.Diary

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.example.counter.FirebaseRef
import com.example.counter.R
import com.example.counter.databinding.ActivityDiaryWatchBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener

class DiaryWatchActivity : AppCompatActivity() {

    lateinit var binding: ActivityDiaryWatchBinding

    private lateinit var key: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary_watch)

        binding.backbutton.setOnClickListener {
            finish()
        }

        binding.menubutton.setOnClickListener {
            showDialog()
        }

            key = intent.getStringExtra("key").toString()
            intent.putExtra("key", key)
            getDiaryData(key)
    }

    override fun onBackPressed() {
        finish()
    }

    private fun getDiaryData(key: String){
        val postListener = object : ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                try {

                    val dataModel = datasnapshot.getValue(DiaryModel::class.java)

                    binding.titlearea.text = dataModel!!.title
                    binding.writearea.text = dataModel!!.content
                    binding.datepickerarea.text = dataModel!!.time

                    if(dataModel!!.mood == "행복") {
                        binding.happy.isChecked = true
                        binding.sad.isChecked = false
                        binding.angry.isChecked = false
                        binding.tired.isChecked = false
                        binding.surprised.isChecked = false
                        binding.radiogroup.isClickable = false
                    }
                    else if (dataModel!!.mood == "슬픔") {
                        binding.happy.isChecked = false
                        binding.sad.isChecked = true
                        binding.angry.isChecked = false
                        binding.tired.isChecked = false
                        binding.surprised.isChecked = false
                        binding.radiogroup.isClickable = false
                    }

                    else if (dataModel!!.mood == "화남") {
                        binding.happy.isChecked = false
                        binding.sad.isChecked = false
                        binding.angry.isChecked = true
                        binding.tired.isChecked = false
                        binding.surprised.isChecked = false
                        binding.radiogroup.isClickable = false
                    }

                    else if (dataModel!!.mood == "지침") {
                        binding.happy.isChecked = false
                        binding.sad.isChecked = false
                        binding.angry.isChecked = false
                        binding.tired.isChecked = true
                        binding.surprised.isChecked = false
                        binding.radiogroup.isClickable = false
                    }

                    else if (dataModel!!.mood == "놀람") {
                        binding.happy.isChecked = false
                        binding.sad.isChecked = false
                        binding.angry.isChecked = false
                        binding.tired.isChecked = false
                        binding.surprised.isChecked = true
                        binding.radiogroup.isClickable = false
                    }


                } catch (e : Exception) {

                    Log.d(ContentValues.TAG, "삭제 완료")

                }
            }

            override fun onCancelled(error: DatabaseError) {
            }
        }
        FirebaseRef.diaryRef.child(key).addValueEventListener(postListener)
    }

    private fun showDialog() {
        val mDialogView = LayoutInflater.from(this).inflate(R.layout.dialog_diaryin, null)
        val mBuilder = AlertDialog.Builder(this)
            .setView(mDialogView)

        val alterDialog = mBuilder.show()

        alterDialog.findViewById<Button>(R.id.editbutton)?.setOnClickListener {
            val intent = Intent(this, DiaryEditActivity::class.java)
            intent.putExtra("key", key)
            startActivity(intent)
            alterDialog.dismiss()
        }

        alterDialog.findViewById<Button>(R.id.deletebutton)?.setOnClickListener {
            FirebaseRef.diaryRef.child(key).removeValue()
            Toast.makeText(this, "삭제 완료", Toast.LENGTH_LONG).show()
            finish()
            alterDialog.dismiss()
        }
    }
}