package com.example.applicationpractice.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.applicationpractice.R
import com.example.applicationpractice.StockApplication
import com.example.applicationpractice.logic.modle.Stock
import com.example.retrofit.logic.database.ConcernTable
import kotlinx.android.synthetic.main.concernstock_item.view.*
import kotlinx.android.synthetic.main.stock_item.view.*

class ConcernStockAdapter (private var stockList: MutableList<Stock>) : RecyclerView.Adapter<ConcernStockAdapter.ViewHolder>() {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val zqjc: TextView = view.findViewById(R.id.zqjc)
        val zxj: TextView = view.findViewById(R.id.zxj)
        val zf: TextView = view.findViewById(R.id.zf)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.concernstock_item, parent, false)
        //以下代码实现点击减号删除数据库中所关注的数据
        val concernTable: ConcernTable = ConcernTable(StockApplication.context)
        val viewHolder = ViewHolder(view)
        concernTable.writableDatabase
        viewHolder.itemView.removeStock.setOnClickListener{
            val position = viewHolder.adapterPosition
            val stock = stockList[position]
            Toast.makeText(StockApplication.context, "${stock.zqjc} 移除成功", Toast.LENGTH_SHORT).show()
            val newData = concernTable.removeConcernStock(stock.zqjc)
            //删除数据后立即更新RecyclerView
            updateData(newData)
        }
        return viewHolder
    }

    fun updateData(newData: List<Stock>) {
        stockList.clear()
        stockList.addAll(newData)
        notifyDataSetChanged()  // 更新 RecyclerView
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val stock = stockList[position]
        holder.zqjc.text = stock.zqjc
        holder.zxj.text = stock.zxj
        holder.zf.text = stock.zf
    }
    override fun getItemCount() = stockList.size
}