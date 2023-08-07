package com.example.retrofit.ui.tab

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.applicationpractice.R
import com.example.applicationpractice.ui.ConcernStockAdapter
import com.example.applicationpractice.ui.StockViewModel
import kotlinx.android.synthetic.main.concernstock_item.*

import kotlinx.android.synthetic.main.fragment_concern.*

class ConcernFragment : ConcernStockAdapter.ButtonClickListener,Fragment() {
    val viewModel by lazy { ViewModelProvider(this).get(StockViewModel::class.java) }
    private lateinit var adapter: ConcernStockAdapter
    private var activity: AppCompatActivity? = null
    //用于获得父布局的搜索框，设置为空方便释放空间

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_concern, container, false)
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
        val layoutManager = LinearLayoutManager(activity)
        recyclerViewc.layoutManager = layoutManager
        adapter = ConcernStockAdapter(viewModel.stockConcernList)
        recyclerViewc.adapter = adapter

        swipeRefreshc.setColorSchemeColors(
            ContextCompat.getColor(
                requireContext(),
                R.color.purple_500
            )
        )
        swipeRefreshc.setOnRefreshListener {
            refreshStocks(adapter, searchmc)
        }

        viewModel.searchStocks(searchmc?.text.toString())
        //获得父布局的搜索框，并进行搜索

        //监听搜索框的变化
        searchmc?.addTextChangedListener { editable ->
            val content = editable.toString()
            viewModel.searchStocks(content)
        }

        viewModel.stockConcernLiveData.observe(viewLifecycleOwner, Observer { result ->
            val stocks = result.getOrNull()
            if (stocks != null) {
                recyclerViewc.visibility = View.VISIBLE
                viewModel.stockConcernList.clear()
                viewModel.stockConcernList.addAll(stocks)
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(activity, "未能查询到任何信息", Toast.LENGTH_SHORT).show()
                result.exceptionOrNull()?.printStackTrace()
            }
        })
    }
    //刷新
    private fun refreshStocks(adapter: ConcernStockAdapter, searchmc: EditText?) {
        viewModel.searchStocks(searchmc?.text.toString())
        Log.d("MainActivity", "下拉刷新的返回值 + ${searchmc?.text.toString()}")
        swipeRefreshc.isRefreshing = false
    }

     override fun onButtonClicked() {
        val searchmc = activity?.findViewById<EditText>(R.id.searchmc)
        searchmc?.setText("${searchmc?.text.toString()}")
    }

}
