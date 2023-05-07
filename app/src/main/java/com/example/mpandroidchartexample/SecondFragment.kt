package com.example.mpandroidchartexample

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import com.example.mpandroidchartexample.databinding.FragmentSecondBinding
import com.example.mpandroidchartexample.foldlinechart.ChartHighlightChangedListener

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private var _binding: FragmentSecondBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSecond.setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
        binding.foldChartView.apply {
            setChartData(
                listOf(
                    listOf(
                        10000f,
                        6000f,
                        2000f,
                        12000f,
                        20000f,
                        5000f,
                        10000f,
                        6000f,
                        2000f,
                        12000f,
                        20000f,
                        5000f
                    ),
                    listOf(
                        25000f,
                        45000f,
                        40000f,
                        60000f,
                        10000f,
                        25000f,
                        25000f,
                        45000f,
                        40000f,
                        60000f,
                        10000f,
                        25000f
                    ),
                    listOf(
                        50000f,
                        20000f,
                        30000f,
                        40000f,
                        12000f,
                        27000f,
                        22000f,
                        35000f,
                        30000f,
                        50000f,
                        25000f,
                        25000f
                    ),
                )
            )



            lineColors = listOf(
                ContextCompat.getColor(requireContext(), R.color.green),
                ContextCompat.getColor(requireContext(), R.color.blue),
                ContextCompat.getColor(requireContext(), R.color.red)
            )

            initialHighlightIndex = 3f
            // set months March to April as label list
            labelList = listOf(
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep",
                "Oct",
                "Nov",
                "Dec",
                "Jan",
                "Feb",
                "Mar",
                "Apr",
                "May",
                "Jun",
                "Jul",
                "Aug",
                "Sep"
            )
            labelTextColor = Color.GRAY
            labelSizeSp = 12f

            // verttical line
            drawStaticCenteredVerticalLine = true

            chartHighlightChangedListener = object : ChartHighlightChangedListener {
                override fun onHighlightChanged(index: Int) {
                    Log.d("TAG", "onHighlightChanged() called with: index = $index")
                }
            }
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}