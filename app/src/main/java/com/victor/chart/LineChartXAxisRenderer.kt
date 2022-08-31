package com.hok.lib.common.view.widget.linechart

import android.graphics.Canvas
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.renderer.XAxisRenderer
import com.github.mikephil.charting.utils.MPPointF
import com.github.mikephil.charting.utils.Transformer
import com.github.mikephil.charting.utils.Utils
import com.github.mikephil.charting.utils.ViewPortHandler

/*
 * -----------------------------------------------------------------
 * Copyright (C) 2018-2028, by Victor, All rights reserved.
 * -----------------------------------------------------------------
 * File: LineChartXAxisRenderer
 * Author: Victor
 * Date: 2022/8/29 17:17
 * Description: x轴换行显示处理
 * -----------------------------------------------------------------
 */

class LineChartXAxisRenderer(
    viewPortHandler: ViewPortHandler?, xAxis: XAxis?, trans: Transformer?) :
    XAxisRenderer(viewPortHandler, xAxis, trans) {

    override fun drawLabel(
        c: Canvas?,
        formattedLabel: String?,
        x: Float,
        y: Float,
        anchor: MPPointF?,
        angleDegrees: Float
    ) {
//        super.drawLabel(c, formattedLabel, x, y, anchor, angleDegrees)
        val lines = formattedLabel?.split("\n")

        lines?.forEachIndexed { index, s ->
            val vOffset: Float = index * mAxisLabelPaint.textSize
            Utils.drawXAxisValue(c, lines[index], x, y + vOffset, mAxisLabelPaint, anchor, angleDegrees)
        }
    }
}