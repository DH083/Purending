package com.example.counter.Diary

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Filter
import android.widget.Filterable
import android.widget.ImageView
import android.widget.TextView
import com.example.counter.R

class DiaryLVAdapter(var diaryList : ArrayList<DiaryModel>): BaseAdapter() {
    override fun getCount(): Int {
        return diaryList.size
    }

    override fun getItem(position: Int): Any {
        return diaryList[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if(view == null) {
            view = LayoutInflater.from(parent?.context).inflate(R.layout.diary_list_item, parent, false)
        }

        val title = view?.findViewById<TextView>(R.id.DiaryTitle)
        title!!.text = diaryList[position].title

        val content = view?.findViewById<TextView>(R.id.DiaryText)
        content!!.text = diaryList[position].content

        val date = view?.findViewById<TextView>(R.id.DiaryDate)
        date!!.text = diaryList[position].time

        val mood = view?.findViewById<TextView>(R.id.DiaryMood)
        mood!!.text = diaryList[position].mood

        val emoji = view?.findViewById<ImageView>(R.id.MoodEmoji)

        if(diaryList[position].mood == "행복")
        {
            emoji!!.setImageResource(R.drawable.happy)
        }
        else if (diaryList[position].mood == "슬픔") {
            emoji!!.setImageResource(R.drawable.sad)
        }
        else if(diaryList[position].mood == "화남") {
            emoji!!.setImageResource(R.drawable.angry)
        }
        else if(diaryList[position].mood == "지침") {
            emoji!!.setImageResource(R.drawable.tired)
        }
        else if(diaryList[position].mood == "놀람") {
            emoji!!.setImageResource(R.drawable.surprised)
        }

        return view!!
    }

    fun searchDataList(searchList: ArrayList<DiaryModel>) {
        diaryList = searchList
        notifyDataSetChanged()
    }
}