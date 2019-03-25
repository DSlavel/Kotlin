package com.example.myapplication

import android.app.Application
import io.realm.Realm

class App:Application() {

    override fun onCreate() {
        super.onCreate()
        Realm.init(this)
        getSharedPreferences("name", 0).edit().putString("zzz","xx").apply()
        getSharedPreferences("name", 0).getString("zzz","")
    }
}