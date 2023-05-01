package com.example.mpandroidchartexample.foldlinechart

import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.formatter.ValueFormatter

class FoldChartXAxisValueFormatter(private val labels: List<String>) : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
        // todo verify behaviour if needed to produce repeating labels after the end of the list
        val index = value.toInt().rem(labels.size)
        return if(index in labels.indices) {
            labels[index]
        } else {
            ""
        }
    }
}