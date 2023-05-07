package com.example.mpandroidchartexample.foldlinechart

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import com.example.mpandroidchartexample.dpToPx
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class FoldLineChartMarker(private val lineChart: LineChart) : IMarker {

    private val paint = Paint().apply {
        color = Color.BLACK
        style = Paint.Style.FILL
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        Log.d(TAG, "refreshContent() called with: e = $e, highlight = $highlight")
        val dataSet = lineChart.data.getDataSetByIndex(highlight?.dataSetIndex ?: 0)
        val dataSetColor = dataSet.color
        paint.color = dataSetColor
    }

    override fun draw(canvas: Canvas?, posX: Float, posY: Float) {
        Log.d(TAG, "onDraw() called with: canvas = $canvas")
        canvas?.drawCircle(posX, posY, 8.dpToPx(), paint)
    }

    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
        Log.d(TAG, "getOffsetForDrawingAtPoint() called with: posX = $posX, posY = $posY")
        return MPPointF(0f, 0f)
    }

    override fun getOffset(): MPPointF {
        Log.d(TAG, "getOffset() called")
        return MPPointF(0f, 0f)
    }

    companion object {
        private const val TAG = "Marker"
    }
}