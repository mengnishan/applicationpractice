import android.util.Log
import androidx.lifecycle.liveData
import com.example.applicationpractice.StockApplication
import com.example.applicationpractice.logic.modle.Stock
import com.example.applicationpractice.logic.modle.StockResponse
import com.example.applicationpractice.logic.network.StockNetwork
import com.example.retrofit.logic.database.ConcernTable
import com.example.retrofit.logic.database.MyDatabaseHelper
import kotlinx.coroutines.Dispatchers


object Repository {
    private val myDatabaseHelper: MyDatabaseHelper = MyDatabaseHelper(StockApplication.context)
    private val concernTable: ConcernTable = ConcernTable(StockApplication.context)

    //搜索包含query的数据
    fun searchStock(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            //查看是否有数据库，有则读取数据，没有则创建。
            val shouldFetchFromNetwork = shouldFetchFromNetwork()
            if (shouldFetchFromNetwork) {
                val stocks = getStocksFromDatabase(query)
                Result.success(stocks)
            } else {
                val stockResponse = fetchStocksFromNetwork()
                saveStocks(stockResponse)//保存从网络获取的数据到数据库
                val stocks = getStocksFromDatabase(query)
                Result.success(stocks)
            }
        } catch (e: Exception) {
            Result.failure<List<Stock>>(e)
        }
        emit(result)
    }

    //搜索关注的股票
    fun searchConcernStock(query: String) = liveData(Dispatchers.IO) {
        val result = try {
            val stocks = getAllConcernStock(query)
            Result.success(stocks)
        } catch (e: Exception) {
            Result.failure<List<Stock>>(e)
        }
        emit(result)
    }

    private fun shouldFetchFromNetwork(): Boolean {
        return myDatabaseHelper.searchStock()
    }

    private suspend fun fetchStocksFromNetwork(): List<StockResponse> {
        val stock : List<StockResponse> = StockNetwork.searchStock()
        if (stock.isNullOrEmpty()) {
            throw RuntimeException("response status is ??")
        } else {
            return stock
        }
    }

    private fun saveStocks(stockList: List<StockResponse>) {
        myDatabaseHelper.insertStock(stockList)
    }

    private fun getStocksFromDatabase(query: String): List<Stock> {
        return myDatabaseHelper.getStocks(query)
    }

    private fun getAll(): List<Stock> {
        return myDatabaseHelper.getAll()
    }

    fun searchConcernStock():Boolean{
        val c = concernTable.searchConcernStock()
        return c
    }

    private fun getAllConcernStock(query: String):List<Stock>{
        return  concernTable.getAllConcernStock(query)
    }
}

