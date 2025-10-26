package com.kaninitech.salesnote.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kaninitech.salesnote.model.SingleProductEntity
import com.kaninitech.salesnote.repository.SingleProductRepository
import com.kaninitech.salesnote.screens.Sales
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class SingleProductSaleViewModel(private val singleProductRepository: SingleProductRepository) : ViewModel() {

    //hold list of single product
    private val _singleProduct = MutableStateFlow<List<SingleProductEntity>>(emptyList())
    val singleProduct: StateFlow<List<SingleProductEntity>> = _singleProduct
    fun insertSingleProduct(singleProductEntity: SingleProductEntity) {
        viewModelScope.launch {
            singleProductRepository.insertSoldProduct(singleProductEntity)
        }
    }

    fun updateSingleProduct(singleProductEntity: SingleProductEntity) {
        viewModelScope.launch {
            singleProductRepository.updateSoldProduct(singleProductEntity)
        }
    }




//    init {
//        getSingleSalesByDate()
//    }

    /**
     * Fetch all watchlist
     */
    private fun getSingleSalesByDate(saleDate: String) {
        viewModelScope.launch {
           singleProductRepository.getSingleSalesByDate(saleDate).collectLatest { products ->
                _singleProduct.value = products
            }
        }
    }



    private val _salesSummary = MutableStateFlow<List<Sales>>(emptyList())
    val salesSummary: StateFlow<List<Sales>> = _salesSummary.asStateFlow()

    private val _productsForReceipt = MutableStateFlow<List<SingleProductEntity>>(emptyList())
    val productsForReceipt: StateFlow<List<SingleProductEntity>> = _productsForReceipt.asStateFlow()

    fun loadSalesByDate(date: String) {
        viewModelScope.launch {
            singleProductRepository.getSalesSummaryByDate(date).collect {
                _salesSummary.value = it
            }
        }
    }

    fun loadProductsByReceipt(receipt: String) {
        viewModelScope.launch {
            singleProductRepository.getProductsByReceipt(receipt).collect {
                _productsForReceipt.value = it
            }
        }
    }


    fun hardDeleteSalesProductById(receipt: String) {
        viewModelScope.launch {
            singleProductRepository.hardDeleteSalesProductById(receipt)
        }
    }











}