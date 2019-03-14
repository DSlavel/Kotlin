package com.example.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*


fun button2_click_hadler(v: View){
    Log.d("TAG", "Button2 was pressed")
}

class MainActivity : AppCompatActivity() {

    fun button3_click_handler(v: View){
        Log.d("TAG", "button3 was pressed")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button1.setOnClickListener{
            Log.d("TAG", "Button1 was pressed")
        }

        button2.setOnClickListener(::button2_click_hadler)

        button3.setOnClickListener(this::button3_click_handler)
    }
}
