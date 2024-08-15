package com.riftar.stockchart.chart

import android.annotation.SuppressLint
import android.content.Context
import android.widget.TextView
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.riftar.stockchart.R

@SuppressLint("ViewConstructor")
class CustomMarkerView(context: Context?, layoutResource: Int) :
    MarkerView(context, layoutResource), IMarker {
    private val textView: TextView = findViewById<TextView>(R.id.tvContent)

    override fun refreshContent(e: Entry, highlight: Highlight) {
        textView.text = e.y.toString()
        super.refreshContent(e, highlight)
    }
}