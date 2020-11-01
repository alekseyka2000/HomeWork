package com.example.fourpointtwo

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PointF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.google.android.material.snackbar.Snackbar
import java.lang.Math.random
import kotlin.math.pow
import kotlin.math.sqrt

class CustomViewForGame @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private var widthS: Int = 0
    private var heightS: Int = 0
    private var size: Int = 0
    private var left: Float = 0f
    private var right: Float = 0f
    private var top: Float = 0f
    private var bottom: Float = 0f
    private val colors = listOf(
        Color.YELLOW,
        Color.BLUE,
        Color.RED,
        Color.GREEN,
        Color.CYAN,
        Color.DKGRAY,
        Color.LTGRAY,
        Color.MAGENTA,
        Color.argb(255, 255, 127, 0),
        Color.BLACK
    )
    private var useColorsList = mutableListOf(0, 1, 2, 3)
    private var paint1 = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = colors[0] }
    private var paint2 = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = colors[1] }
    private var paint3 = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = colors[2] }
    private var paint4 = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = colors[3] }
    private var paintCenter = Paint(Paint.ANTI_ALIAS_FLAG).apply { color = Color.GRAY }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        widthS = MeasureSpec.getSize(widthMeasureSpec)
        heightS = MeasureSpec.getSize(heightMeasureSpec)
        size = if (widthS >= heightS) heightS * 2 / 3 else widthS * 2 / 3
        left = (widthS / 2 - size / 2).toFloat()
        right = (widthS / 2 + size / 2).toFloat()
        top = (heightS / 2 - size / 2).toFloat()
        bottom = (heightS / 2 + size / 2).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.drawArc(left, top, right, bottom, 0f, 90f, true, paint1)
        canvas?.drawArc(left, top, right, bottom, 90f, 90f, true, paint2)
        canvas?.drawArc(left, top, right, bottom, 180f, 90f, true, paint3)
        canvas?.drawArc(left, top, right, bottom, 270f, 90f, true, paint4)
        canvas?.drawCircle(
            width / 2.toFloat(),
            height / 2.toFloat(),
            size / 6.toFloat(),
            paintCenter
        )

        super.onDraw(canvas)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_UP -> {
                val touchPoint = PointF(event.x, event.y)
                val r = sqrt(
                    (widthS / 2 - touchPoint.x).pow(2)
                            + (heightS / 2 - touchPoint.y).pow(2)
                )

                when {
                    r <= size / 6 -> changColors()
                    r <= size / 2 && r > size / 4 -> when {
                        widthS / 2 > touchPoint.x && heightS / 2 > touchPoint.y -> changColor(paint3)
                        widthS / 2 < touchPoint.x && heightS / 2 > touchPoint.y -> changColor(paint4)
                        widthS / 2 > touchPoint.x && heightS / 2 < touchPoint.y -> changColor(paint2)
                        widthS / 2 < touchPoint.x && heightS / 2 < touchPoint.y -> changColor(paint1)
                    }
                }
            }
        }
        return true
    }

    private fun changColor(paint: Paint) {
        val oldColor = colors.indexOf(paint.color)
        var newColor: Int
        do {
            newColor = (random() * 10).toInt()
        } while (useColorsList.contains(newColor))
        useColorsList.apply {
            remove(oldColor)
            add(newColor)
        }
        paint.color = colors[newColor]
        Snackbar.make(this, "You push sector", Snackbar.LENGTH_SHORT).show()
        invalidate()
    }

    private fun changColors() {
        useColorsList = mutableListOf()
        var newColor = (random() * 10).toInt()
        useColorsList.add(newColor)
        for (i in 1..3) {
            do {
                newColor = (random() * 10).toInt()
            } while (useColorsList.contains(newColor))
            useColorsList.add(newColor)
        }
        paint1.color = colors[useColorsList[0]]
        paint2.color = colors[useColorsList[1]]
        paint3.color = colors[useColorsList[2]]
        paint4.color = colors[useColorsList[3]]
        Snackbar.make(this, "You push center", Snackbar.LENGTH_SHORT).show()
        invalidate()
    }
}