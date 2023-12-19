package com.example.counter

import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import android.os.AsyncTask
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.example.counter.AppLock.LockActivity
import com.example.counter.Diary.DiaryWriteActivity
import com.example.counter.Weather.WeatherActivity
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import org.json.JSONObject
import java.net.URL
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), View.OnTouchListener {

    val CITY: String = "yangju,kr"
    val API: String = "06c921750b9a82d8f5d1294e1586276f" // Use API key

    private lateinit var binding: MainActivity

    private lateinit var speechBubbleView: TextView
    private lateinit var mainLayout: LinearLayout

    //말풍선 내용
    private val wordList = mutableListOf("안녕하세요!", "오늘 하루를\n다이어리에 정리해봐요!", "오늘 기분이\n어떤가요?", "저와 함께\n산책해요!", "안녕하세요!", "제 이름은\n푸렌딩이에요!", "오늘 기분이\n어떤가요?", "날씨가 좋으면\n함께 산책해요!", "저와 함께\n건강한 습관을\n만들어 봐요~!", "스트레칭으로\n개운한 하루를 보내요", "쉼호흡을 하면서\n마음을 진정시켜봐요")

    private var currentWordIndex = 0
    private val random = Random()
    private var speechBubbleLocation = IntArray(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        speechBubbleView = findViewById<TextView>(R.id.speech_bubble)
        mainLayout = findViewById<LinearLayout>(R.id.main_layout)
        mainLayout.setOnTouchListener(this)

        //일기쓰기
        val writebutton = findViewById<Button>(R.id.writebutton)
        writebutton.setOnClickListener {
            val intent = Intent(this, DiaryWriteActivity::class.java)
            startActivity(intent)
        }

        //날씨 이동
        val weather = findViewById<LinearLayout>(R.id.weather)
        weather.setOnClickListener {
            val intent = Intent(this, WeatherActivity::class.java)
            startActivity(intent)
        }

        //설정 띄우기
        val setting = findViewById<ImageButton>(R.id.setting)
        setting.setOnClickListener {

            // Dialog만들기
            val mDialogView = LayoutInflater.from(this).inflate(R.layout.setting_dialog, null)
            val mBuilder = AlertDialog.Builder(this)
                .setView(mDialogView)
                .setNegativeButton("닫기",
                    DialogInterface.OnClickListener { dialog, id ->
                        // Cancel 버튼 선택 시 수행
                    })

            mBuilder.show()

            //앱 잠금 설정
            val lock = mDialogView.findViewById<Button>(R.id.lock)
            lock.setOnClickListener {
                val intent = Intent(this, LockActivity::class.java)
                startActivity(intent)
            }

            //문의하기 기능
            val sugges = mDialogView.findViewById<Button>(R.id.suggestion)
            sugges.setOnClickListener {

                val uid = FirebaseAuthUtils.getUid()

                var i = Intent(Intent.ACTION_SEND)
                i.data = Uri.parse("Mail to: ")
                i.type = "text/plain"

                i.putExtra(Intent.EXTRA_EMAIL, arrayOf("ehgml1174@gmail.com"))
                i.putExtra(Intent.EXTRA_SUBJECT, "[푸렌딩 문의하기] ")
                i.putExtra(Intent.EXTRA_TEXT, "[문의내용] : "
                        + "\n"
                        + "\n" + "\n" + "\n" + "\n"
                        + "\n" + "* 문의 관련 스크린샷 또는 녹화본을 함께 보내주시면 빠른 문제 확인 및 해결이 가능합니다."
                        + "\n"
                        + "\n" +"---원활한 처리를 위해 아래 정보는 지우지 말아주세요.---"
                        + "\n"
                        + "\n" + "User: " + uid
                        + "\n" + "System: " + Build.VERSION.RELEASE
                        + "\n" + "Model: " + Build.MODEL
                        + "\n"
                        + "\n"
                        + "\n" + "해당 정보는 문제해결 및 답변을 위한 목적으로만 사용됩니다. 일부 정보가 확인되지 않는 경우 문제 처리 및 회신이 불가할 수 있습니다.")

                startActivity(Intent.createChooser(i, "사용할 메일을 선택해주세요"))
            }

            //로그아웃 기능
            val logout = mDialogView.findViewById<Button>(R.id.log_out)
            logout.setOnClickListener {
                Firebase.auth.signOut()

                val intent = Intent(this, TitleActivity::class.java)

                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
            }
        }

        val self = findViewById<ImageButton>(R.id.self)
        self.setOnClickListener {
            val intent = Intent(this, SelfActivity::class.java)
            startActivity(intent)
        }

        weatherTask().execute()

//        val reload = findViewById<LinearLayout>(R.id.reload)
//        reload.setOnClickListener {
//            weatherTask().execute()
//        }
    }

    //터치시 말풍선 변경
    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                speechBubbleView.text = wordList[currentWordIndex]
                currentWordIndex = (currentWordIndex + 1) % wordList.size
            }
        }
        return true
    }

    inner class weatherTask() : AsyncTask<String, Void, String>() {
        override fun onPreExecute() {
            super.onPreExecute()
        }

        override fun doInBackground(vararg params: String?): String? {
            var response:String?
            try{
                response = URL("https://api.openweathermap.org/data/2.5/weather?q=$CITY&units=metric&appid=$API").readText(
                    Charsets.UTF_8
                )
            }catch (e: Exception){
                response = null
            }
            return response
        }

        override fun onPostExecute(result: String?) {
            super.onPostExecute(result)
            try {
                /* Extracting JSON returns from the API */
                val jsonObj = JSONObject(result)
                val main = jsonObj.getJSONObject("main")
                val weather = jsonObj.getJSONArray("weather").getJSONObject(0)

                val updatedAt:Long = jsonObj.getLong("dt")
                val updatedAtText = SimpleDateFormat("yyyy년 MM월 dd일 hh:mm a", Locale.KOREA).format(Date(updatedAt*1000))
                val temp = main.getString("temp")+"°C"
                val weatherDescription = weather.getString("description")

                findViewById<TextView>(R.id.status).text = weatherDescription.capitalize()
                findViewById<TextView>(R.id.temp).text = temp


            } catch (e: Exception) {

            }

        }
    }

    // 뒤로가기 2번
    private var backPressedTime : Long = 0
    override fun onBackPressed() {
        Log.d("TAG", "뒤로가기")

        // 2초내 다시 클릭하면 앱 종료
        if (System.currentTimeMillis() - backPressedTime < 2000) {
            finishAffinity()
            finish()
            return
        }

        // 처음 클릭 메시지
        Toast.makeText(this, "종료하시려면 버튼을 한번 더 눌러주세요.", Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }

}