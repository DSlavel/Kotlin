package com.example.company.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*




class MainActivity : AppCompatActivity() {
    fun button_click_hadler(v: View){
        button.setText("Button was pressed")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write your code here
        button.setOnClickListener(this::button_click_hadler)

    }
}
