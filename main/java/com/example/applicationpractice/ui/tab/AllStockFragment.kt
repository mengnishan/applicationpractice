package com.example.retrofit.ui.tab

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationpractice.R
import com.example.applicationpractice.ui.StockAdapter
import com.example.applicationpractice.ui.StockViewModel
import kotlinx.android.synthetic.main.fragment_all_stock.*
import kotlinx.android.synthetic.main.stock_item.*
import java.util.Locale



class AllStockFragment : Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(StockViewModel::class.java) }
    private lateinit var adapter: StockAdapter
    //用于获得父布局的搜索框，设置为空方便释放空间
    private var activity: AppCompatActivity? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_all_stock, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is AppCompatActivity) {
            activity = context
        }
    }

    override fun onDetach() {
        super.onDetach()
        // 释放 activity 引用
        activity = null
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val searchmc = activity?.findViewById<EditText>(R.id.searchmc)
        //获得父布局的搜索框
        val layoutManager = LinearLayoutManager(activity)
        recyclerView.layoutManager = layoutManager
        adapter = StockAdapter(viewModel.stockList)
        recyclerView.adapter = adapter

        val c = searchmc?.text.toString()
        //这里的c不一定是“”还可能是初始化时在另一个viewpage搜索的内容
        viewModel.searchStocks(c)
        //这里相当于初始化这个viewpage

        //监听搜索框的变化
        searchmc?.addTextChangedListener { editable ->
            val content = editable.toString()
            viewModel.searchStocks(content)
        }
        viewModel.stockLiveData.observe(viewLifecycleOwner, Observer { result ->
            val stocks = result.getOrNull()
            //getOrNull() 方法尝试从 Result 对象中获取操作成功时的结果值。如果操作成功并且结果存在，它将返回结果值。否则，它将返回 null
            if (stocks != null) {
                if (stocks.isNotEmpty()) {
                    recyclerView.visibility = View.VISIBLE
                    viewModel.stockList.clear()
                    viewModel.stockList.addAll(stocks)
                    adapter.notifyDataSetChanged()
                } else {
                    Toast.makeText(activity, "未找到有关数据", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(activity, "数据库空，或正在加载中", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
        //刷新页面，还没有实现数据的更新
        swipeRefresh.setOnRefreshListener {
            Toast.makeText(activity, "刷新成功", Toast.LENGTH_SHORT).show()
            swipeRefresh.isRefreshing = false
        }
    }

}


