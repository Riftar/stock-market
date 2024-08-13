package com.riftar.data.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.riftar.data.searchhistory.room.dao.SearchHistoryDao
import com.riftar.data.searchhistory.room.entity.SearchHistoryEntity

@Database(
    entities = [SearchHistoryEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun getSearchHistoryDao(): SearchHistoryDao
}