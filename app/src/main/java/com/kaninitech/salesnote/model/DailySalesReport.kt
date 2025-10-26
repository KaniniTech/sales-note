package com.kaninitech.salesnote.model

data class DailySalesReport(
    val date: String,
    val cash: Float,
    val bank: Float,
    val mobile: Float,
    val other: Float,
    val total: Float
)
