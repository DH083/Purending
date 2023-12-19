package com.example.counter.yoga

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.example.counter.R

class YogaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_yogabutton)

        //뒤로가기
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            finish()
        }

        val intent = Intent(this, YogamornActivity::class.java)
        val morn = findViewById<Button>(R.id.morning)
        morn.setOnClickListener{startActivity(intent)}

        val intent2 = Intent(this, YogaevenActivity::class.java)
        val even = findViewById<Button>(R.id.evening)
        even.setOnClickListener{startActivity(intent2)}

        val intent3 = Intent(this, YogabefActivity::class.java)
        val before = findViewById<Button>(R.id.before)
        before.setOnClickListener{startActivity(intent3)}

        val intent4 = Intent(this, YogaafterActivity::class.java)
        val aft = findViewById<Button>(R.id.after)
        aft.setOnClickListener{startActivity(intent4)}
    }
}