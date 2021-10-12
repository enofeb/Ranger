package com.enofeb.view

import android.animation.ValueAnimator
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import android.view.MotionEvent
import androidx.annotation.ColorInt
import androidx.annotation.Dimension
import java.lang.Exception
import kotlin.math.roundToInt

class RangerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = R.attr.rangerView
) : View(context, attrs, defStyleAttr) {

    @ColorInt
    private var _baseBarColor = context.getColor(R.color.colorPurple)

    @ColorInt
    private var _subBarColor = context.getColor(R.color.colorPink)

    @ColorInt
    private var _circleColor = context.getColor(R.color.colorWhite)

    @ColorInt
    private var _indicatorColor = context.getColor(R.color.colorGrey)

    @ColorInt
    private var _indicatorTextColor = context.getColor(R.color.colorWhite)

    private var _indicatorTextSize = 15f

    private var _barHeight: Int = 40

    private var _circleRadius: Int = 20

    private var _indicatorRadius: Int = 25

    private var _minValue: Double = 0.0

    private var _maxValue: Double = 100.0

    private var valueToDraw: Float = 5f

    private var baseBarPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var subBarPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var circlePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var indicatorPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var indicatorTextPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var animation: ValueAnimator? = null

    private var isBubbleVisible: Boolean? = false

    var baseBarColor: Int
        @ColorInt get() = _baseBarColor
        set(@ColorInt value) {
            _baseBarColor = value
            baseBarPaint.color = value
        }

    var subBarColor: Int
        @ColorInt get() = _subBarColor
        set(@ColorInt value) {
            _subBarColor = value
            subBarPaint.color = value
        }

    var circleColor: Int
        @ColorInt get() = _circleColor
        set(@ColorInt value) {
            _circleColor = value
            circlePaint.color = value
        }

    var indicatorColor: Int
        @ColorInt get() = _indicatorColor
        set(@ColorInt value) {
            _indicatorColor = value
            indicatorPaint.color = value
        }

    var indicatorTextColor: Int
        @ColorInt get() = _indicatorTextColor
        set(@ColorInt value) {
            _indicatorTextColor = value
            indicatorTextPaint.color = value
        }


    var indicatorTextSize: Float
        @Dimension get() = _indicatorTextSize
        set(@Dimension value) {
            _indicatorTextSize = value
            indicatorTextPaint.textSize = value
        }

    var barHeight: Int
        get() = _barHeight
        set(value) {
            _barHeight = value
        }

    var circleRadius: Int
        get() = _circleRadius
        set(value) {
            _circleRadius = value
        }

    var indicatorRadius: Int
        get() = _indicatorRadius
        set(value) {
            _indicatorRadius = value
        }

    var minValue: Double
        get() = _minValue
        set(value) {
            _minValue = value
        }

    var maxValue: Double
        get() = _maxValue
        set(value) {
            _maxValue = value
        }

    var currentValue: Double = DEFAULT_CURRENT_VALUE
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
        obtainStyledAttributes(attrs, defStyleAttr)
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

    private fun obtainStyledAttributes(attrs: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.RangerView,
            defStyleAttr,
            0
        )

        try {
            baseBarColor = typedArray.getColor(
                R.styleable.RangerView_baseBarColor,
                baseBarColor
            )
            subBarColor = typedArray.getColor(
                R.styleable.RangerView_subBarColor,
                subBarColor
            )
            circleColor = typedArray.getColor(
                R.styleable.RangerView_circleColor,
                circleColor
            )
            indicatorColor = typedArray.getColor(
                R.styleable.RangerView_indicatorColor,
                indicatorColor
            )
            indicatorTextColor = typedArray.getColor(
                R.styleable.RangerView_indicatorTextColor,
                indicatorTextColor
            )
            indicatorTextSize = typedArray.getDimension(
                R.styleable.RangerView_indicatorTextSize,
                indicatorTextSize
            )
            barHeight = typedArray.getInteger(
                R.styleable.RangerView_barHeight,
                barHeight
            )
            circleRadius = typedArray.getInteger(
                R.styleable.RangerView_circleRadius,
                circleRadius
            )
            indicatorRadius = typedArray.getInteger(
                R.styleable.RangerView_indicatorRadius,
                indicatorRadius
            )
        } catch (e: Exception) {
            //no-op
        } finally {
            typedArray.recycle()
        }
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

        canvas.drawRoundRect(rectangle, halfBarHeight / 2, halfBarHeight / 2, baseBarPaint)

        val percentFilled =
            calculateProgress(valueToDraw.toInt(), minValue, maxValue).toFloat() / 100

        val fillLength = barLength * percentFilled
        val fillPosition = left + fillLength
        val fillRect = RectF(left + 10, top + 10, fillPosition - 10, bottom - 10)

        canvas.drawRoundRect(fillRect, halfBarHeight - 15, halfBarHeight - 15, subBarPaint)

        canvas.drawCircle(fillPosition, barCenter, circleRadius.toFloat(), circlePaint)

        val radius = indicatorRadius.toFloat()

        val bounds = Rect()
        val valueString = valueToDraw.roundToInt().toString()
        indicatorTextPaint.getTextBounds(valueString, 0, valueString.length, bounds)
        indicatorTextPaint.textAlign = Paint.Align.CENTER

        val y = top - radius

        if (isBubbleVisible == true) {
            canvas.drawCircle(fillPosition, top - radius, radius, indicatorPaint)
            canvas.drawText(valueString, fillPosition, y, indicatorTextPaint)
        }

    }

    private fun calculateProgress(value: Int, min: Double, max: Double): Double {
        return 100 * (value - min) / (max - min)
    }

    companion object {
        const val DEFAULT_CURRENT_VALUE = 0.0
    }
}