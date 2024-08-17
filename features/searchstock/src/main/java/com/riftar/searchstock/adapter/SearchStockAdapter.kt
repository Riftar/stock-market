package com.riftar.searchstock.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.riftar.common.helper.convertToUSD
import com.riftar.common.helper.orZero
import com.riftar.domain.searchhistory.model.StockHistory
import com.riftar.searchstock.databinding.ItemSearchStockBinding


class SearchStockAdapter(private val listener: StockHistoryListener) :
    PagingDataAdapter<StockHistory, SearchStockAdapter.StockViewHolder>(diffCallback) {
    override fun onBindViewHolder(holder: StockViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StockViewHolder {
        return StockViewHolder(
            ItemSearchStockBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            ),
            listener
        )
    }

    inner class StockViewHolder(
        private val binding: ItemSearchStockBinding,
        private val listener: StockHistoryListener
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: StockHistory?) {
            with(binding) {
                tvStockCode.text = item?.symbol
                tvCompanyName.text = item?.shortName
                tvRegularPrice.text = item?.close.convertToUSD()
                tvPercentage.text = item?.percentageChange.convertToUSD()
                if (item?.percentageChange.orZero() < 0) {
                    tvPercentage.setTextColor(tvPercentage.context.getColor(com.riftar.common.R.color.red_loss))
                } else {
                    tvPercentage.setTextColor(tvPercentage.context.getColor(com.riftar.common.R.color.green_profit))
                }
                root.setOnClickListener {
                    listener.onItemClicked(item)
                }
            }
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<StockHistory>() {

            override fun areItemsTheSame(oldItem: StockHistory, newItem: StockHistory): Boolean {
                return oldItem.symbol == newItem.symbol
            }

            override fun areContentsTheSame(oldItem: StockHistory, newItem: StockHistory): Boolean {
                return oldItem == newItem
            }
        }
    }
}

interface StockHistoryListener {
    fun onItemClicked(item: StockHistory?)
}