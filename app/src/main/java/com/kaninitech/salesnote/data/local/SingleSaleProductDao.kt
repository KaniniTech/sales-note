package com.kaninitech.salesnote.data.local




import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.kaninitech.salesnote.model.SingleProductEntity
import com.kaninitech.salesnote.screens.Sales
import kotlinx.coroutines.flow.Flow

//This interface defines the database operations.
@Dao
interface SingleSaleProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSoldProduct(singleProductEntity: SingleProductEntity)

    @Update
    suspend fun updateSoldProduct(singleProductEntity: SingleProductEntity)


    @Query("SELECT * FROM single_product WHERE date = :saleDate")
    fun getAllSingleSalesByDate(saleDate: String): Flow<List<SingleProductEntity>>


    // Get all grouped sales by receipt for a given date
    @Query("""
        SELECT 
            receipt, 
            date,
            SUM(total) AS totalSales 
        FROM single_product 
        WHERE date = :saleDate
        GROUP BY receipt, date
        ORDER BY receipt DESC
    """)
    fun getSalesSummaryByDate(saleDate: String): Flow<List<Sales>>



    // Get all sold items for a specific receipt
    @Query("SELECT * FROM single_product WHERE receipt = :receiptNumber")
    fun getProductsByReceipt(receiptNumber: String): Flow<List<SingleProductEntity>>



}