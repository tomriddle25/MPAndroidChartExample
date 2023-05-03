package com.example.mpandroidchartexample.foldlinechart

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.util.AttributeSet
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.MotionEvent
import android.view.SoundEffectConstants
import android.widget.LinearLayout
import com.example.mpandroidchartexample.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.ChartTouchListener
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF

/**
 * LineChart wrapper view with customisations
 * */
class FoldLineChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : LinearLayout(context, attrs), OnChartGestureListener, OnChartValueSelectedListener {

    companion object {
        private const val TAG = "FoldLineChartView"
    }

    private var chart: LineChart

    private var chartData: List<List<Entry>> = emptyList()


    /**
     * Configurable properties
     * */

    var lineColors: List<Int>? = null
        set(value) {
            field = value
            updateChart()
        }

    var labelList: List<String>? = null
        set(value) {
            field = value
            updateChart()
        }

    var lineChartMode = LineDataSet.Mode.LINEAR
        set(value) {
            field = value
            updateChart()
        }

    var lineWidthDp = 4f
        set(value) {
            field = value
            updateChart()
        }

    var pointCircleRadiusDp = 8f
        set(value) {
            field = value
            updateChart()
        }

    var pointCircleHoleRadiusDp = 4f
        set(value) {
            field = value
            updateChart()
        }

    var drawGridLines = false
        set(value) {
            field = value
            updateChart()
        }

    var gridLineWidthDp = 2f
        set(value) {
            field = value
            updateChart()
        }

    var gridLineColor = Color.LTGRAY
        set(value) {
            field = value
            updateChart()
        }

    var labelTypeface = Typeface.DEFAULT
        set(value) {
            field = value
            updateChart()
        }

    var labelTextColor = Color.BLACK
        set(value) {
            field = value
            updateChart()
        }

    var labelSizeSp = 12f
        set(value) {
            field = value
            updateChart()
        }

    var chartHighlightChangedListener: ChartHighlightChangedListener? = null

    /*
    * Private properties
    * */

    private var maxVisiblePoints = 7
    private var visibleIndex: Int = 0
    private var currentHighlight: Highlight? = null

    init {
        inflate(context, R.layout.view_fold_line_chart, this)
        chart = findViewById(R.id.lineChart)
    }

    fun setChartData(data: List<List<Float>>) {
        chartData = data.mapIndexed { index, list ->
            list.mapIndexed { index2, value ->
                Entry(index2.toFloat(), value)
            }
        }
        // update chart
        updateChart()
    }

    private fun updateChart() {
        val lineDataSets = chartData.mapIndexed { index, list ->
            LineDataSet(list, "$index").apply {
                color = lineColors?.get(index) ?: Color.BLACK
                lineWidth = lineWidthDp
                setDrawCircles(true)
                mode = lineChartMode
                setCircleColor(lineColors?.get(index) ?: Color.BLACK)
                setDrawValues(false)
                highlightLineWidth = lineWidth
                highLightColor = Color.TRANSPARENT
                setDrawHorizontalHighlightIndicator(false)
                setDrawVerticalHighlightIndicator(true)
                isHighlightEnabled = true
                circleRadius = pointCircleRadiusDp
                circleHoleColor = Color.WHITE
                circleHoleRadius = pointCircleHoleRadiusDp
            }
        }
        chart.data = LineData(lineDataSets)
        formatXAxis()
        formatVerticalAxes()
        formatChart()
        chart.invalidate()
    }

    private fun formatChart() {
        chart.apply {
            description.isEnabled = false
            legend.isEnabled = false
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false
            // zoom in to chart
            setVisibleXRangeMaximum(maxVisiblePoints.toFloat())
            isDragXEnabled = true
            isDragYEnabled = false
            isScaleXEnabled = false
            isScaleYEnabled = false
            isDoubleTapToZoomEnabled = false
            isHighlightPerDragEnabled = true
            setOnChartValueSelectedListener(this@FoldLineChartView)
            onChartGestureListener = this@FoldLineChartView
            // drag
            isDragDecelerationEnabled = false

            extraBottomOffset = 16f

            marker = FoldLineChartMarker(this)

            if (highlighted == null || highlighted?.isEmpty() == true) {
                highlightValue(0f, 0)
            }
        }
    }

    private fun formatXAxis() {
        chart.xAxis.apply {
            labelList?.let {
                valueFormatter = FoldChartXAxisValueFormatter(it)
            }
            position = XAxis.XAxisPosition.BOTTOM
            setDrawGridLines(drawGridLines)
            gridColor = gridLineColor
            gridLineWidth = gridLineWidthDp
            setDrawLabels(true)
            textSize = labelSizeSp
            typeface = labelTypeface
            textColor = labelTextColor
            axisMaximum = labelList?.size?.toFloat() ?: 0f
            axisMinimum = -maxVisiblePoints / 2f
//                setLabelCount(12, false)
            setDrawAxisLine(false)
            // todo typeface textsize and text color
        }
    }

    private fun formatVerticalAxes() {
        chart.apply {
            axisLeft.isEnabled = false
            axisRight.apply {
                isEnabled = true
                gridLineWidth = 2f
                gridColor = gridLineColor
                setDrawGridLines(drawGridLines)
                setDrawAxisLine(false)
                setDrawLabels(false)
            }
        }
    }


    private fun performVibration() {
        chart.performHapticFeedback(
            HapticFeedbackConstants.CLOCK_TICK,
            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
        )
    }

    private fun playTickSound() {
        chart.playSoundEffect(SoundEffectConstants.CLICK)
    }


    /*
    * Overrides for listeners
    * */


    /**
     * [OnChartGestureListener] overrides
     * */
    override fun onChartGestureStart(
        me: MotionEvent?,
        lastPerformedGesture: ChartTouchListener.ChartGesture?
    ) {
    }

    override fun onChartGestureEnd(
        me: MotionEvent?,
        lastPerformedGesture: ChartTouchListener.ChartGesture?
    ) {
        Log.d(
            TAG,
            "onChartGestureEnd() called with: me = $me, lastPerformedGesture = $lastPerformedGesture"
        )
        val goToPoint =
            when (lastPerformedGesture) {
                // go to touched point if tapped or long press
                ChartTouchListener.ChartGesture.LONG_PRESS, ChartTouchListener.ChartGesture.SINGLE_TAP -> MPPointF(
                    me?.x ?: 0f,
                    me?.y ?: 0f
                )
                // go to center of view if drag is ending
                ChartTouchListener.ChartGesture.DRAG -> chart.centerOfView
                else -> chart.centerOfView
            }
        val nextHightlightPoint = chart.getHighlightByTouchPoint(goToPoint.x, goToPoint.y)
        // settle chart on nearest point
        if (nextHightlightPoint != null) {
            chart.highlightValue(nextHightlightPoint, true)
            visibleIndex = nextHightlightPoint.x.toInt()
        }
        // scroll chart to nearest point
        if (nextHightlightPoint != null) {
            chart.centerViewToAnimated(
                nextHightlightPoint.x,
                nextHightlightPoint.y,
                chart.data.getDataSetByIndex(nextHightlightPoint.dataSetIndex).axisDependency,
                200
            )
        }
    }

    override fun onChartLongPressed(me: MotionEvent?) {
    }

    override fun onChartDoubleTapped(me: MotionEvent?) {
    }

    override fun onChartSingleTapped(me: MotionEvent?) {
    }

    override fun onChartFling(
        me1: MotionEvent?,
        me2: MotionEvent?,
        velocityX: Float,
        velocityY: Float
    ) {
    }

    override fun onChartScale(me: MotionEvent?, scaleX: Float, scaleY: Float) {
    }

    override fun onChartTranslate(me: MotionEvent?, dX: Float, dY: Float) {
        val midPoint = chart.centerOfView
        val nearestHighlightPoint =
            chart.getHighlightByTouchPoint(midPoint.x, midPoint.y) ?: return
        if (visibleIndex != nearestHighlightPoint.x.toInt()) {
            visibleIndex = nearestHighlightPoint.x.toInt()
            chart.highlightValue(nearestHighlightPoint, true)
        }
    }

    /**
     * [OnChartValueSelectedListener] overrides
     * */
    override fun onValueSelected(e: Entry?, h: Highlight?) {
        Log.d(TAG, "onValueSelected() called with: e = $e, h = $h")
        val entryIndex = e?.x?.toInt() ?: return
        if (currentHighlight != null) {
            performVibration()
            playTickSound()
        }

        val highlights = chart.data.dataSets.mapIndexedNotNull { index, iLineDataSet ->
            val entry = iLineDataSet.getEntryForIndex(entryIndex) ?: return@mapIndexedNotNull null
            Highlight(entry.x, entry.y, index)
        }
        chart.highlightValues(highlights.toTypedArray())
        currentHighlight = h
        chartHighlightChangedListener?.onHighlightChanged(entryIndex)
    }

    override fun onNothingSelected() {
        chart.highlightValue(currentHighlight, false)
    }

}

