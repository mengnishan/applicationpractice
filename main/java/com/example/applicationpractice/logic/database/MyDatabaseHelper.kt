package com.example.retrofit.logic.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.applicationpractice.StockApplication
import com.example.applicationpractice.logic.modle.Stock
import com.example.applicationpractice.logic.modle.StockResponse


class MyDatabaseHelper(context: Context) :
    SQLiteOpenHelper(context, "Stock.db", null, 3) {

    val createStock = "create table Stock (" +
            "id integer primary key autoincrement," +
            "zqdm text," +
            "zqjc text," +
            "sgdm text," +
            "fxsl text," +
            "swfxsl text," +
            "sgsx text," +
            "dgsz text," +
            "sgrq text," +
            "fxjg text," +
            "zxj text," +
            "srspj text," +
            "zqgbrq text," +
            "zqjkrq text," +
            "ssrq text," +
            "syl text," +
            "hysyl text," +
            "wszql text," +
            "yzbsl text," +
            "zf text," +
            "yqhl text," +
            "zyyw text)"


    override fun onCreate(p0: SQLiteDatabase) {
        Log.d("MainActivity","my表创建成功")
        p0.execSQL(createStock)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun insertStock(stockList: List<StockResponse>) {
        val db = writableDatabase
        for (i in stockList) {
            val values = ContentValues().apply {
                put("zqdm", i.zqdm ?: "")
                put("zqjc", i.zqjc ?: "")
                put("sgdm", i.sgdm ?: "")
                put("fxsl", i.fxsl ?: "")
                put("swfxsl", i.swfxsl ?: "")
                put("sgsx", i.sgsx ?: "")
                put("dgsz", i.dgsz ?: "")
                put("sgrq", i.sgrq ?: "")
                put("fxjg", i.fxjg ?: "")
                put("zxj", i.zxj ?: "0")
                put("srspj", i.srspj ?: "")
                put("zqgbrq", i.zqgbrq ?: "")
                put("zqjkrq", i.zqjkrq ?: "")
                put("ssrq", i.ssrq ?: "")
                put("syl", i.syl ?: "")
                put("hysyl", i.hysyl ?: "")
                put("wszql", i.wszql ?: "")
                put("yzbsl", i.yzbsl ?: "")
                put("zf", i.zf ?: "0")
                put("yqhl", i.yqhl ?: "")
                put("zyyw", i.zyyw ?: "")
            }
            db.insert("Stock", null, values)
        }
    }

    fun searchStock() : Boolean {
        val db = writableDatabase
        val cursor = db.query("Stock", null, null, null, null, null, null)
        return cursor.moveToFirst()
    }

    fun getAll() : List<Stock>{
        val db = writableDatabase
        val cursor = db.query("Stock", null, null, null, null, null, null)
        var stockList = ArrayList<Stock>()
        if (cursor.moveToFirst()) {
            do {
                var zqjc: String = ""
                var zxj: String = ""
                var zf: String = ""
                var columnIndex = cursor.getColumnIndex("zqjc")
                if (columnIndex >= 0) {
                    zqjc = cursor.getString(columnIndex)
                }
                columnIndex = cursor.getColumnIndex("zxj")
                if (columnIndex >= 0) {
                    zxj = cursor.getString(columnIndex)
                }
                columnIndex = cursor.getColumnIndex("zf")
                if (columnIndex >= 0) {
                    zf = cursor.getString(columnIndex)
                }
                var tem: Stock = Stock(zqjc, zxj, zf)
                stockList.add(tem)
            } while (cursor.moveToNext())
            cursor.close()
        }
        return stockList
    }

    fun getStocks(query:String) : List<Stock>{
        val db = writableDatabase
        val cursor = db.query("Stock", null, null, null, null, null, null)
        var stockList = ArrayList<Stock>()
        if (cursor.moveToFirst()) {
            do {
                var zqjc: String = ""
                var zxj: String = ""
                var zf: String = ""
                var columnIndex = cursor.getColumnIndex("zqjc")
                if (columnIndex >= 0) {
                    zqjc = cursor.getString(columnIndex)
                }
                columnIndex = cursor.getColumnIndex("zxj")
                if (columnIndex >= 0) {
                    zxj = cursor.getString(columnIndex)
                }
                columnIndex = cursor.getColumnIndex("zf")
                if (columnIndex >= 0) {
                    zf = cursor.getString(columnIndex)
                }
                var tem: Stock = Stock(zqjc, zxj, zf)
                stockList.add(tem)
            } while (cursor.moveToNext())
            cursor.close()
        }
        val filteredList = stockList.filter { stock ->
            stock.zqjc.contains(query, ignoreCase = true)
        }
        return filteredList

    }
}



