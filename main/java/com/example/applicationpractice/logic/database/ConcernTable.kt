package com.example.retrofit.logic.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.applicationpractice.StockApplication
import com.example.applicationpractice.logic.modle.Stock


class ConcernTable(context: Context) :
    SQLiteOpenHelper(context, "Stock.db", null, 3) {
    val myDatabaseHelper = MyDatabaseHelper(StockApplication.context)

    val createStocak = "create table ConcernStock (" +
            "id integer primary key autoincrement," +
            "zqjc text," +
            "zxj text," +
            "zf text)"

    override fun onCreate(p0: SQLiteDatabase) {
        Log.d("MainActivity","con表创建成功")
        p0.execSQL(createStocak)
        p0.execSQL(myDatabaseHelper.createStock)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
    }

    fun insertConcernStock(stock: Stock) {
       val db = writableDatabase
        val searchKeyword = "${stock.zqjc}"
        val selection = "zqjc LIKE ?"
        val selectionArgs = arrayOf("%$searchKeyword%")
        val cursor = db.query("ConcernStock", null, selection, selectionArgs, null, null, null)
        if (cursor.moveToFirst()){
            Toast.makeText(StockApplication.context, "${stock.zqjc} 已经关注过了", Toast.LENGTH_SHORT).show()
        }else{
            Toast.makeText(StockApplication.context, "${stock.zqjc} 关注成功", Toast.LENGTH_SHORT).show()
            val values = ContentValues().apply {
                put("zqjc" , stock.zqjc )
                put("zxj" , stock.zxj )
                put("zf" , stock.zf )
            }
            db.insert("ConcernStock", null, values)
        }
    }

    fun removeConcernStock(query: String) :List<Stock>{
        val db = writableDatabase
        db.delete("ConcernStock","zqjc = ?", arrayOf(query))
        val cursor = db.query("ConcernStock", null, null, null, null, null, null)
        var concernStockList = ArrayList<Stock>()
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
                concernStockList.add(tem)
            } while (cursor.moveToNext())
            cursor.close()
        }
        return concernStockList



    }

    fun searchConcernStock() : Boolean {
        val db = writableDatabase
        val cursor = db.query("ConcernStock", null, null, null, null, null, null)
        return cursor.moveToFirst()
    }

    fun getAllConcernStock(query: String) : List<Stock>{
        val db = writableDatabase
        val cursor = db.query("ConcernStock", null, null, null, null, null, null)
        var concernStockList = ArrayList<Stock>()
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
                concernStockList.add(tem)
            } while (cursor.moveToNext())
            cursor.close()
        }
        val filteredList = concernStockList.filter { stock ->
            stock.zqjc.contains(query, ignoreCase = true)
        }
        return filteredList

    }


}
