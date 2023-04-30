package com.example.mpandroidchartexample

import android.content.Context
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.drawable.Drawable
import android.media.SoundPool
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.util.Log
import android.view.HapticFeedbackConstants
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.SoundEffectConstants
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.mpandroidchartexample.databinding.FragmentFirstBinding
import com.github.mikephil.charting.charts.Chart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.IMarker
import com.github.mikephil.charting.components.MarkerImage
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.ChartTouchListener.ChartGesture
import com.github.mikephil.charting.listener.OnChartGestureListener
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.MPPointF

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment(), OnChartValueSelectedListener, OnChartGestureListener {

    private var _binding: FragmentFirstBinding? = null
    private val TAG = "FirstFragment"

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    // colors for the line chart
    private lateinit var cashFlowColors: List<Int>

    private var visibleIndex: Int = 0
    private var currentHighlight: Highlight? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        cashFlowColors = listOf(
            ContextCompat.getColor(requireContext(), R.color.green),
            ContextCompat.getColor(requireContext(), R.color.blue),
            ContextCompat.getColor(requireContext(), R.color.red)
        )
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonFirst.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }

        binding.lineChart.apply {
            // setup data for line chart
            val data = listOf(
                listOf(
                    345349,
                    266877,
                    235758,
                    45125,
                    260545,
                    496150,
                    1659196,
                    1091197,
                    918093,
                    554032,
                    13019,
                    1075405,
                    720
                ),
                listOf(0, 0, 10000, 20000, 0, 7390, 100, 0, 0, 0, 0, 0, 7390),
                listOf(
                    334907,
                    223007,
                    154814,
                    175303,
                    257723,
                    494282,
                    1610962,
                    1008147,
                    1005444,
                    58466,
                    535459,
                    622535,
                    300751
                )
            )

//            val entries = data.map { it.mapIndexed { index, value -> Entry(index.toFloat(), value.toFloat()) } }

            val entries: List<List<Entry>> =
                listOf(
                    listOf(
                        Entry(0f, 10000f),
                        Entry(1f, 6000f),
                        Entry(2f, 2000f),
                        Entry(3f, 12000f),
                        Entry(4f, 20000f),
                        Entry(5f, 5000f),
                        Entry(6f, 10000f),
                        Entry(7f, 6000f),
                        Entry(8f, 2000f),
                        Entry(9f, 12000f),
                        Entry(10f, 20000f),
                        Entry(11f, 5000f)
                    ),
                    listOf(
                        Entry(0f, 25000f),
                        Entry(1f, 45000f),
                        Entry(2f, 40000f),
                        Entry(3f, 60000f),
                        Entry(4f, 10000f),
                        Entry(5f, 25000f),
                        Entry(6f, 25000f),
                        Entry(7f, 45000f),
                        Entry(8f, 40000f),
                        Entry(9f, 60000f),
                        Entry(10f, 10000f),
                        Entry(11f, 25000f)
                    ),
                    listOf(
                        Entry(0f, 50000f),
                        Entry(1f, 20000f),
                        Entry(2f, 30000f),
                        Entry(3f, 40000f),
                        Entry(4f, 12000f),
                        Entry(5f, 27000f),
                        Entry(6f, 22000f),
                        Entry(7f, 35000f),
                        Entry(8f, 30000f),
                        Entry(9f, 50000f),
                        Entry(10f, 25000f),
                        Entry(11f, 25000f)
                    )
                )

            val lineDataSets = entries.mapIndexed { index, entryList ->
                LineDataSet(entryList, "Line $index").apply {
                    setCircleColor(cashFlowColors[index])
                    setDrawValues(false)
                    setColors(cashFlowColors[index])
                    mode = LineDataSet.Mode.LINEAR
                    highlightLineWidth = 2f
                    highLightColor = Color.TRANSPARENT
                    setDrawHorizontalHighlightIndicator(false)
                    setDrawVerticalHighlightIndicator(true)
                    isHighlightEnabled = true
                    circleRadius = 8f
                    circleHoleColor = Color.WHITE
                    circleHoleRadius = 4f
                    lineWidth = 4f
//                    when(index) {
//                        0 -> {
//                            fillColor = Color.parseColor("#66FF0000")
//                            setCircleColor(Color.parseColor("#FF0000"))
//                            setColors(Color.parseColor("#FF0000"))
//                        }
//                        1 -> {
//                            fillColor = Color.parseColor("#660000FF")
//                            setCircleColor(Color.parseColor("#0000FF"))
//                            setColors(Color.parseColor("#0000FF"))
//                        }
//                        2 -> {
//                            fillColor = Color.parseColor("#6600FF00")
//                            setCircleColor(Color.parseColor("#00FF00"))
//                            setColors(Color.parseColor("#00FF00"))
//                        }
//                    }
                }
            }

            // setup line chart
            val lineData = LineData(lineDataSets)
            this.data = lineData
            xAxis.apply {
                valueFormatter = MonthValueFormatter()
                gridLineWidth = 0f
                position = XAxis.XAxisPosition.BOTTOM
                setDrawGridLines(true)
                setDrawAxisLine(true)
                setDrawLabels(true)
                axisMaximum = 18f
                axisMinimum = -3.5f
//                setLabelCount(12, false)
            }
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            axisRight.setDrawLabels(false)
            description.isEnabled = false
            legend.isEnabled = false
            setPinchZoom(false)
            isDoubleTapToZoomEnabled = false

            // zoom in to chart
            setVisibleXRangeMaximum(7f)
            isDragXEnabled = true
            isDragYEnabled = false
            isScaleXEnabled = false
            isScaleYEnabled = false
            isDoubleTapToZoomEnabled = false
            isHighlightPerDragEnabled = true
//            setDrawGridBackground(true)
//            setDrawBorders(true)
//            setExtraOffsets(
//                0f,
//                resources.getDimensionPixelSize(R.dimen.chart_padding_top).toFloat(),
//                0f,
//                resources.getDimensionPixelSize(R.dimen.chart_padding_bottom).toFloat()
//            )

            setOnChartValueSelectedListener(this@FirstFragment)
            onChartGestureListener = this@FirstFragment

            // drag
            isDragDecelerationEnabled = true
            dragDecelerationFrictionCoef = 0.3f

            marker = Marker(this)

            invalidate()
            if (highlighted == null || highlighted?.isEmpty() == true) {
                highlightValue(0f, 0)
            }
        }
    }

    private fun performVibration() {
        binding.lineChart.performHapticFeedback(
            HapticFeedbackConstants.CLOCK_TICK,
            HapticFeedbackConstants.FLAG_IGNORE_GLOBAL_SETTING
        )
    }

    private fun playTickSound() {
        binding.lineChart.playSoundEffect(SoundEffectConstants.CLICK)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

        val chartDataSets = binding.lineChart.data.dataSets ?: return
        val incomingDataSet = chartDataSets[0]
        val investedDataSet = chartDataSets[1]
        val outgoingDataSet = chartDataSets[2]

        val index = e?.x?.toInt() ?: return

        if (currentHighlight != null) {
            performVibration()
            playTickSound()
        }
        val highlights = mutableListOf<Highlight>()
        val outgoingEntry = outgoingDataSet.getEntryForIndex(index)
        if (outgoingEntry != null) {
            highlights.add(Highlight(outgoingEntry.x, outgoingEntry.y, 2))
        }
        val incomingEntry = incomingDataSet.getEntryForIndex(index)
        if (incomingEntry != null) {
            highlights.add(Highlight(incomingEntry.x, incomingEntry.y, 0))
        }
        val investedEntry = investedDataSet.getEntryForIndex(index)
        if (investedEntry != null) {
            highlights.add(Highlight(investedEntry.x, investedEntry.y, 1))
        }
        binding.lineChart.highlightValues(highlights.toTypedArray())
        currentHighlight = h
    }

    override fun onNothingSelected() {

    }

    override fun onChartGestureStart(me: MotionEvent?, lastPerformedGesture: ChartGesture?) {
        Log.d(
            TAG,
            "onChartGestureStart() called with: me = $me, lastPerformedGesture = $lastPerformedGesture"
        )
    }

    override fun onChartGestureEnd(me: MotionEvent?, lastPerformedGesture: ChartGesture?) {
        Log.d(
            TAG,
            "onChartGestureEnd() called with: me = $me, lastPerformedGesture = $lastPerformedGesture"
        )
        //mid point of the chart
        val midPoint = binding.lineChart.centerOfView
        val nextHightlightPoint = binding.lineChart.getHighlightByTouchPoint(midPoint.x, midPoint.y)
        // settle chart on nearest point
        if (nextHightlightPoint != null) {
            binding.lineChart.highlightValue(nextHightlightPoint, true)
        }
        // scroll chart to nearest point
        if (nextHightlightPoint != null) {
            binding.lineChart.centerViewToAnimated(
                nextHightlightPoint.x,
                nextHightlightPoint.y,
                binding.lineChart.data.getDataSetByIndex(nextHightlightPoint.dataSetIndex).axisDependency,
                300
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
        Log.d(TAG, "onChartTranslate() called with: me = $me, dX = $dX, dY = $dY")
        val midPoint = binding.lineChart.centerOfView
        val nearestHighlightPoint =
            binding.lineChart.getHighlightByTouchPoint(midPoint.x, midPoint.y) ?: return
        if (visibleIndex != nearestHighlightPoint.x.toInt()) {
            visibleIndex = nearestHighlightPoint.x.toInt()
            binding.lineChart.highlightValue(nearestHighlightPoint, true)
        }
    }
}

// create value formatter for x axis for month names from march to april
class MonthValueFormatter : ValueFormatter() {
    override fun getAxisLabel(value: Float, axis: AxisBase?): String {
//        return when (value.toInt().rem(12)) {
//            0 -> "Jan"
//            1 -> "Feb"
//            2 -> "Mar"
//            3 -> "Apr"
//            4 -> "May"
//            5 -> "Jun"
//            6 -> "Jul"
//            7 -> "Aug"
//            8 -> "Sep"
//            9 -> "Oct"
//            10 -> "Nov"
//            11 -> "Dec"
//            else -> ""
//        }
        return when (value.toInt().rem(12)) {
            0 -> "Mar"
            1 -> "Apr"
            2 -> "May"
            3 -> "Jun"
            4 -> "Jul"
            5 -> "Aug"
            6 -> "Sep"
            7 -> "Oct"
            8 -> "Nov"
            9 -> "Dec"
            10 -> "Jan"
            11 -> "Feb"
            else -> ""
        }
    }
}

class Marker(private val lineChart: LineChart) : IMarker {

    private var colorToPaint: Int = Color.BLACK
    private var currentX = -1f
    private var isLineDrawnForCurrentX = false
    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        Log.d(Companion.TAG, "refreshContent() called with: e = $e, highlight = $highlight")
        val dataSet = lineChart.data.getDataSetByIndex(highlight?.dataSetIndex ?: 0)
        val dataSetColor = dataSet.color
        colorToPaint = dataSetColor
        if(currentX != e?.x) {
            isLineDrawnForCurrentX = false
        }
        currentX = e?.x ?: -1f
    }

    override fun draw(canvas: Canvas?, posX: Float, posY: Float) {
        Log.d(TAG, "onDraw() called with: canvas = $canvas")
        // draw a line at the center of the chart

        if(!isLineDrawnForCurrentX || posX != (canvas?.clipBounds?.right ?: (0f / 2f))) {
            isLineDrawnForCurrentX = true
            canvas?.drawLine(
                canvas.clipBounds.right/2f,
                0f,
                canvas.clipBounds.right/2f,
                lineChart.height.toFloat(),
                Paint().apply {
                    color = Color.GRAY
                    strokeWidth = 2.dpToPx()
                }
            )
        }

        val paint = Paint()
        paint.color = colorToPaint
        paint.style = Paint.Style.FILL
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

data class DrawCircle(
    val x: Float,
    val y: Float,
    val radius: Float,
    val color: Int
)

fun Int.dpToPx(): Float {
    val displayMetrics = Resources.getSystem().displayMetrics
    return (this * displayMetrics.density + 0.5f)
}


