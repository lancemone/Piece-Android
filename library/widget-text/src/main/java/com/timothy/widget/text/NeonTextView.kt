package com.timothy.widget.text

import android.content.Context
import android.graphics.Canvas
import android.graphics.LinearGradient
import android.graphics.Matrix
import android.graphics.Shader
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatTextView
import androidx.core.graphics.transform

class NeonTextView : AppCompatTextView{

    private var mLinearGradient: LinearGradient? = null
    private var mGradientMatrix: Matrix = Matrix()

    @ColorInt
    var startColor = 0
        set(value) {
            field = value
            initColors()
        }

    @ColorInt
    var endColor = 0

    @AnimateDirection
    var direction = leftToRight

    var translateSpeed = 0

    var translateAnimate = false

    private var translate = 0f

    var colors: IntArray = intArrayOf()

    constructor(context: Context): this(context = context, attrs = null)

    constructor(context: Context, attrs: AttributeSet?) : this(context = context, attrs = attrs, defStyleAttr = 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        val ta = context.obtainStyledAttributes(attrs, R.styleable.NeonTextView)
        startColor = ta.getColor(R.styleable.NeonTextView_gradient_startColor, resources.getColor(R.color.design_default_color_primary, null))
        endColor = ta.getColor(R.styleable.NeonTextView_gradient_endColor, resources.getColor(R.color.design_default_color_secondary, null))
        direction = ta.getInt(R.styleable.NeonTextView_gradient_direction, leftToRight)
        translateSpeed = ta.getInt(R.styleable.NeonTextView_gradient_speed, translateSpeedNormal)
        translateAnimate = ta.getBoolean(R.styleable.NeonTextView_gradient_animate, false)
        initColors()
        ta.recycle()
    }

    private fun initColors() {
        if (translateAnimate) {
            colors = intArrayOf(startColor, endColor, startColor, startColor, startColor)
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (translateAnimate){
            when(direction){
                leftToRight -> {
                    // 利用 Matrix 的平移动作实现霓虹灯的效果
                    translate += measuredWidth / translateSpeed
                    if (translate > measuredWidth) {
                        translate = 0f
                    }
                    mGradientMatrix.setTranslate(translate, 0f)
                }
                topToBottom -> {
                    translate += measuredHeight / translateSpeed
                    if (translate > measuredHeight) {
                        translate = 0f
                    }
                    mGradientMatrix.setTranslate(0f, translate)
                }
            }

            mLinearGradient?.setLocalMatrix(mGradientMatrix)
            postInvalidateDelayed(50)
//            postInvalidateDelayed(translate.toLong())
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        setupGradient()
        paint.shader = mLinearGradient
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        translateAnimate = false
    }

    private fun setupGradient(){
        if (measuredWidth <= 0 || measuredHeight <= 0) {
            mLinearGradient = null
            return
        }
        when (direction) {
            leftToRight ->
                if (translateAnimate) {
                    mLinearGradient = LinearGradient(
                        0f,
                        0f,
                        measuredWidth.toFloat(),
                        0f,
                        colors,
                        null,
                        Shader.TileMode.CLAMP
                    )
                } else {
                    mLinearGradient = LinearGradient(
                        0f,
                        0f,
                        measuredWidth.toFloat(),
                        0f,
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
            topToBottom ->
                if (translateAnimate) {
                    mLinearGradient = LinearGradient(
                        0f,
                        0f,
                        0f,
                        measuredHeight.toFloat(),
                        colors,
                        null,
                        Shader.TileMode.CLAMP
                    )
                } else {
                    mLinearGradient = LinearGradient(
                        0f,
                        0f,
                        0f,
                        measuredHeight.toFloat(),
                        startColor,
                        endColor,
                        Shader.TileMode.CLAMP
                    )
                }
        }
    }

    companion object{

        const val leftToRight = 1
        const val topToBottom = 2

        const val translateSpeedFast = 5
        const val translateSpeedNormal = 10
        const val translateSpeedSlow = 20
    }
}

@IntDef(value = [NeonTextView.leftToRight, NeonTextView.topToBottom], flag = false, open = false)
annotation class AnimateDirection