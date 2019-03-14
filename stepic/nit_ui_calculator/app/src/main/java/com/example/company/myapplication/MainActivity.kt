package com.example.company.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.NumberFormatException

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write your code here
        fun button_click_add(v: View){
           try {
               answer.setText("${arg1.text.toString().toInt() + arg2.text.toString().toInt()}")
           }
           catch (e: NumberFormatException){
               answer.setText("Input Error")
           }
        }

        fun button_click_sub(v: View){
            try {
                answer.setText("${arg1.text.toString().toInt() - arg2.text.toString().toInt()}")
            }
            catch (e: NumberFormatException){
                answer.setText("Input Error")
            }
        }

        fun button_click_mul(v: View){
            try {
                answer.setText("${arg1.text.toString().toInt() * arg2.text.toString().toInt()}")
            }
            catch (e: NumberFormatException){
                answer.setText("Input Error")
            }
        }

        fun button_click_div(v: View){
            try {
                if (arg2.text.toString().toInt() == 0) answer.setText("Div by zero")
                else answer.setText("${arg1.text.toString().toInt() / arg2.text.toString().toInt()}")
            }
            catch (e: NumberFormatException){
                answer.setText("Input Error")
            }
        }

        add.setOnClickListener(::button_click_add)
        sub.setOnClickListener(::button_click_sub)
        mul.setOnClickListener(::button_click_mul)
        div.setOnClickListener(::button_click_div)

    }
}
