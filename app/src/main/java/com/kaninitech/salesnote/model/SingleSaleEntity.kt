package com.kaninitech.salesnote.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "single_sale")
data class SingleSaleEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val date: String, //DD-MM-YYYY
    val receipt: String,
    val saleType: String,
    val description: String,
    val totalSale: Float,
    val totalPaid: Float,
    val change: Float,
    val timestamp: Long = System.currentTimeMillis()
)