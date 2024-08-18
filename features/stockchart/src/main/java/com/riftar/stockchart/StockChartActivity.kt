package com.riftar.stockchart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.core.view.children
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.data.Entry
import com.riftar.common.helper.convertToUSD
import com.riftar.common.helper.formatNumber
import com.riftar.common.helper.getPercentageColor
import com.riftar.common.helper.orZero
import com.riftar.common.helper.roundTwoDecimal
import com.riftar.common.view.NavigationConstants.SEARCH_STOCK_ACTIVITY
import com.riftar.common.view.NavigationConstants.SELECTED_STOCK_INTENT
import com.riftar.common.view.base.BaseActivity
import com.riftar.domain.searchhistory.mapper.calculateGainOrLoss
import com.riftar.domain.searchhistory.mapper.calculatePercentageChange
import com.riftar.domain.stockchart.model.ChartResult
import com.riftar.stockchart.chart.ChartHelper.createLineDataSet
import com.riftar.stockchart.chart.ChartHelper.setupChartLayout
import com.riftar.stockchart.databinding.ActivityStockChartBinding
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel


class StockChartActivity : BaseActivity<ActivityStockChartBinding>() {
    private val viewModel: StockChartViewModel by viewModel()
    private val searchStockLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == Activity.RESULT_OK) {
                val intent = result.data
                onResultReceived(intent)
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }

    override fun getViewBinding() = ActivityStockChartBinding.inflate(layoutInflater)

    override fun initViewListener() {
        with(binding) {
            etSearch.setOnClickListener {
                navigateToSearchActivity()
            }
        }
    }

    override fun observeViewModel() {
        lifecycleScope.launch {
            viewModel.stockChartState.collect {
                when (it) {
                    StockChartState.Initial -> {
                        showInitialLayout()
                    }

                    is StockChartState.Error -> {
                        binding.layoutLoading.root.visibility = View.GONE
                        showErrorSnackBar(it.message)
                    }

                    StockChartState.Loading -> {
                        binding.layoutLoading.root.visibility = View.VISIBLE
                    }

                    is StockChartState.Success -> {
                        showSuccessLayout()
                        showStockData(it.chartResult)
                        showChartData(it.chartResult)
                    }
                }
            }
        }
        lifecycleScope.launch {
            viewModel.saveHistoryState.collect {
                when (it) {
                    is SaveStockHistoryState.Error -> {
                        showErrorSnackBar(it.message)
                    }

                    SaveStockHistoryState.Initial -> {}
                }
            }
        }
    }

    private fun showSuccessLayout() {
        with(binding) {
            layoutStarter.root.visibility = View.GONE
            svMain.visibility = View.VISIBLE
            layoutLoading.root.visibility = View.GONE
        }
    }

    private fun showInitialLayout() {
        with(binding) {
            layoutStarter.root.visibility = View.VISIBLE
            svMain.visibility = View.GONE
            layoutLoading.root.visibility = View.GONE
        }
    }

    private fun onResultReceived(intent: Intent?) {
        intent?.getStringExtra(SELECTED_STOCK_INTENT)?.let {
            viewModel.setStockCode(it, System.currentTimeMillis())
        }
    }

    private fun showStockData(chartResult: ChartResult) {
        with(binding) {
            tvStockCode.text = chartResult.meta.symbol
            tvCompanyName.text = chartResult.meta.shortName
            tvRegularPrice.text = chartResult.meta.regularMarketPrice.convertToUSD()
            tvPercentage.text = "${
                chartResult.calculatePercentageChange().roundTwoDecimal()
            }% (${chartResult.calculateGainOrLoss().convertToUSD()})"
            val color = getPercentageColor(
                chartResult.meta.regularMarketPrice,
                chartResult.meta.previousClose
            )
            tvPercentage.setTextColor(ContextCompat.getColor(this@StockChartActivity, color))

            tvPrevClose.text = chartResult.meta.previousClose.convertToUSD()
            tvOpen.text =
                chartResult.indicators.quote.getOrNull(0)?.open?.getOrNull(0).convertToUSD()
            tvDayHigh.text = chartResult.meta.regularMarketDayHigh.convertToUSD()
            tvDayLow.text = chartResult.meta.regularMarketDayLow.convertToUSD()
            tvVolume.text = chartResult.meta.regularMarketVolume.formatNumber()
            tv52High.text = chartResult.meta.fiftyTwoWeekHigh.convertToUSD()
            tv52Low.text = chartResult.meta.fiftyTwoWeekLow.convertToUSD()

            setButtonListener()
        }
    }

    private fun setButtonListener() {
        binding.layoutButtonPeriod.children.forEach { btn ->
            btn.setOnClickListener {
                viewModel.setPeriodState(btn.tag.toString())
            }
        }
    }

    private fun navigateToSearchActivity() {
        val intent = Intent(
            this@StockChartActivity,
            Class.forName(SEARCH_STOCK_ACTIVITY)
        )
        searchStockLauncher.launch(intent)
    }

    private fun showChartData(chartResult: ChartResult) {

        val chartData = chartResult.timestamp.mapIndexed { index, i ->
            Entry(
                i.toFloat(),
                chartResult.indicators.quote.getOrNull(0)?.close?.getOrNull(0).orZero().toFloat()
            )
        }

        with(binding.layoutChart) {
            val lineData = createLineDataSet(this@StockChartActivity, chartData)
            chart.setupChartLayout(chartResult)
            chart.data = lineData
            chart.notifyDataSetChanged()
            chart.invalidate()
        }
    }
}