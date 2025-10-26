package com.kaninitech.salesnote.viewmodel


import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaninitech.salesnote.model.DailySalesReport
import com.kaninitech.salesnote.model.SingleSaleEntity
import com.kaninitech.salesnote.repository.SingleSaleRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SingleSaleViewModel(private val singleSaleRepository: SingleSaleRepository) : ViewModel() {
    private val _singleSale = MutableStateFlow<List<SingleSaleEntity>>(emptyList())
    val singleSale: StateFlow<List<SingleSaleEntity>> = _singleSale




    //hold list of single product
    private val _allSingleSale = MutableStateFlow<List<SingleSaleEntity>>(emptyList())
    val allSingleSale: StateFlow<List<SingleSaleEntity>> = _allSingleSale





    fun insertSingleSale(singleSaleEntity: SingleSaleEntity) {
        viewModelScope.launch {
//            singleProductRepository.insertSoldProduct(singleProductEntity)
            singleSaleRepository.insertSingleSale(singleSaleEntity)
        }
    }

    fun updateSingleSale(singleSaleEntity: SingleSaleEntity) {
        viewModelScope.launch {
//            singleProductRepository.updateSoldProduct(singleProductEntity)
            singleSaleRepository.updateSingleSale(singleSaleEntity)
        }
    }


//    init {
//        getSingleSalesByDate()
//    }

    /**
     * Fetch all watchlist
     */
    private fun getAllSingleSale() {
        viewModelScope.launch {
           singleSaleRepository.getAllSingleSale().collectLatest { products ->
                _allSingleSale.value = products
            }
        }
    }




     fun getSalesByDate(saleDate: String) {
        viewModelScope.launch {
           singleSaleRepository.getSalesByDate(saleDate).collectLatest { products ->
                _singleSale.value = products
            }
        }
    }





    // StateFlow to expose daily sales reports
    private val _dailySalesReports = MutableStateFlow<List<DailySalesReport>>(emptyList())
    val dailySalesReports: StateFlow<List<DailySalesReport>> = _dailySalesReports.asStateFlow()

    init {
        // Start collecting reports immediately when ViewModel is created
        viewModelScope.launch {
            singleSaleRepository.getDailySalesReports()
                .collect { reports ->
                    _dailySalesReports.value = reports
                }
        }
    }



    fun hardDeleteSalesById(receipt: String) {
        viewModelScope.launch {
            singleSaleRepository.hardDeleteSalesById(receipt)
        }
    }

    private val _totalMonthSales = MutableStateFlow(0.0f)
    val totalMonthSales: StateFlow<Float> = _totalMonthSales

    fun getMonthlyTotalSales(currentYearMonth : String) {
        viewModelScope.launch {
            Log.d("SaleViewModel", "Fetching monthly sales for: $currentYearMonth")
            singleSaleRepository.getMonthlyTotalSales(currentYearMonth).collectLatest { total ->
                _totalMonthSales.value = total
            }
        }
    }

    private val _totalNoOfSaleThisMonth = MutableStateFlow(0)
    val totalNoOfSaleThisMonth : StateFlow<Int> = _totalNoOfSaleThisMonth

    fun getNumberOfMonthlySales(currentYearMonth : String) {
        viewModelScope.launch {
            singleSaleRepository.getNumberOfMonthlySales(currentYearMonth).collectLatest { total ->
                _totalNoOfSaleThisMonth.value = total
            }
        }
    }

}





