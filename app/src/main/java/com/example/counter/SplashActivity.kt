package com.example.counter

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import com.example.counter.AppLock.AppLock
import com.example.counter.AppLock.AppLockConst
import com.example.counter.AppLock.AppPassWordActivity

class SplashActivity : AppCompatActivity() {

    private val TAG = "SplashActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val uid = FirebaseAuthUtils.getUid()

        //로그인 상태 확인
        if (uid == "null") {
            Handler().postDelayed({
                val intent = Intent(this, TitleActivity::class.java)
                startActivity(intent)
                finish()
            }, 1000)
        } else {
            Handler().postDelayed({
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }, 1000)
        }
    }
}
