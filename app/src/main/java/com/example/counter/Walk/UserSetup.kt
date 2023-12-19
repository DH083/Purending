package com.example.counter.Walk

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import com.example.counter.Model.Week
import com.example.counter.R
import com.example.counter.SelfActivity
import com.example.counter.database.DatabasePreference
import com.example.counter.databinding.AbsLayoutBinding
import com.example.counter.databinding.ActivityUserSetupBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.util.*

class UserSetup : AppCompatActivity() {
    private var databasePreference : DatabasePreference? = null
    private var binding : ActivityUserSetupBinding? = null
    private var absBinding : AbsLayoutBinding? = null

    private var maxSteps: Float? = 0f
    private var calories: Float? = 0f
    private var isRegister : Boolean = false
    private var myWeek : Week? = Week()

    private var isInitAccount : Boolean? = null
    companion object{
        private const val TAG = "UserSetup"
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserSetupBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        //뒤로가기
        val back = findViewById<ImageView>(R.id.back)
        back.setOnClickListener {
            val intent = Intent(this, SelfActivity::class.java)
            finishAffinity()
            startActivity(intent)
        }

        //Setup Activity Custom action bar
//        supportActionBar!!.setDisplayHomeAsUpEnabled(true)

//        supportActionBar!!.displayOptions = ActionBar.DISPLAY_SHOW_CUSTOM

        absBinding = AbsLayoutBinding.inflate(layoutInflater)
//        supportActionBar!!.customView = absBinding!!.root
        absBinding!!.activityTitleTv.text =  baseContext.resources.getString(R.string.user_setup_activity_title)

        // Because delay when connect to Firestore so need to prevent user moving to another screen
        bottomNavigationVisible(false)

        //Bottom navigation
        bottomNavigationHandle()

        //Setup database

        databasePreference = DatabasePreference(baseContext)
        Log.v(TAG,"Get key success: ${databasePreference!!.deviceId}")

        isRegister = intent.getBooleanExtra("isRegister",false)

        isTargetFill()
        eventHandle()
    }
    private fun eventHandle(){
        binding!!.goBt.setOnClickListener{
            if (!binding!!.totalMaxStepEv.text.isNullOrBlank()){
                myWeek!!.stepPerDay = maxSteps!!.toInt()

                val countStepIntent = Intent(this,CountStep::class.java)
                countStepIntent.putExtra("myWeek",myWeek)
                startActivity(countStepIntent)
            }
            else{
                Toast.makeText(this,"목표 걸음 수를 입력해 주세요!",Toast.LENGTH_SHORT).show()
            }
        }

        binding!!.totalMaxStepEv.setOnClickListener {
            if (!binding!!.totalMaxStepEv.text.isNullOrBlank()) {
                maxSteps = binding!!.totalMaxStepEv.text.toString().toFloat()
            }
        }
    }

    override fun onPause() {
        super.onPause()
        // Save
        myWeek!!.stepPerDay = maxSteps!!.toInt()
        saveData()

//        Toast.makeText(this, "Pause!!!", Toast.LENGTH_SHORT).show()
//
        myWeek!!.stepPerDay = maxSteps!!.toInt()
        Log.v(TAG, "Activity on pause, data updating!!!")
    }
    private fun isTargetFill(){
        if (!loadWeekData()){
            Log.v(TAG,"User data is init")
            bottomNavigationVisible(false)
            isInitAccount = true
        }
        else{
            Log.v(TAG,"User data is exist")
            if (myWeek!!.stepPerDay == 0){
                bottomNavigationVisible(false)
            }
            else {
                maxSteps = myWeek!!.stepPerDay!!.toFloat()
                calories = (maxSteps!!.toFloat() * FOOT_TO_CALORIE).toFloat()
                binding!!.totalMaxStepEv.setText(maxSteps!!.toInt().toString())
                bottomNavigationVisible(true)
            }
            if(isRegister) {
                val countStepIntent = Intent(this, CountStep::class.java)
                countStepIntent.putExtra("myWeek", myWeek)
                startActivity(countStepIntent)
            }
            isInitAccount = false
        }
        checkNewDay()
    }

    private fun bottomNavigationVisible(flag : Boolean){
        binding!!.bottomNavigation.menu[0].isVisible = flag
        binding!!.bottomNavigation.menu[1].isVisible = flag
    }
    private fun bottomNavigationHandle(){
        val bottomNavigationView : BottomNavigationView = binding!!.bottomNavigation

        binding!!.bottomNavigation.menu[1].isChecked = true
        bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.home-> {
                    startActivity(Intent(this, CountStep::class.java))
//                    finishAffinity()
                }
            }
            true
        }
    }
    private fun loadWeekData() : Boolean {
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        myWeek = databasePreference!!.initData(0)

        val deviceId = sharedPreferences.getString("deviceId","")
        if (deviceId == ""){
            return false
        }
        myWeek!!.deviceId = deviceId
        myWeek!!.stepPerDay = sharedPreferences.getInt("stepPerDay",0)
        myWeek!!.mon = sharedPreferences.getInt("monStep",0)
        myWeek!!.tue = sharedPreferences.getInt("tueStep",0)
        myWeek!!.wed = sharedPreferences.getInt("wedStep",0)
        myWeek!!.thu = sharedPreferences.getInt("thuStep",0)
        myWeek!!.fri = sharedPreferences.getInt("friStep",0)
        myWeek!!.sat = sharedPreferences.getInt("satStep",0)
        myWeek!!.sun = sharedPreferences.getInt("sunStep",0)
        return true
    }
    private fun saveData() {
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        // Save day
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        editor.putInt("today", today)
        Log.v(TAG, "Today save is: $today")

        Log.v(TAG,"Week save is : $myWeek")
        editor.putString("deviceId", myWeek!!.deviceId)
        editor.putInt("stepPerDay",myWeek!!.stepPerDay!!)
        editor.putInt("monStep",myWeek!!.mon!!)
        editor.putInt("tueStep",myWeek!!.tue!!)
        editor.putInt("wedStep",myWeek!!.wed!!)
        editor.putInt("thuStep",myWeek!!.thu!!)
        editor.putInt("friStep",myWeek!!.fri!!)
        editor.putInt("satStep",myWeek!!.sat!!)
        editor.putInt("sunStep",myWeek!!.sun!!)

        editor.apply()
    }
    private fun resetData() {
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putFloat("previousTotalSteps",0f)
        editor.apply()

    }
    private fun checkNewDay() {
        val sharedPreferences = getSharedPreferences("myPrefs", Context.MODE_PRIVATE)
        val oldDay = sharedPreferences.getInt("today",0)
        Log.v(TAG, "Old day: $oldDay")
        val today = Calendar.getInstance().get(Calendar.DAY_OF_WEEK)
        if (oldDay == today && !isInitAccount!!) {
            Log.v(TAG, "Still in today: $today")
        } else {
            // Reset data
            Log.v(TAG, "Change to new day is: $today")
            resetData()
            myWeek = databasePreference!!.updateSpecifyDay(myWeek!!, today,0)
        }
    }
    override fun onBackPressed() {
        val intent = Intent(this, SelfActivity::class.java)
        finishAffinity()
        startActivity(intent)
    }
}