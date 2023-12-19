package com.example.counter

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.counter.AppLock.AppLock
import com.example.counter.AppLock.AppLockConst
import com.example.counter.AppLock.AppPassWordActivity

class ChkActivity : AppCompatActivity() {

    var lock = true // 잠금 상태 여부 확인

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chk)
    }

    // 액티비티가 onStart인 경우
    override fun onStart() {
        super.onStart()
        if(lock && AppLock(this).isPassLockSet()){
            val intent = Intent(this, AppPassWordActivity::class.java).apply {
                putExtra(AppLockConst.type, AppLockConst.UNLOCK_PASSWORD)
            }
            startActivityForResult(intent, AppLockConst.UNLOCK_PASSWORD)
        }
        else {
            val intent = Intent(this, SplashActivity::class.java)
            startActivity(intent)
        }
    }

    // 액티비티가 onPause인경우
    override fun onPause() {
        super.onPause()
        if (AppLock(this).isPassLockSet()) {
            lock = true // 잠금로 변경

        }
    }

    // startActivityForResult 결과값을 받는다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            AppLockConst.UNLOCK_PASSWORD ->
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "잠금 해제 됨", Toast.LENGTH_SHORT).show()
                    lock = false
                    val intent = Intent(this, SplashActivity::class.java)
                    startActivityForResult(intent, AppLockConst.UNLOCK_PASSWORD)
                }
        }

    }
}