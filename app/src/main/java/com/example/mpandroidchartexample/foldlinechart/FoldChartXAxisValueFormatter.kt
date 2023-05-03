package com.example.mpandroidchartexample.foldlinechart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.IAxisValueFormatter

class FoldChartXAxisValueFormatter(private val labels: List<String>) : IAxisValueFormatter {
    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        val index = value.toInt().rem(labels.size)
        return if(index in labels.indices) {
            labels[index]
        } else {
            ""
        }
    }
}