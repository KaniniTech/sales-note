package com.kaninitech.salesnote.repository



import com.kaninitech.salesnote.data.local.SingleSaleProductDao
import com.kaninitech.salesnote.model.SingleProductEntity
import com.kaninitech.salesnote.screens.Sales
import kotlinx.coroutines.flow.Flow

class SingleProductRepository(private val singleSaleProductDao: SingleSaleProductDao) {
  suspend fun insertSoldProduct(singleProductEntity: SingleProductEntity) {
      singleSaleProductDao.insertSoldProduct(singleProductEntity)
  }

    suspend fun updateSoldProduct(singleProductEntity: SingleProductEntity) {
      singleSaleProductDao.updateSoldProduct(singleProductEntity)
  }

    fun getSingleSalesByDate(saleDate: String): Flow<List<SingleProductEntity>> = singleSaleProductDao.getAllSingleSalesByDate(saleDate)


  suspend fun hardDeleteSalesProductById(receipt: String): Int? {
    return singleSaleProductDao.hardDeleteSalesProductById(receipt)
  }

  fun getSalesSummaryByDate(date: String): Flow<List<Sales>> {
    return singleSaleProductDao.getSalesSummaryByDate(date)
  }

  fun getProductsByReceipt(receipt: String): Flow<List<SingleProductEntity>> {
    return singleSaleProductDao.getProductsByReceipt(receipt)
  }



//    fun getAllWatchList(): Flow<List<WatchListEntity>> = watchListDao.getAllWatchList()
//
//
//
//    suspend fun insertWatchlist(watchlist: WatchListEntity) {
//        watchListDao.insertWatchlist(watchlist)
//    }
//
//    suspend fun updateWatchlist(watchlist: WatchListEntity) {
//        watchListDao.updateWatchlist(watchlist)
//    }
//
//    fun getWatchListById(watchlistId: String): Flow<WatchListEntity?> {
//        return watchListDao.getWatchListById(watchlistId)
//    }
//
//    suspend fun deleteWatchListById(watchlistId: String) {
//        watchListDao.deleteWatchListById(watchlistId)
//    }
//    suspend fun updateWatchlistStatusById(watchlistId: String, watchlistStatus: String): Boolean {
//        val rowsUpdated = watchListDao.updateWatchlistStatusById(watchlistId, watchlistStatus) ?: 0
//        return rowsUpdated > 0
//    }
//
//    suspend fun updateStatusById(watchlistId: String, status: String): Boolean {
//        val rowsUpdated = watchListDao.updateStatusById(watchlistId, status) ?: 0
//        return rowsUpdated > 0
//    }
//
//    suspend fun updateSeenPageEpisodeById(watchlistId: String, seenPageEpisode: Int): Boolean {
//        val rowsUpdated = watchListDao.updateSeenPageEpisodeById(watchlistId, seenPageEpisode) ?: 0
//        return rowsUpdated > 0
//    }
//
//
//    suspend fun updateWatchlistById(watchlistId: String, watchListTile: String,expectedCompleteDate : String, link: String?, type: String, notes: String?, category: String, noEpisodesPage: Int): Boolean {
//        val rowsUpdated = watchListDao.updateWatchlistById(watchlistId, watchListTile,expectedCompleteDate , link, type, notes, category, noEpisodesPage) ?: 0
//        return rowsUpdated > 0
//    }



}