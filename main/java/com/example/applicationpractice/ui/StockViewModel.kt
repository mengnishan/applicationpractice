package com.example.applicationpractice.ui

import Repository
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.example.applicationpractice.logic.modle.Stock

class StockViewModel() : ViewModel() {

    private val searchLiveData = MutableLiveData<String>()

    fun getLiveDate() = searchLiveData.value

    val stockList = ArrayList<Stock>()
    val stockConcernList = ArrayList<Stock>()

    //检测数据的变动，实现动态搜索
    val stockLiveData = Transformations.switchMap(searchLiveData){ query ->
        Repository.searchStock(query)
    }

    val stockConcernLiveData = Transformations.switchMap(searchLiveData){ query ->
        Repository.searchConcernStock(query)
    }

    fun searchStocks(query:String){
        searchLiveData.value = query
    }

}