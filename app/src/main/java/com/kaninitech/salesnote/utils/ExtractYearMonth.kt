package com.kaninitech.salesnote.utils

import java.time.LocalDate
import java.util.Locale

fun extractYearMonth(date: String): String? {
    return try {
        val parsed = LocalDate.parse(date)
        "${parsed.year}-${String.format(Locale.US, "%02d", parsed.monthValue)}"
    } catch (e: Exception) {
        null // or return ""
    }
}

