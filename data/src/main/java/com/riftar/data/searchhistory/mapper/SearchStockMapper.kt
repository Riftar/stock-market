package com.riftar.data.searchhistory.mapper

import com.riftar.common.helper.orZero
import com.riftar.data.searchhistory.room.entity.SearchHistoryEntity
import com.riftar.domain.searchhistory.model.StockHistory

fun SearchHistoryEntity.toDomainModel() = StockHistory(
    symbol = this.symbol,
    shortName = this.shortName.orEmpty(),
    searchTimeMillis = this.searchTimeMillis.orZero(),
    close = this.close.orZero(),
    percentageChange = this.percentageChange.orZero()
)

fun StockHistory.toEntityModel() = SearchHistoryEntity(
    symbol = this.symbol,
    shortName = this.shortName,
    searchTimeMillis = System.currentTimeMillis(),
    close = this.close.orZero(),
    percentageChange = this.percentageChange
)