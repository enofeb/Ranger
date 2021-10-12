package com.enofeb.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.MotionEvent
import kotlin.math.roundToInt

class RangerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.rangerView
) : View(context, attrs, defStyleAttr) {

    private var barHeight: Int = 40
    private var barBasePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var barFillPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var circleFillPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var indicatorFillPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var valuePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var valueToDraw: Float = 5f
    private var minValue: Double = 0.0
    private var maxValue: Double = 100.0

    private var animation: ValueAnimator? = null

    private var isBubbleVisible: Boolean? = false


    var currentValue: Double = 0.0
        set(value) {
            val previousValue = currentValue
            field = value
            var newValue = value

            if (newValue < minValue || newValue > maxValue) {
                newValue = currentValue
                field = newValue
            }

            animation?.cancel()

            animation = ValueAnimator.ofFloat(previousValue.toFloat(), currentValue.toFloat())

            animation?.addUpdateListener { valueAnimator ->
                valueToDraw = valueAnimator.animatedValue as Float
                this.invalidate()
            }

            valueToDraw = currentValue.toFloat()

            animation!!.start()

            invalidate()
        }


    init {
        barBasePaint.color = context.getColor(R.color.colorPurple)
        barFillPaint.color = context.getColor(R.color.colorPink)
        circleFillPaint.color = context.getColor(R.color.colorGrey)
        valuePaint.color = context.getColor(R.color.colorWhite)
        indicatorFillPaint.color = context.getColor(R.color.colorWhite)
        valuePaint.textSize = 24f
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawRangerBar(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent): Boolean {

        var coordinate = event.x.toDouble()
        val canvasSize = (width - paddingStart - paddingEnd).toDouble()
        val eventAction = event.action

        if (coordinate < 0) {
            coordinate = 0.0
        } else if (coordinate > canvasSize) {
            coordinate = canvasSize
        }

        when (eventAction) {
            MotionEvent.ACTION_DOWN -> {
                //no-op
            }
            MotionEvent.ACTION_UP -> {
                val value = (coordinate / canvasSize * 100).toInt()
                isBubbleVisible = false
                updatePosition(value)
            }
            MotionEvent.ACTION_MOVE -> {
                val value = (coordinate / canvasSize * 100).toInt()
                isBubbleVisible = true
                updatePosition(value)
            }
            MotionEvent.ACTION_CANCEL -> {
                //no-op
            }
        }

        return true
    }

    override fun performClick(): Boolean {
        super.performClick()
        return true
    }

    private fun updatePosition(value: Int) {
        val calculatedValue = (value * (maxValue - minValue) / 100)
        val displayValue = ((calculatedValue) + minValue)
        currentValue = displayValue
    }

    private fun measureWidth(measureSpec: Int): Int {
        val size = paddingLeft + paddingRight
        return resolveSizeAndState(size, measureSpec, 0)
    }

    private fun measureHeight(measureSpec: Int): Int {
        var size = paddingTop + paddingBottom
        size += barHeight.coerceAtLeast(100)
        return resolveSizeAndState(size, measureSpec, 0)
    }

    private fun drawRangerBar(canvas: Canvas) {
        val paddingSpaces = paddingLeft + paddingRight
        val barLength = (width - paddingSpaces).toFloat()
        val barCenter = (height / 2).toFloat()

        val halfBarHeight = (barHeight / 2).toFloat()
        val top = barCenter - halfBarHeight
        val bottom = barCenter + halfBarHeight
        val left = paddingLeft.toFloat()
        val right = paddingLeft + barLength
        val rectangle = RectF(left, top, right, bottom)

        canvas.drawRoundRect(rectangle, halfBarHeight / 2, halfBarHeight / 2, barBasePaint)

        val percentFilled =
            calculateProgress(valueToDraw.toInt(), minValue, maxValue).toFloat() / 100

        val fillLength = barLength * percentFilled
        val fillPosition = left + fillLength
        val fillRect = RectF(left + 10, top + 10, fillPosition - 10, bottom - 10)

        canvas.drawRoundRect(fillRect, halfBarHeight - 15, halfBarHeight - 15, barFillPaint)

        canvas.drawCircle(fillPosition, barCenter, 15f, indicatorFillPaint)

        val radius = 20f

        if (isBubbleVisible == true) {
            canvas.drawCircle(fillPosition, top - radius, radius, circleFillPaint)
        }

        val bounds = Rect()
        val valueString = valueToDraw.roundToInt().toString()
        valuePaint.getTextBounds(valueString, 0, valueString.length, bounds)
        valuePaint.textAlign = Paint.Align.CENTER
        valuePaint.textSize = 15f

        val y = top - radius

        canvas.drawText(valueString, fillPosition, y, valuePaint)

    }

    private fun calculateProgress(value: Int, min: Double, max: Double): Double {
        return 100 * (value - min) / (max - min)
    }
}