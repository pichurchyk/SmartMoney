package com.example.smartmoney.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.smartmoney.R

class CustomProgressChart(context: Context, attrs: AttributeSet) : View(context, attrs) {

    private var total: Float = 0f
    private var plan: Float = 0f
    private var spent: Float = 0f

    private var planPaint = Paint().apply {
        color = context.getColor(R.color.blue)
    }

    private var spentPaint = Paint().apply {
        color = context.getColor(R.color.blue_dark)
    }

    private val totalPaint = Paint().apply {
        color = context.getColor(R.color.light_gray)
    }

    private val textPaint = Paint().apply {
        color = context.getColor(R.color.black)
        textSize = 40f
        isFakeBoldText = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        val percentWidth = width / 10f

        val planPercent = plan / total * 100
        val spentPercent = spent / total * 100

        val drawPlan = percentWidth * planPercent / 10f
        val drawSpent = percentWidth * spentPercent / 10f

        canvas?.drawRoundRect(0f, 0f, width.toFloat(), height / 3f, 30f, 30f, totalPaint)
        canvas?.drawRoundRect(0f, 0f, drawPlan, height / 3f, 30f, 30f, planPaint)
        canvas?.drawRoundRect(0f, 0f, drawSpent, height / 3f, 30f, 30f, spentPaint)

        canvas?.drawCircle(30f, height - 30f, 30f, spentPaint)
        canvas?.drawText(" spent money", 60f, height - 15f, textPaint)

        canvas?.drawCircle(width / 2f, height - 30f, 30f, planPaint)
        canvas?.drawText(" planned limit", (width / 2f) + 30f, height - 15f, textPaint)
    }

    fun setData(newTotal: Float, newPlan: Float, newSpent: Float) {
        total = newTotal
        plan = newPlan
        spent = newSpent

        invalidate()
    }
}