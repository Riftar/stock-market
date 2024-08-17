package com.riftar.data.searchhistory.room

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import com.riftar.data.common.database.AppDatabase
import com.riftar.data.searchhistory.room.dao.SearchHistoryDao
import com.riftar.data.searchhistory.room.entity.SearchHistoryEntity
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
@SmallTest
class SearchHistoryDaoTest {
    private lateinit var searchHistoryDao: SearchHistoryDao
    private lateinit var db: AppDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, AppDatabase::class.java
        ).build()
        searchHistoryDao = db.getSearchHistoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun writeHistoryAndGetHistory() = runBlocking {
        val stockHistoryEntity = SearchHistoryEntity(
            symbol = "AAPL",
            shortName = "Apple Inc.",
            close = 122.13,
            percentageChange = 14.1,
            searchTimeMillis = 1723798058816
        )
        searchHistoryDao.insertAll(stockHistoryEntity)
        val historyById = searchHistoryDao.getSearchHistoryById("AAPL")
        assertEquals(stockHistoryEntity, historyById)
    }

    @Test
    fun writeHistoryAndClearAllHistory() = runBlocking {
        val stockHistoryEntity = SearchHistoryEntity(
            symbol = "AAPL",
            shortName = "Apple Inc.",
            close = 122.13,
            percentageChange = 14.1,
            searchTimeMillis = 1723798058816
        )
        searchHistoryDao.insertAll(stockHistoryEntity)
        searchHistoryDao.clearAll()
        val historyById = searchHistoryDao.getSearchHistoryById("AAPL")
        assertEquals(null, historyById)
    }

    @Test
    fun writeHistoryAndClearHistoryById() = runBlocking {
        val stockHistoryEntity = SearchHistoryEntity(
            symbol = "AAPL",
            shortName = "Apple Inc.",
            close = 122.13,
            percentageChange = 14.1,
            searchTimeMillis = 1723798058816
        )
        searchHistoryDao.insertAll(stockHistoryEntity)
        searchHistoryDao.clearAllById("AAPL")
        val historyById = searchHistoryDao.getSearchHistoryById("AAPL")
        assertEquals(null, historyById)
    }

    @Test
    fun transactionInsertAndGetHistory() = runBlocking {
        val stockHistoryEntity = SearchHistoryEntity(
            symbol = "AAPL",
            shortName = "Apple Inc.",
            close = 122.13,
            percentageChange = 14.1,
            searchTimeMillis = 1723798058816
        )
        val historyById = searchHistoryDao.insertAndGetSearchHistoryById(stockHistoryEntity)
        assertEquals(stockHistoryEntity, historyById)
    }
}