package com.example.counter.Diary

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import com.example.counter.FirebaseRef
import com.example.counter.MainActivity
import com.example.counter.R
import com.example.counter.databinding.ActivityDiaryListViewBinding
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import java.util.Locale

class DiaryListViewActivity : AppCompatActivity() {

    lateinit var binding: ActivityDiaryListViewBinding

    private lateinit var key: String

    private val TAG = ActivityDiaryListViewBinding::class.java.simpleName

    private val diaryDataList = ArrayList<DiaryModel>()

    private lateinit var diaryRVAdapter : DiaryLVAdapter

    private val diaryKeyList = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_diary_list_view)

        // 일기 쓰기 페이지로 넘어감
        binding.writebutton.setOnClickListener {
            val intent = Intent(this, DiaryWriteActivity::class.java)
            startActivity(intent)
        }


        //뒤로가기
        binding.backbutton.setOnClickListener {
           finish()
        }

        diaryRVAdapter = DiaryLVAdapter(diaryDataList)
        binding.diaryListView.adapter = diaryRVAdapter

        //각 다이어리 내용 읽기 페이지로 넘어감
        binding.diaryListView.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, DiaryWatchActivity::class.java)
            intent.putExtra("key", diaryKeyList[position])
            startActivity(intent)
        }

        // 다이어리 리스트 불러오기기
        FirebaseRef.diaryRef.orderByChild("time").addValueEventListener(object: ValueEventListener {
            override fun onDataChange(datasnapshot: DataSnapshot) {

                diaryDataList.clear()
                diaryKeyList.clear()

                for (dataModel in datasnapshot.children) {

                    Log.d(TAG, dataModel.toString())
                    //dataModel.key

                    val item = dataModel.getValue(DiaryModel::class.java)
                    diaryDataList.add(item!!)
                    diaryKeyList.add(dataModel.key.toString())

                }

                diaryRVAdapter.notifyDataSetChanged()

                Log.d(TAG, diaryDataList.toString())
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })

        binding.search.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String): Boolean {
                searchList(newText)
                return true
            }
        })

    }

    override fun onBackPressed() {
        finish()
    }

    fun searchList(text: String) {
        val searchList = ArrayList<DiaryModel>()
        for(dataClass in diaryDataList) {
            if(dataClass.time.contains(text.lowercase(Locale.getDefault())) ||
                dataClass.title.contains(text.lowercase(Locale.getDefault())) ||
                dataClass.mood.contains(text.lowercase(Locale.getDefault())) ||
                dataClass.content.contains(text.lowercase(Locale.getDefault()))) {
                searchList.add(dataClass)
            }
        }
        diaryRVAdapter.searchDataList(searchList)
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