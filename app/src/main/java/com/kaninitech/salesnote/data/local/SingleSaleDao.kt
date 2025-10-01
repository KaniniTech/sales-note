package com.kaninitech.salesnote.data.local

//


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kaninitech.salesnote.model.DailySalesReport
import com.kaninitech.salesnote.model.SingleSaleEntity
import kotlinx.coroutines.flow.Flow

//This interface defines the database operations.
@Dao
interface SingleSaleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSingleSale(singleSaleEntity: SingleSaleEntity)

    @Update
    suspend fun updateSingleSale(singleSaleEntity: SingleSaleEntity)

    @Query("SELECT * FROM single_sale ORDER BY timestamp DESC")
    fun getAllSingleSale(): Flow<List<SingleSaleEntity>>

    @Query("SELECT * FROM single_sale WHERE date = :saleDate")
    fun getSalesByDate(saleDate: String): Flow<List<SingleSaleEntity>>



    @Query("""
    SELECT 
        date,
        SUM(CASE WHEN saleType = 'Cash' THEN totalSale ELSE 0 END) AS cash,
        SUM(CASE WHEN saleType = 'Bank' THEN totalSale ELSE 0 END) AS bank,
        SUM(CASE WHEN saleType = 'M-pesa' THEN totalSale ELSE 0 END) AS mpesa,
        SUM(CASE WHEN saleType NOT IN ('Cash','Bank','M-pesa') THEN totalSale ELSE 0 END) AS other,
        SUM(totalSale) AS total
    FROM single_sale
    GROUP BY date
    ORDER BY date DESC
""")
    fun getDailySalesReports(): Flow<List<DailySalesReport>>





}