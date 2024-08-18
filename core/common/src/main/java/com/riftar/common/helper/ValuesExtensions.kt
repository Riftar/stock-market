package com.riftar.common.helper

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.math.abs

fun Int?.orZero() = this ?: 0
fun Long?.orZero() = this ?: 0
fun Float?.orZero() = this ?: 0f
fun Double?.orZero() = this ?: 0.0

fun Boolean?.orFalse() = this ?: false
fun Long.formatNumber(): String {
    return NumberFormat.getInstance(Locale.getDefault()).format(this)
}

fun Double?.roundTwoDecimal(): String {
    return "%.2f".format(this.orZero())
}

fun Float?.roundTwoDecimal(): String {
    return "%.2f".format(this.orZero())
}

fun Double?.convertToUSD(): String {
    return if (this.orZero() < 0.0) {
        "-$${abs(this.orZero()).roundTwoDecimal()}"
    } else "$${this.roundTwoDecimal()}"
}

fun Float?.convertToUSD(): String {
    return if (this.orZero() < 0.0) {
        "-$${abs(this.orZero()).roundTwoDecimal()}"
    } else "$${this.roundTwoDecimal()}"
}

fun Long.unixTimestampToDate(): String {
    val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
    val date = Date(this * 1000) // Convert seconds to milliseconds
    val dateFormatted = sdf.format(date)
    return dateFormatted
}
