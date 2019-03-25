package com.example.myapplication

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class SecondActivity:Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.second_activity)

        findViewById<Button>(R.id.act2_button).setOnClickListener{
            val vEdit=findViewById<EditText>(R.id.act2_edit)

            val newStr = vEdit.text.toString()
            val i=Intent()
            i.putExtra("tag2",newStr)
            setResult(5,i)
            finish()
        }

        val str = intent.getStringExtra("tag1")
        val vEdit=findViewById<EditText>(R.id.act2_edit)
        vEdit.setText(str)
    }
}