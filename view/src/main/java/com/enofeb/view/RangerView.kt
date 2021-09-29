package com.enofeb.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View

class RangerView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int
) : View(context, attrs, defStyleAttr) {

    private var barHeight: Int = 15
    private var circleRadius: Int = 0
    private var barBasePaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    init {
        barBasePaint.color = context.getColor(R.color.colorPurple)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawRangerBar(canvas)
    }

    private fun measureWidth(measureSpec: Int): Int {
        val size = paddingLeft + paddingRight
        return resolveSizeAndState(size, measureSpec, 0)
    }

    private fun measureHeight(measureSpec: Int): Int {
        var size = paddingTop + paddingBottom
        size += barHeight.coerceAtLeast(circleRadius)
        return resolveSizeAndState(size, measureSpec, 0)
    }

    private fun drawRangerBar(canvas: Canvas) {
        val paddingSpaces = paddingLeft - paddingRight
        val barLength = (width - paddingSpaces).toFloat()
        val barCenter = (height / 2).toFloat()

        val halfBarHeight = (barHeight / 2).toFloat()
        val top = barCenter - halfBarHeight
        val bottom = barCenter + halfBarHeight
        val left = paddingLeft.toFloat()
        val right = paddingLeft + barLength
        val rectangle = RectF(left, top, right, bottom)

        canvas.drawRoundRect(rectangle, halfBarHeight, halfBarHeight, barBasePaint)

    }
}