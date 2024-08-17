package com.riftar.common.helper

import org.junit.Assert.assertEquals
import org.junit.Test
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ValuesExtensionsKtTest {

    @Test
    fun `test Int orZero`() {
        val nullValue: Int? = null
        assertEquals(0, nullValue.orZero())
        assertEquals(5, 5.orZero())
    }

    @Test
    fun `test Long orZero`() {
        val nullValue: Long? = null
        assertEquals(0L, nullValue.orZero())
        assertEquals(5L, 5L.orZero())
    }

    @Test
    fun `test Float orZero`() {
        val nullValue: Float? = null
        assertEquals(0.0f, nullValue.orZero())
        assertEquals(5f, 5f.orZero())
    }

    @Test
    fun `test Double orZero`() {
        val nullValue: Double? = null
        assertEquals(0.0, nullValue.orZero(), 0.0)
        assertEquals(5.0, 5.0.orZero(), 0.0)
    }

    @Test
    fun `test Boolean orFalse`() {
        assertEquals(false, null.orFalse())
        assertEquals(true, true.orFalse())
        assertEquals(false, false.orFalse())
    }

    @Test
    fun `test Long formatNumber`() {
        Locale.setDefault(Locale.US)
        assertEquals("1,000", 1000L.formatNumber())
        assertEquals("1,000,000", 1000000L.formatNumber())
    }

    @Test
    fun `test Double roundTwoDecimal`() {
        val nullValue: Double? = null
        assertEquals("3.14", 3.14159.roundTwoDecimal())
        assertEquals("0.00", nullValue.roundTwoDecimal())
    }

    @Test
    fun `test Float roundTwoDecimal`() {
        val nullValue: Float? = null
        assertEquals("3.14", 3.14159f.roundTwoDecimal())
        assertEquals("0.00", nullValue.roundTwoDecimal())
    }

    @Test
    fun `test Double convertToUSD`() {
        val nullValue: Double? = null
        assertEquals("$3.14", 3.14159.convertToUSD())
        assertEquals("-$3.14", (-3.14159).convertToUSD())
        assertEquals("$0.00", nullValue.convertToUSD())
    }

    @Test
    fun `test Float convertToUSD`() {
        val nullValue: Float? = null
        assertEquals("$3.14", 3.14159f.convertToUSD())
        assertEquals("-$3.14", (-3.14159f).convertToUSD())
        assertEquals("$0.00", nullValue.convertToUSD())
    }

    @Test
    fun `test Long unixTimestampToDate`() {
        val timestamp = 1609459200L // 2021-01-01 00:00:00 UTC
        val expectedFormat = SimpleDateFormat("dd MMM", Locale.getDefault())
        val expectedDate = expectedFormat.format(Date(timestamp * 1000))
        assertEquals(expectedDate, timestamp.unixTimestampToDate())
    }

}