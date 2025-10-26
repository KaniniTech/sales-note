package com.kaninitech.salesnote.repository

import com.kaninitech.salesnote.data.local.SingleSaleDao
import com.kaninitech.salesnote.model.DailySalesReport
import com.kaninitech.salesnote.model.SingleSaleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SingleSaleRepository(private val singleSaleDao: SingleSaleDao) {

    suspend fun insertSingleSale(singleSaleEntity: SingleSaleEntity) {
        singleSaleDao.insertSingleSale(singleSaleEntity)
    }

    suspend fun updateSingleSale(singleSaleEntity: SingleSaleEntity) {
        singleSaleDao.updateSingleSale(singleSaleEntity)
    }


    fun getAllSingleSale(): Flow<List<SingleSaleEntity>> = singleSaleDao.getAllSingleSale()


    fun getSalesByDate(saleDate: String): Flow<List<SingleSaleEntity>> = singleSaleDao.getSalesByDate(saleDate)

    suspend fun hardDeleteSalesById(receipt: String): Int? {
        return singleSaleDao.hardDeleteSalesById(receipt)
    }

    fun getDailySalesReports(): Flow<List<DailySalesReport>> {
        return singleSaleDao.getDailySalesReports()
    }


    fun getMonthlyTotalSales(month: String): Flow<Float> {
        return  singleSaleDao.getMonthlyTotalSales(month)
            .map { total -> total ?: 0.0f }  // Convert NULL to 0.0
    }


    fun getNumberOfMonthlySales(month: String): Flow<Int> {
        return singleSaleDao.getNumberOfMonthlySales(month)
            .map { total -> total ?: 0 }  // Convert NULL to 0
    }


}