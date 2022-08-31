package com.victor.chart

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    var mLineChartManager: LineChartManager? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mLineChartManager = LineChartManager(mLineChart)
        mLineChartManager?.clearData()

        var xTitles = ArrayList<String>()
        var xValues = ArrayList<Int>()
        var yValues = ArrayList<ArrayList<Float>>()
        var y1Values = ArrayList<Float>()
        var y2Values = ArrayList<Float>()

        for (i in 0 until 20) {
            xValues.add(i + 1)
            xTitles.add("08-${20 + i}星期天")
            var totalFindCount = Random(100 - 10 * i).nextFloat()
            var totalAddCount = Random(1000 - 100 * i).nextFloat()

            y1Values.add(totalFindCount)
            y2Values.add(totalAddCount)
        }

        var lineNames = ArrayList<String>()
        lineNames.add("寻找案例次数")
        lineNames.add("新增案例数")

        var lineColors = ArrayList<Int>()
        lineColors.add(Color.BLUE)
        lineColors.add(Color.YELLOW)

        yValues.add(y1Values)
        yValues.add(y2Values)

        mLineChartManager?.showDatas(lineNames,lineColors,xValues,yValues,xTitles)
    }
}