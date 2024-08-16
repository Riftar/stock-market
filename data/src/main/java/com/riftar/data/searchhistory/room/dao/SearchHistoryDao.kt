package com.riftar.data.searchhistory.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.riftar.data.searchhistory.room.entity.SearchHistoryEntity
import com.riftar.data.searchhistory.room.entity.SearchHistoryEntity.Companion.SEARCH_HISTORY_TABLE

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(search: SearchHistoryEntity): Long

    @Query("DELETE FROM $SEARCH_HISTORY_TABLE")
    suspend fun clearAll()

    @Query("DELETE FROM $SEARCH_HISTORY_TABLE WHERE symbol = :symbol")
    suspend fun clearAllById(symbol: String)

    @Query("SELECT * FROM $SEARCH_HISTORY_TABLE WHERE symbol LIKE '%' || :query || '%' ORDER BY searchTimeMillis DESC")
    fun getSearchHistoryByQuery(query: String): PagingSource<Int, SearchHistoryEntity>

}