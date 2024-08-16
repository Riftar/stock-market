package com.riftar.data.searchhistory.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.riftar.data.searchhistory.room.entity.SearchHistoryEntity.Companion.SEARCH_HISTORY_TABLE


@Entity(tableName = SEARCH_HISTORY_TABLE)
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = false)
    val symbol: String,
    val shortName: String?,
    val searchTimeMillis: Long?,
    val close: Double?,
    val percentageChange: Double?
) {
    companion object {
        const val SEARCH_HISTORY_TABLE = "search_history"
    }
}