package com.example.applicationpractice.ui

import Repository
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.applicationpractice.R
import com.example.retrofit.ui.tab.AllStockFragment
import com.example.retrofit.ui.tab.ConcernFragment
import com.example.retrofit.ui.tab.VariationFragment
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.activity_show_list.*


class StockFragment : Fragment() {
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.activity_show_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //配置viewpager
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return 3
            }

            override fun createFragment(position: Int): Fragment {
                return when (position) {
                    0 -> ConcernFragment()
                    1 -> VariationFragment()
                    else -> AllStockFragment()
                }
            }
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            when (position) {
                0 -> tab.text = "自选"
                1 -> tab.text = "异动"
                else -> tab.text = "全部"
            }
        }.attach()

        //如果数据库中有关注的股票则跳转到关注列表，没有则跳转到全部列表
        val repository = Repository
        when(repository.searchConcernStock()) {
            true ->  viewPager.currentItem = 0
            else -> viewPager.currentItem = 3
        }
    }
}