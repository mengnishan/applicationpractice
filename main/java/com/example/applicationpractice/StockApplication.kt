package com.example.applicationpractice

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class StockApplication :Application(){
    //全局获得Context
    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}