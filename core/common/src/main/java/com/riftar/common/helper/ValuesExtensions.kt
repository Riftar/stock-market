package com.riftar.common.helper

import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

// TODO remove unused
fun Int?.orZero() = this ?: 0
fun Long?.orZero() = this ?: 0
fun Float?.orZero() = this ?: 0f
fun Double?.orZero() = this ?: 0.0
fun Double?.orToStringEmpty() = this?.toString() ?: ""
fun String?.orZero() = this ?: "0"
fun String?.orDash() = if (this.isNullOrEmpty()) "-" else this
fun String?.checkIsDefaultDate() =
    if (this == "0001-01-01T00:00:00Z" || this.isNullOrEmpty()) "-" else this

fun String?.capitalize() = this?.replaceFirstChar(Char::titlecase) ?: this.orEmpty()
fun String.isNumeric(): Boolean {
    val regex = "-?[0-9]+(\\.[0-9]+)?".toRegex()
    return this.trim().matches(regex)
}

fun String?.isDoubleNotZeroOrEmpty(): Boolean {
    return if (!this.isNullOrEmpty()) {
        try {
            this.toDoubleOrNull() != 0.0
        } catch (e: Exception) {
            false
        }
    } else false
}

fun Boolean?.orFalse() = this ?: false
fun Boolean?.orTrue() = this ?: true
fun Boolean.toIntValue() = if (this) 1 else 0
fun Int.toBoolean() = this == 1
fun <T> T?.isNotNull(): Boolean = this != null
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
    return "$${this.roundTwoDecimal()}"
}

fun Float?.convertToUSD(): String {
    return "$${this.roundTwoDecimal()}"
}

fun Long.unixTimestampToDate(): String {
    val sdf = SimpleDateFormat("dd MMM", Locale.getDefault())
    val date = Date(this * 1000) // Convert seconds to milliseconds
    val dateFormatted = sdf.format(date)
    return dateFormatted
}