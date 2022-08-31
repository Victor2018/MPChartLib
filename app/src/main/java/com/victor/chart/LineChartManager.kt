package com.victor.chart

import android.graphics.Color
import android.util.Log
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.components.Description
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.components.YAxis
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.hok.lib.common.view.widget.linechart.LineChartXAxisRenderer


/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LineChartManager
 * Author: Victor
 * Date: 2022/8/23 11:29
 * Description: 
 * -----------------------------------------------------------------
 */

class LineChartManager(var lineChart: LineChart?) {
    private val TAG = "LineChartManager"
    private var leftAxis: YAxis? = null
    private var rightAxis: YAxis? = null
    private var xAxis: XAxis? = null
    private var xTitles: ArrayList<String> = ArrayList()

    init {
        leftAxis = lineChart?.axisLeft
        rightAxis = lineChart?.axisRight
        xAxis = lineChart?.xAxis

        initChart()
    }

    /**
     * 初始化LineChar
     */
    private fun initChart() {
        //取消描述
        var description = Description()
        description.isEnabled = false
        lineChart?.description = description

        //无数据时显示的文本内容
        lineChart?.setNoDataText("无数据")
        //是否展示网格线
        lineChart?.setDrawGridBackground(false)
        //是否显示边界
        lineChart?.setDrawBorders(false)

        lineChart?.extraBottomOffset = 2 * 10f

        lineChart?.setXAxisRenderer(
            LineChartXAxisRenderer(lineChart?.viewPortHandler,
                lineChart?.xAxis,lineChart?.getTransformer(YAxis.AxisDependency.LEFT))
        )

        //折线图例 标签 设置
        val legend = lineChart?.legend
        legend?.form = Legend.LegendForm.LINE
        legend?.formLineWidth = 2f // 高度
        legend?.formSize = 16f // 宽度
        legend?.textSize = 11f

        //显示位置
        legend?.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend?.horizontalAlignment = Legend.LegendHorizontalAlignment.CENTER
        legend?.orientation = Legend.LegendOrientation.HORIZONTAL
        legend?.setDrawInside(false)

        legend?.isEnabled = false//禁用图例

        //X轴设置显示位置在底部
        xAxis?.position = XAxis.XAxisPosition.BOTTOM
        xAxis?.granularity = 1f//x轴最小显示间隔
//        xAxis?.labelCount = 10//设置x轴的标签数
        xAxis?.axisLineColor = Color.TRANSPARENT
        xAxis?.setDrawAxisLine(false)
        xAxis?.setDrawGridLines(false)//取消x轴网格线
        xAxis?.setDrawLabels(true)//X轴刻度

        //保证Y轴从0开始，不然会上移一点
        leftAxis?.axisMinimum = 0f
        rightAxis?.axisMinimum = 0f

        leftAxis?.setDrawAxisLine(false)
        rightAxis?.setDrawAxisLine(false)

        rightAxis?.isEnabled = false////取消右侧y轴显示

        leftAxis?.textColor = Color.parseColor("#333333")
        leftAxis?.textSize = 12f

        //X轴绘制刻度，Y轴不绘制刻度
        xAxis?.setLabelCount(6, false)
        //设置是否绘制刻度
        xAxis?.isDrawScale = true
        leftAxis?.isDrawScale = false

       /* //XY轴都显示刻度
        leftAxis?.setDrawAxisLine(true)
        xAxis?.setLabelCount(6, true)
        //设置是否绘制刻度
        xAxis?.isDrawScale = true
        leftAxis?.isDrawScale = true
*/
//        leftAxis?.setDrawZeroLine(true); // draw a zero line
//        leftAxis?.zeroLineColor = Color.GRAY
//        leftAxis?.zeroLineWidth = 1f

    }

    fun showDatas (lineNames: ArrayList<String>,lineColors: ArrayList<Int>,xValues: ArrayList<Int>,
                   yValues: ArrayList<ArrayList<Float>>,xTitleValues: ArrayList<String>) {
        this.xTitles = xTitleValues
        setXAxisValueFormatter()
        var lineNamesSize = lineNames.size
        var lineColorsSize = lineColors.size

        if (lineNamesSize != lineColorsSize) {
            Log.e(TAG,"showDatas-数据格式错误")
            return
        }

        val dataSets = ArrayList<ILineDataSet>()
        lineNames.forEachIndexed { i, s ->

            var values = ArrayList<Entry>()

            yValues[i].forEachIndexed { j, fl ->
                values.add(Entry(xValues[j].toFloat(), fl))
            }

            var lineDataSet = getLineDataSet(values,lineNames[i],lineColors[i])
            dataSets.add(lineDataSet)
        }

        lineChart?.data = LineData(dataSets)
        lineChart?.invalidate()

        lineChart?.animateX(1000)
    }

    fun getLineDataSet(values: ArrayList<Entry>,name: String?,color: Int): LineDataSet {
        var lineDataSet = LineDataSet(values, name)
        //设置折线的粗细
        lineDataSet?.lineWidth = 1.5f
        //设置折线上的圆点的大小
        lineDataSet?.circleRadius = 1.5f
        //设置折线的颜色
        lineDataSet?.color = color
        //设置曲线填充
        lineDataSet?.setDrawFilled(false)
        //设置节点是否显示具体值
        lineDataSet?.setDrawValues(values.size == 1)
        lineDataSet?.setDrawCircles(values.size == 1)
        //设置折线上的圆点是否是空心
        lineDataSet?.setDrawCircleHole(values.size > 1)
        //设置折线上的圆点颜色
        lineDataSet?.setCircleColor(color)
        lineDataSet?.highLightColor = color
        lineDataSet?.mode = LineDataSet.Mode.HORIZONTAL_BEZIER
        lineDataSet?.axisDependency = YAxis.AxisDependency.LEFT
        lineDataSet?.valueTextSize = 10f
        //取消横向辅助线
        lineDataSet?.setDrawHighlightIndicators(false)
        //取消纵向辅助线
        lineDataSet?.setDrawVerticalHighlightIndicator(false)

        return lineDataSet
    }

    fun setXAxisValueFormatter () {
        xAxis?.valueFormatter = LineChartXAxisFormatter(xTitles)
    }

    fun clearData () {
        xTitles?.clear()
    }

}