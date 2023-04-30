//package com.example.mpandroidchartexample
//
//import android.content.Context
//import android.graphics.Canvas
//import android.view.LayoutInflater
//import android.view.View
//import android.widget.RelativeLayout
//import android.widget.TextView
//import com.github.mikephil.charting.charts.LineChart
//import com.github.mikephil.charting.components.IMarker
//import com.github.mikephil.charting.data.Entry
//import com.github.mikephil.charting.highlight.Highlight
//import com.github.mikephil.charting.utils.MPPointF
//
//
//class CenterMarkerView(context: Context, private val layoutResource: Int, val lineChart: LineChart) : RelativeLayout(context),
//    IMarker {
//
//    private lateinit var incomingLabel: TextView
//    private lateinit var investedLabel: TextView
//    private lateinit var outgoingLabel: TextView
//
//
//    init {
//        setUpLayoutResource()
//    }
//
//    private fun setUpLayoutResource() {
//        val inflated: View = LayoutInflater.from(context).inflate(layoutResource, this)
//        inflated.layoutParams =
//            LayoutParams(lineChart.measuredWidth, lineChart.measuredHeight)
//        inflated.measure(
//            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED),
//            MeasureSpec.makeMeasureSpec(0, MeasureSpec.UNSPECIFIED)
//        )
//        inflated.layout(0, 0, inflated.measuredWidth, inflated.measuredHeight)
//
//        incomingLabel = findViewById<TextView>(R.id.incoming)
//        investedLabel = findViewById<TextView>(R.id.invested)
//        outgoingLabel = findViewById<TextView>(R.id.outgoing)
//    }
//
//    override fun refreshContent(e: Entry?, highlight: Highlight?) {
//        incomingLabel.text = "${e?.y?.toInt()}"
//        investedLabel.text = "${e?.y?.toInt()}"
//        outgoingLabel.text = "${e?.y?.toInt()}"
//    }
//
//    override fun draw(canvas: Canvas?, posX: Float, posY: Float) {
//        // take offsets into consideration
////        val offset = getOffsetForDrawingAtPoint(posX, posY)
//        val width = measuredWidth
//        val height = measuredHeight
//        val chartWidth = lineChart?.width ?: 0
//        val saveId = canvas?.save()
//        canvas?.translate(chartWidth/2f - width/2f, 0f)
//        draw(canvas)
//        canvas?.restoreToCount(saveId!!)
//    }
//
//    override fun getOffset(): MPPointF {
//        // center the marker horizontally and vertically
//
//        return MPPointF(0f, 0f)
//    }
//
//    override fun getOffsetForDrawingAtPoint(posX: Float, posY: Float): MPPointF {
//        return offset
//    }
//}
