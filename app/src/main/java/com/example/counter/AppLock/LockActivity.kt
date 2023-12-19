package com.example.counter.AppLock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.counter.MainActivity
import com.example.counter.R
import com.example.counter.SplashActivity

class LockActivity : AppCompatActivity() {
    var lock = true // 잠금 상태 여부 확인

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lock)

        init()

        val btnSetLock = findViewById<Button>(R.id.btnSetLock)
        val btnSetDelLock = findViewById<Button>(R.id.btnSetDelLock)
        val btnChangePwd = findViewById<Button>(R.id.btnChangePwd)

        //뒤로가기
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            finish()
        }

        // 잠금 설정 버튼을 눌렀을때
        btnSetLock.setOnClickListener {
            val intent = Intent(this, AppPassWordActivity::class.java).apply {
                putExtra(AppLockConst.type, AppLockConst.ENABLE_PASSLOCK)
            }
            startActivityForResult(intent, AppLockConst.ENABLE_PASSLOCK)
        }

        // 잠금 비활성화 버튼을 눌렀을때
        btnSetDelLock.setOnClickListener{
            val intent = Intent(this, AppPassWordActivity::class.java).apply {
                putExtra(AppLockConst.type, AppLockConst.DISABLE_PASSLOCK)
            }
            startActivityForResult(intent, AppLockConst.DISABLE_PASSLOCK)
        }

        // 암호 변경버튼을 눌렀을때
        btnChangePwd.setOnClickListener {
            val intent = Intent(this, AppPassWordActivity::class.java).apply {
                putExtra(AppLockConst.type, AppLockConst.CHANGE_PASSWORD)
            }
            startActivityForResult(intent, AppLockConst.CHANGE_PASSWORD)
        }
    }

    // startActivityForResult 결과값을 받는다.
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        when(requestCode) {
            AppLockConst.ENABLE_PASSLOCK ->
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "암호 설정 됨", Toast.LENGTH_SHORT).show()
                    init()
                    lock = false
                }

            AppLockConst.DISABLE_PASSLOCK ->
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "암호 삭제 됨", Toast.LENGTH_SHORT).show()
                    init()
                }

            AppLockConst.CHANGE_PASSWORD ->
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "암호 변경 됨", Toast.LENGTH_SHORT).show()
                    lock = false
                }

            AppLockConst.UNLOCK_PASSWORD ->
                if (resultCode == Activity.RESULT_OK) {
                    Toast.makeText(this, "잠금 해제 됨", Toast.LENGTH_SHORT).show()
                    lock = false
                    val intent = Intent(this, SplashActivity::class.java)
                    startActivityForResult(intent, AppLockConst.UNLOCK_PASSWORD)
                }
        }

    }

//    // 액티비티가 onStart인 경우
//    override fun onStart() {
//        super.onStart()
//        if(lock && AppLock(this).isPassLockSet()){
//            val intent = Intent(this, AppPassWordActivity::class.java).apply {
//                putExtra(AppLockConst.type, AppLockConst.UNLOCK_PASSWORD)
//            }
//            startActivityForResult(intent, AppLockConst.UNLOCK_PASSWORD)
//        }
//    }

    // 액티비티가 onPause인경우
    override fun onPause() {
        super.onPause()
        if (AppLock(this).isPassLockSet()) {
            lock = true // 잠금로 변경
        }
    }

    // 버튼 비활성화
    private fun init(){

        val btnSetLock = findViewById<Button>(R.id.btnSetLock)
        val btnSetDelLock = findViewById<Button>(R.id.btnSetDelLock)
        val btnChangePwd = findViewById<Button>(R.id.btnChangePwd)

        if (AppLock(this).isPassLockSet()){
            btnSetLock.isEnabled = false
            btnSetDelLock.isEnabled = true
            btnChangePwd.isEnabled = true
            lock = true
        }
        else{
            btnSetLock.isEnabled = true
            btnSetDelLock.isEnabled = false
            btnChangePwd.isEnabled = false
            lock = false
        }
    }
}
