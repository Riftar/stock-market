package com.riftar.data.searchhistory.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.riftar.data.searchhistory.mapper.toDomainModel
import com.riftar.data.searchhistory.mapper.toEntityModel
import com.riftar.data.searchhistory.room.dao.SearchHistoryDao
import com.riftar.domain.searchhistory.model.StockHistory
import com.riftar.domain.searchhistory.repository.SearchStockRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map

class SearchStockRepositoryImpl(private val dao: SearchHistoryDao) : SearchStockRepository {
    override fun getListUserByQuery(query: String): Flow<PagingData<StockHistory>> {
        return Pager(
            config = getDefaultPageConfig(),
            pagingSourceFactory = { dao.getSearchHistoryByQuery(query) }
        ).flow.map { pagingData ->
            pagingData.map { it.toDomainModel() }
        }.catch {
            emit(PagingData.empty())
        }
    }

    override fun saveCurrentSearchToHistory(stockHistory: StockHistory): Flow<Result<Unit>> = flow {
        val entityModel = stockHistory.toEntityModel()
        val retrievedEntity = dao.insertAndGetSearchHistoryById(entityModel)
        if (retrievedEntity != null && retrievedEntity == entityModel) {
            emit(Result.success(Unit))
        } else {
            emit(Result.failure(Exception("Failed to save search history")))
        }
    }.catch { e ->
        emit(Result.failure(e))
    }

    private fun getDefaultPageConfig(): PagingConfig {
        return PagingConfig(
            pageSize = 30,
            enablePlaceholders = false,
            initialLoadSize = 30
        )
    }
}