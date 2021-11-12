package com.example.smartmoney.common

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.MotionEvent
import android.view.View
import com.example.domain.model.SingleTransaction
import com.example.smartmoney.R

class CustomChart(context: Context, attr: AttributeSet) : View(context, attr) {

    private val pointPaint = Paint().apply {
        color = context.getColor(R.color.blue)
    }

    private val linePaint = Paint().apply {
        color = context.getColor(R.color.blue)
        strokeWidth = 5f
    }

    private val guidsPaint = Paint().apply {
        color = context.getColor(R.color.light_gray)
        strokeWidth = 2f
    }

    private val transactionsData = mutableListOf<Pair<Float, Pair<Float, Float>>>()

    private val textPaint = Paint().apply {
        color = context.getColor(R.color.black)
        textSize = 35f
        isFakeBoldText = true
        textAlign = Paint.Align.CENTER
    }

    private var canvasRef: Canvas? = null

    private var prevPointX: Float? = null
    private var prevPointY: Float? = null
    private var firstDrawing: Boolean = false

    private var showInfo: Boolean = false

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun invalidate() {
        super.invalidate()
        firstDrawing = true
    }

    private var stats: List<SingleTransaction>? = null

    fun setStats(transactions: List<SingleTransaction>) {
        stats = transactions.takeLast(10)
        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        canvas?.drawLine(30f, height / 2f, width.toFloat(), height / 2f, guidsPaint)
        canvas?.drawText("0", 10f, (height / 2f) + 10f, textPaint)

        canvasRef = canvas

        if (stats != null && stats!!.isNotEmpty()) {
            val transactionsQuantity = stats?.size!!
            val pointsWidth = width / transactionsQuantity
            val transactionsSum = stats!!.sumOf { it.total!!.toInt() }
            val pointsHeight =
                (height.toFloat() / (transactionsSum)) - (height.toFloat() / (transactionsSum)) / 2.5f
            Log.d("111", pointsHeight.toString())
            var prevPointX = pointsWidth / 2f

            var sum = 0f

            stats!!.forEach {
                var amount = it.total?.toInt()
                if (it.type.equals("Expense")) {
                    amount = amount!!.unaryMinus()
                }
                sum += amount!!.toFloat()

                val x = prevPointX
                val y = height / 2f - (sum * pointsHeight) / 2
                calculatePoints(x, y)

                transactionsData.add(sum to Pair(x, y))

                prevPointX = x + pointsWidth

                if (showInfo) {
                    showInfo()
                }
            }
        }
    }

    private fun calculatePoints(x: Float, y: Float) {

        if (!firstDrawing) {
            drawPoint(x, y)
        } else firstDrawing = false

        prevPointX = x
        prevPointY = y
    }

    private fun drawPoint(newPointX: Float, newPointY: Float) {
        if (prevPointX != null && prevPointY != null) {
            canvasRef?.drawLine(prevPointX!!, prevPointY!!, newPointX, newPointY, linePaint)
            canvasRef?.drawCircle(prevPointX!!, prevPointY!!, 10f, pointPaint)
            canvasRef?.drawCircle(newPointX, newPointY, 10f, pointPaint)
        }
    }

    private fun showInfo() {

        transactionsData.forEach {
            val total = it.first
            val x = it.second.first
            val y = it.second.second

            canvasRef?.drawCircle(x, y, 50f, pointPaint)
            canvasRef?.drawText("${total.toInt()}", x, y + 10f, textPaint)
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        showInfo = !showInfo
        invalidate()
        return super.onTouchEvent(event)
    }
}