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










//    // Holds the list of wishlist
//    private val _watchlist = MutableStateFlow<List<WatchListEntity>>(emptyList())
//    val watchlist: StateFlow<List<WatchListEntity>> = _watchlist
//
//
//    fun insertWatchlist(watchlist: WatchListEntity) {
//        viewModelScope.launch {
//            watchlistRepository.insertWatchlist(watchlist)
//        }
//    }
//
//    fun updateWatchlist(watchlist: WatchListEntity) {
//        viewModelScope.launch {
//            watchlistRepository.updateWatchlist(watchlist)
//        }
//    }
//
//
//
//    init {
//        getAllWatchList()
//    }
//
//    /**
//     * Fetch all watchlist
//     */
//    private fun getAllWatchList() {
//        viewModelScope.launch {
//            watchlistRepository.getAllWatchList().collectLatest { watchList ->
//                _watchlist.value = watchList
//            }
//        }
//    }
//
//
//    private val _watchlistState = MutableStateFlow<WatchListEntity?>(null)
//    val watchlistState: StateFlow<WatchListEntity?> = _watchlistState
//
//
//
//    fun getWatchListById(itemId: String) {
//        viewModelScope.launch {
//            watchlistRepository.getWatchListById(itemId).collect { watchlist ->
//                _watchlistState.value = watchlist
//            }
//        }
//    }
//
//
//    fun deleteWatchListById(itemId: String) {
//        viewModelScope.launch {
//            watchlistRepository.deleteWatchListById(itemId)
//        }
//    }
//
//
//    fun updateWatchlistStatusById(itemId: String, newStatus: String) {
//        viewModelScope.launch {
//            val success = watchlistRepository.updateWatchlistStatusById(itemId, newStatus)
//            if (success) {
//                // Refresh debt list if update is successful
//                _watchlist.value = _watchlist.value.map { watchlist ->
//                    if (watchlist.watchlistId == itemId) watchlist.copy(watchlistStatus = newStatus) else watchlist
//                }
//            }
//
//        }
//    }
//
//
//    fun updateStatusById(itemId: String, newStatus: String) {
//        viewModelScope.launch {
//            val success = watchlistRepository.updateStatusById(itemId, newStatus)
//
//            if (success) {
//                // Refresh debt list if update is successful
//                _watchlist.value = _watchlist.value.map { watchlist ->
//                    if (watchlist.watchlistId == itemId) watchlist.copy(status = newStatus) else watchlist
//                }
//            }
//        }
//    }
//
//
//    fun updateSeenPageEpisodeById(itemId: String, newSeenPageEpisode: Int) {
//        viewModelScope.launch {
//            val success = watchlistRepository.updateSeenPageEpisodeById(itemId, newSeenPageEpisode)
//
//            if (success) {
//                // Refresh debt list if update is successful
//                _watchlist.value = _watchlist.value.map { watchlist ->
//                    if (watchlist.watchlistId == itemId) watchlist.copy(seenPageEpisode = newSeenPageEpisode) else watchlist
//                }
//
//            }
//
//        }
//    }
//
//    fun updateWatchlistById(itemId: String, watchListTile: String,expectedCompleteDate : String, link: String?, type: String, notes: String?, category: String, noEpisodesPage: Int) {
//        viewModelScope.launch {
//            val success = watchlistRepository.updateWatchlistById(itemId, watchListTile,expectedCompleteDate , link, type, notes, category, noEpisodesPage)
//            if (success) {
//                // Refresh debt list if update is successful
//                _watchlist.value = _watchlist.value.map { watchlist ->
//                    if (watchlist.watchlistId == itemId) watchlist.copy(watchListTitle = watchListTile) else watchlist
//                }
//            }
//
//        }
//    }
//






}