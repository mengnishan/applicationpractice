package com.example.applicationpractice.ui

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationpractice.R
import com.example.applicationpractice.StockApplication
import com.example.applicationpractice.logic.modle.Stock
import com.example.retrofit.logic.database.ConcernTable
import kotlinx.android.synthetic.main.stock_item.view.*

class StockAdapter (private val stockList: List<Stock>) : RecyclerView.Adapter<StockAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val zqjc: TextView = view.findViewById(R.id.zqjc)
        val zxj: TextView = view.findViewById(R.id.zxj)
        val zf: TextView = view.findViewById(R.id.zf)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.stock_item, parent, false)
        //以下代码实现点击加号将数据添加到数据库中
        val concernTable: ConcernTable = ConcernTable(StockApplication.context)
        val viewHolder = ViewHolder(view)
        concernTable.writableDatabase
        viewHolder.itemView.addStock.setOnClickListener{
            val position = viewHolder.adapterPosition
            val stock = stockList[position]
            Toast.makeText(StockApplication.context, "${stock.zqjc} 关注成功", Toast.LENGTH_SHORT).show()
            concernTable.insertConcernStock(stock)

        }
        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = stockList[position]
        holder.zqjc.text = stock.zqjc
        holder.zxj.text = stock.zxj
        holder.zf.text = stock.zf
    }
    override fun getItemCount() = stockList.size
}
