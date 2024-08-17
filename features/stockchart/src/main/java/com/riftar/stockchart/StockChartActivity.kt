package com.riftar.stockchart

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.github.mikephil.charting.components.LimitLine
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
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
import com.riftar.stockchart.chart.ChartFormatter.dateFormatter
import com.riftar.stockchart.chart.ChartFormatter.dollarFormatter
import com.riftar.stockchart.chart.CustomMarkerView
import com.riftar.stockchart.databinding.ActivityStockChartBinding
import com.riftar.stockchart.search.SearchStockBottomSheet
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
//        viewModel.getStockChartData("AAPL", System.currentTimeMillis())
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
                    is StockChartState.Error -> {
                        showErrorSnackBar(it.message)
                    }

                    StockChartState.Loading -> {
                        //show loading
                    }

                    is StockChartState.Success -> {
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
                    SaveStockHistoryState.Success -> {
                        // todo delete
                        showSuccessSnackBar("Success insert to History")
                    }
                }
            }
        }
    }

    private fun onResultReceived(intent: Intent?) {
        intent?.getStringExtra(SELECTED_STOCK_INTENT)?.let {
            viewModel.getStockChartData(it, System.currentTimeMillis())
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
        }
    }

    private fun navigateToSearchActivity() {
        val intent = Intent(
            this@StockChartActivity,
            Class.forName(SEARCH_STOCK_ACTIVITY)
        )
        searchStockLauncher.launch(intent)
    }

    private fun openSearchBottomSheet() {
        SearchStockBottomSheet.newInstance { stockCode ->
            viewModel.getStockChartData(stockCode, System.currentTimeMillis())
        }.show(supportFragmentManager, "SearchStockBottomSheet")
    }

    private fun showChartData(chartResult: ChartResult) {

        val chartData = chartResult.timestamp.mapIndexed { index, i ->
            Entry(
                i.toFloat(),
                chartResult.indicators.quote.getOrNull(0)?.close?.getOrNull(0).orZero().toFloat()
            )
        }

        val lineData = createLineDataSet(chartData)
        setupChartLayout(chartResult)
        binding.layoutChart.chart.data = lineData
        binding.layoutChart.chart.notifyDataSetChanged()
        binding.layoutChart.chart.invalidate()
    }

    private fun setupChartLayout(chartResult: ChartResult) {
        val limit = createLimitLine(chartResult.indicators.quote.getOrNull(0)?.close?.last())
        binding.layoutChart.chart.apply {
            setBackgroundColor(
                ContextCompat.getColor(
                    this@StockChartActivity,
                    com.riftar.common.R.color.background_solid
                )
            )
            description.isEnabled = false
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            setDrawMarkers(false)
            legend.isEnabled = false
            axisLeft.apply {
                axisMinimum = chartResult.indicators.quote.getOrNull(0)?.close?.min()
                    .orZero().toFloat()
                setDrawGridLines(false)
                textSize = 12f
                labelCount = 8
                textColor = ContextCompat.getColor(
                    this@StockChartActivity,
                    com.riftar.common.R.color.text_color_primary
                )
                addLimitLine(limit)
                setDrawLimitLinesBehindData(true)
                valueFormatter = dollarFormatter
                spaceMin = 1f
                setClipValuesToContent(false)
            }
            axisRight.apply {
                isEnabled = false
                setDrawGridLines(false)
            }
            xAxis.apply {
                //disable grid line in background
                setDrawGridLines(false)
                position = XAxis.XAxisPosition.BOTTOM
                valueFormatter = dateFormatter
                labelCount = 6
                textSize = 12f
                textColor = ContextCompat.getColor(
                    this@StockChartActivity,
                    com.riftar.common.R.color.text_color_primary
                )
            }
            extraBottomOffset = 4f
            setDrawMarkers(true)
            marker = CustomMarkerView(this@StockChartActivity, R.layout.layout_marker_view)
        }
    }

    private fun createLimitLine(last: Double?): LimitLine {
        val limit = LimitLine(last.orZero().toFloat(), "")
        return limit.apply {
            lineWidth = 2f
            lineColor = ContextCompat.getColor(
                this@StockChartActivity,
                com.riftar.common.R.color.text_color_gray
            )
            enableDashedLine(10f, 10f, 0f)
        }
    }

    private fun createLineDataSet(chartData: List<Entry>): LineData {
        val lineDataSet = LineDataSet(chartData, "")
        lineDataSet.apply {
            mode = LineDataSet.Mode.HORIZONTAL_BEZIER
            color = ContextCompat.getColor(
                this@StockChartActivity,
                com.riftar.common.R.color.green_profit
            )
            highLightColor = ContextCompat.getColor(
                this@StockChartActivity,
                com.riftar.common.R.color.background_invert_gray
            )
            setDrawValues(false)
            setDrawCircles(false)
            lineWidth = 2f
            setDrawFilled(true)
            fillDrawable = AppCompatResources.getDrawable(
                this@StockChartActivity,
                R.drawable.bg_chart_green_gradient
            )
        }

        return LineData(lineDataSet)
    }
}