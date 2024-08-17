package com.riftar.data.searchhistory.room.dao

import android.util.Log
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.riftar.data.searchhistory.room.entity.SearchHistoryEntity
import com.riftar.data.searchhistory.room.entity.SearchHistoryEntity.Companion.SEARCH_HISTORY_TABLE

@Dao
interface SearchHistoryDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(search: SearchHistoryEntity)

    @Query("DELETE FROM $SEARCH_HISTORY_TABLE")
    suspend fun clearAll()

    @Query("DELETE FROM $SEARCH_HISTORY_TABLE WHERE symbol = :symbol")
    suspend fun clearAllById(symbol: String)

    @Query("SELECT * FROM $SEARCH_HISTORY_TABLE WHERE symbol = :symbol")
    suspend fun getSearchHistoryById(symbol: String): SearchHistoryEntity?

    @Query("SELECT * FROM $SEARCH_HISTORY_TABLE WHERE symbol LIKE '%' || :query || '%' ORDER BY searchTimeMillis DESC")
    fun getSearchHistoryByQuery(query: String): PagingSource<Int, SearchHistoryEntity>

    @Transaction
    suspend fun insertAndGetSearchHistoryById(searchHistory: SearchHistoryEntity): SearchHistoryEntity? {
        Log.d("Rifqi-test", "insertAndGetSearchHistoryById: CALLED")
        insertAll(searchHistory)
        return getSearchHistoryById(searchHistory.symbol)
    }
}