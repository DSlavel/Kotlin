package com.example.myapplication
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.RecyclerView
import android.text.Html
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.realm.RealmList
import io.realm.RealmObject
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)

        if (savedInstanceState == null) {
            val bundle = Bundle()
            bundle.putString("param", "value")
            val f = MainFragment()
            f.arguments = bundle
            supportFragmentManager.beginTransaction().replace(R.id.fragment_place, f).commitAllowingStateLoss()
        }

        Log.e("tag", "был запущен onCreate")
    }

    fun showArticle(url: String) {

        val bundle = Bundle()
        bundle.putString("url", url)
        val f = SecondFragment()
        f.arguments = bundle

        val frame2 = findViewById<View>(R.id.frag2_browser)
        if (frame2 != null) {
            frame2.visibility = View.VISIBLE
            supportFragmentManager.beginTransaction().replace(R.id.frag2_browser, f).commitAllowingStateLoss()
        } else
            supportFragmentManager.beginTransaction().add(R.id.fragment_place, f).addToBackStack("main")
                .commitAllowingStateLoss()
    }

}

class FeedAPI(
    val items: ArrayList<FeedItemAPI>
)

class FeedItemAPI(
    val title: String,
    val link: String,
    val thumbnail: String,
    val description: String,
    val guid: String
)

open class Feed(
    var items: RealmList<FeedItem> = RealmList<FeedItem>()
) : RealmObject()

open class FeedItem(
    var title: String = "",
    var link: String = "",
    var thumbnail: String = "",
    var description: String = "",
    var guid: String = ""
) : RealmObject()

class RecAdapter(val items: RealmList<FeedItem>) : RecyclerView.Adapter<RecHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecHolder {
        val inflater = LayoutInflater.from(parent.context)

        val view = inflater.inflate(R.layout.listitem, parent, false)

        return RecHolder(view)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onBindViewHolder(holder: RecHolder, position: Int) {
        val item = items[position]!!

        holder.bind(item)
    }

    override fun getItemViewType(position: Int): Int {
        return super.getItemViewType(position)
    }

}

class RecHolder(view: View) : RecyclerView.ViewHolder(view) {

    fun bind(item: FeedItem) {
        val vTitle = itemView.findViewById<TextView>(R.id.item_title)
        val vDesc = itemView.findViewById<TextView>(R.id.item_desc)
        val vThumb = itemView.findViewById<ImageView>(R.id.item_thumb)
        vTitle.text = item.title
        vDesc.text = Html.fromHtml(item.description)

        Picasso.with(vThumb.context).load(item.thumbnail).into(vThumb)

        itemView.setOnClickListener {
            (vThumb.context as MainActivity).showArticle(item.link)
        }
    }

}
/*
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DialogTitle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.LinearLayout
import android.widget.ListView
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
    lateinit var vRecView: RecyclerView
    lateinit var vListView: ListView
    var rquest:Disposable?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
       // vList=findViewById<LinearLayout>(R.id.act1_list)
        vList=findViewById<LinearLayout>(R.id.act1_reclView)
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
         //       showLinerLayout(it.items)
        showRecView(it.items)
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

    fun showListView(feedList:ArrayList<FeedItem>){

        vListView.adapter=Adapter(feedList)
    }

    fun showRecView(feedList:ArrayList<FeedItem>){
        vRecView.adapter=Adapter.RecAdapter(feedList)
        vRecView.layoutManager=LinearLayoutManager(this)
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

class Adapter(val items:ArrayList<FeedItem>):BaseAdapter(){
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

        val infLater=LayoutInflater.from(parent!!.context)
        val view = convertView ?: infLater.inflate(R.layout.listitem, parent,false)
        val vTitle=view.findViewById<TextView>(R.id.item_title)
        val item=getItem(position) as FeedItem
        vTitle.text=item.title
        return view
    }

    class RecAdapter(val items: ArrayList<FeedItem>):RecyclerView.Adapter<RecHolder>(){
        override fun onCreateViewHolder(parent: ViewGroup, p1: Int): RecHolder {
            val infLater=LayoutInflater.from(parent!!.context)
            val view =infLater.inflate(R.layout.listitem, parent,false)
           return RecHolder(view)
        }

        override fun getItemCount(): Int {
            return items.size
        }

        override fun onBindViewHolder(holder: RecHolder, position: Int) {
           val   item = items[position]
            holder?.bind(item)
        }

    }

    class RecHolder(view: View):RecyclerView.ViewHolder(view){

        fun bind(item: FeedItem){
            val vTitle=itemView.findViewById<TextView>(R.id.item_title)
            vTitle.text=item.title
        }

    }

    override fun getItem(position: Int): Any {
        return items[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return items.size
    }

}
*/