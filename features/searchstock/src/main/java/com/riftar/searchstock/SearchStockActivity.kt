package com.riftar.searchstock

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import com.riftar.common.view.NavigationConstants.SELECTED_STOCK_INTENT
import com.riftar.common.view.base.BaseActivity
import com.riftar.domain.searchhistory.model.StockHistory
import com.riftar.searchstock.adapter.SearchStockAdapter
import com.riftar.searchstock.adapter.StockHistoryListener
import com.riftar.searchstock.databinding.ActivitySearchStockBinding
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class SearchStockActivity : BaseActivity<ActivitySearchStockBinding>(), StockHistoryListener {
    private val viewModel: SearchStockViewModel by viewModel()
    private var adapter: SearchStockAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAdapter()
        initLoadStateListener()
        getData()
    }

    override fun getViewBinding() = ActivitySearchStockBinding.inflate(layoutInflater)

    private fun getData() {
        lifecycleScope.launch {
            viewModel.getSearchHistory().collect {
                adapter?.submitData(it)
            }
        }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.searchStockState.collectLatest {
                when (it) {
                    SearchStockState.Initial -> {}
                    is SearchStockState.EmptyLayoutVisibility -> setEmptyLayoutVisibility(it.isVisible)
                    is SearchStockState.Loading -> setLoadingBarVisibility(it.isLoading)
                    is SearchStockState.Error -> showErrorSnackBar(it.message)
                    is SearchStockState.Search -> sendResult(it.query)
                }
            }
        }
    }

    override fun initViewListener() {
        with(binding) {
            ivBack.setOnClickListener {
                finish()
            }
            etSearch.doAfterTextChanged {
                viewModel.setSearchQuery(it.toString())
            }
            btnSearch.setOnClickListener {
                val text = etSearch.text.toString()
                viewModel.onSearchClicked(text)
            }
        }
    }

    private fun initAdapter() {
        adapter = SearchStockAdapter(this)
        binding.rvStock.adapter = adapter
    }

    private fun initLoadStateListener() {
        adapter?.addLoadStateListener { loadState ->
            viewModel.setProgressBarVisibility(loadState.refresh is LoadState.Loading)
            viewModel.setEmptyViewVisibility(loadState, adapter?.itemCount)
        }
    }

    private fun setLoadingBarVisibility(isVisible: Boolean) {
        binding.progressBar.isVisible = isVisible
        binding.rvStock.isVisible = !isVisible
        binding.tvRecentSearch.isVisible = !isVisible
    }

    private fun setEmptyLayoutVisibility(isVisible: Boolean) {
        with(binding) {
            rvStock.isVisible = !isVisible
            tvRecentSearch.isVisible = !isVisible
            layoutEmpty.root.isVisible = isVisible
            if (isVisible) {
                val query = etSearch.text.toString().ifEmpty { "stock" }
                layoutEmpty.tvTitle.text =
                    getString(R.string.template_msg_empty, query)
            }
        }
    }

    override fun onItemClicked(item: StockHistory?) {
        sendResult(item?.symbol.orEmpty())
    }

    private fun sendResult(selectedStock: String) {
        val resultIntent = Intent()
        resultIntent.putExtra(SELECTED_STOCK_INTENT, selectedStock)
        setResult(Activity.RESULT_OK, resultIntent)
        finish()
    }
}