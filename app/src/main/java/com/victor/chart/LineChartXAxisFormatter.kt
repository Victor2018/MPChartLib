package com.victor.chart

import android.util.Log
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import com.github.mikephil.charting.formatter.IValueFormatter
import com.github.mikephil.charting.utils.ViewPortHandler

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LineBarXAxisFormatter
 * Author: Victor
 * Date: 2022/8/31 14:48
 * Description: x轴添加换行符为LineChartXAxisRenderer中x轴换行做准备
 * -----------------------------------------------------------------
 */

class LineChartXAxisFormatter(var xTitles: ArrayList<String>): IAxisValueFormatter {

    var TAG = "LineBarXAxisFormatter"

    override fun getFormattedValue(value: Float, axis: AxisBase?): String {
        Log.e(TAG,"getFormattedValue-xTitles.size = ${xTitles.size}")
        var index = value.toInt()
        if (index >= 0 && index < xTitles.size) {
            var title = xTitles.get(value.toInt())
            if (title.length > 3) {
                var date = title.substring(0, title.length - 3)
                var week = title.substring(title.length - 3, title.length)

                var formatterStr = "$date\n$week"
                return formatterStr
            }
        }
//        return super.getFormattedValue(value)
        return ""
    }
}