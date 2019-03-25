package com.example.myapplication

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DialogTitle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import com.google.gson.Gson
import java.util.*
import io.reactivex.Observable
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.zipWith
import io.reactivex.schedulers.Schedulers
import java.lang.RuntimeException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {

    lateinit var vText:TextView
    lateinit var vList: LinearLayout
    var rquest:Disposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        vList=findViewById<LinearLayout>(R.id.act1_list)
       // vText=findViewById<TextView>(R.id.act1_text)
   //     vText.setTextColor(0xFFFF0000.toInt())
  //      vText.setOnClickListener{
    //        Log.e("tag","нажата кнопка")
           // val i=Intent(this,SecondActivity::class.java)
           // i.putExtra("tag1",vText.text)
            //startActivityForResult(i,0)

            val o= createRequest("https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Ffeeds.bbci.co.uk%2Fnews%2Frss.xml")
                .map { Gson().fromJson(it,Feed::class.java) }
                .subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())

            rquest = o.subscribe({
                showLinerLayout(it.items)

              //  for(item in it.items)
            //        Log.w("test", "title: ${item.title}")
            },{
        Log.e("test", "",it)
            })
      //  }
     //   Log.v("tag","Был запущен onCreate")

    }
fun showLinerLayout(feedList:ArrayList<FeedItem>){

    val inflater = layoutInflater
    for( f in feedList) {
        val view = inflater.inflate(R.layout.listitem, vList,false)
        val vTitle=view.findViewById<TextView>(R.id.item_title)
        vTitle.text=f.title
        vList.addView(view)

    }

}

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(data!=null){
            val str = data.getStringExtra("tag2")
            vText.text=str

        }
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onResume() {
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        rquest?.dispose()
        super.onDestroy()
    }
}

class Feed(
    val items:ArrayList<FeedItem>
)

class FeedItem(
    val title: String
  //  val link: String,
 //   val thumbnail: String,
 //   val description: String
)


/*https://api.rss2json.com/v1/api.json?rss_url=http%3A%2F%2Ffeeds.bbci.co.uk%2Fnews%2Frss.xml﻿


        */