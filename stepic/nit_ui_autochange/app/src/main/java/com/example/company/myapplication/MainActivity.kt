package com.example.company.myapplication

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Write your code here
        var counter : Int = 0
        var temp : String = ""

        editText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
           //     if (editText.text.toString() == "question") {
           //         editText.setText("answer")
            //        editText.setSelection(editText.text.length)
            //        onTextChanged(s, start=editText.text.length, before = editText.text.length, count = 1 )
               // }
                Log.d("TAG", " after s= ${s}")

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                Log.d("TAG", "befor s= ${s}")
                //if (s ==  "question" ) {
               //     onTextChanged(s, s.length, s.length,1)

              //  }
               // editText.setText(editText.text.toString().replace("A", "C"))

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
               // counter += 1
               // textView.setText(counter.toString())if (editText.text.toString() == "question") editText.setText("answer")
                Log.d("TAG", " on s= ${s}")
                if (editText.text.toString() == "question") {
                    editText.setText(temp + "answer")
                    temp = temp + "answer"
                    editText.setSelection(editText.text.length)
                    Log.d("TAG", " on temp= ${temp}")
                }



                }


        })

    }
}


