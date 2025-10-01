package com.kaninitech.salesnote.repository

import com.kaninitech.salesnote.data.local.SingleSaleDao
import com.kaninitech.salesnote.model.DailySalesReport
import com.kaninitech.salesnote.model.SingleSaleEntity
import kotlinx.coroutines.flow.Flow

class SingleSaleRepository(private val singleSaleDao: SingleSaleDao) {

    suspend fun insertSingleSale(singleSaleEntity: SingleSaleEntity) {
        singleSaleDao.insertSingleSale(singleSaleEntity)
    }

    suspend fun updateSingleSale(singleSaleEntity: SingleSaleEntity) {
        singleSaleDao.updateSingleSale(singleSaleEntity)
    }


    fun getAllSingleSale(): Flow<List<SingleSaleEntity>> = singleSaleDao.getAllSingleSale()


    fun getSalesByDate(saleDate: String): Flow<List<SingleSaleEntity>> = singleSaleDao.getSalesByDate(saleDate)


//    fun getDailySalesReports(): Flow<List<DailySalesReport>> {
//        return singleSaleDao.getDailySalesReports().map { dailySalesReports ->
//            dailySalesReports.map { dailySalesReport ->
//                DailySalesReport(
//                    date = dailySalesReport.date,
//                    cash = dailySalesReport.cash,
//                    bank = dailySalesReport.bank,
//                    mpesa = dailySalesReport.mpesa,
//                    other = dailySalesReport.other,
//                    total = dailySalesReport.total
//                )
//            }
//        }
//    }

    fun getDailySalesReports(): Flow<List<DailySalesReport>> {
        return singleSaleDao.getDailySalesReports()
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