package com.example.applicationpractice.logic.network

import com.example.applicationpractice.StockApplication
import com.example.applicationpractice.logic.modle.StockResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface StockService {
    @GET("652ea346064d40ba4d")
    fun searchStocks(): Call<List<StockResponse>>
}