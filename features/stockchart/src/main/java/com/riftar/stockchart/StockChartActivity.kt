package com.riftar.stockchart

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class StockChartActivity : AppCompatActivity() {
    private val viewModel: StockChartViewModel by viewModel()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_stock_chart)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val btn = findViewById<Button>(R.id.btnSearch)
        btn.setOnClickListener {
            val etInput = findViewById<EditText>(R.id.etInput)
            viewModel.getStockChartData(etInput.text.toString())
        }
        lifecycleScope.launch {
            val tvResult = findViewById<TextView>(R.id.tvResult)
            viewModel.stockChartState.collect {
                when (it) {
                    is StockChartState.Error -> {

                        tvResult.text ="Error ${it.message}"
                    }

                    StockChartState.Loading -> {

                        tvResult.text = "Loading"
                    }

                    is StockChartState.Success -> {

                        tvResult.text = it.charResult.toString()
                    }
                }
            }
        }
    }
}