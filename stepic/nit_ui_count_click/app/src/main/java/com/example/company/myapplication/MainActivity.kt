package com.example.company.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write your code here
        var count: Int = 0
        fun tv_click_count(v: View){
            count+=1
            textView.setText("${count}")
        }

        textView.setOnClickListener(::tv_click_count)



    }
}
