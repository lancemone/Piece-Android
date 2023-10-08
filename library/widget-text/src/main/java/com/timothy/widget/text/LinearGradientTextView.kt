package com.timothy.widget.text

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Shader
import android.util.AttributeSet
import android.util.Log
import androidx.annotation.ColorInt
import com.google.android.material.textview.MaterialTextView

class LinearGradientTextView : MaterialTextView{

    @ColorInt
    private var startColor = 0

    @ColorInt
    private var midColor = 0

    @ColorInt
    private var endColor = 0

    private var colors = intArrayOf()

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int): super(context, attrs, defStyleAttr){
        val at = context.obtainStyledAttributes(attrs, R.styleable.LinearGradientTextView)
        startColor = at.getColor(R.styleable.LinearGradientTextView_gradient_startColor, 0)
        midColor = at.getColor(R.styleable.LinearGradientTextView_gradient_midColor, 0)
        endColor = at.getColor(R.styleable.LinearGradientTextView_gradient_endColor, 0)
        Log.d("Piece", "startColor=$startColor midColor=$midColor endColor=$endColor")
        updateColors()
        at.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        updateColors()
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        if (colors.isNotEmpty()){
            paint.shader = LinearGradient(
                /* x0 = */ 0F,
                /* y0 = */ 0F,
                /* x1 = */ measuredWidth.toFloat(),
                /* y1 = */ 0F,
                /* colors = */ colors,
                /* positions = */ null,
                /* tile = */ Shader.TileMode.CLAMP
            )
        }
        super.onDraw(canvas)
    }

    private fun updateColors(){
        colors = if (startColor != 0 && endColor != 0){
            if (midColor != 0){
                intArrayOf(startColor, midColor, endColor)
            }else{
                intArrayOf(startColor, endColor)
            }
        }else{
            intArrayOf()
        }
        Log.d("motao", "updateColors ${colors.size}")
    }

    fun setColor(@ColorInt startColor: Int, @ColorInt endColor: Int){
        this.startColor = startColor
        this.endColor = endColor
        updateColors()
        postInvalidate()
    }

    fun setColor(@ColorInt startColor: Int, @ColorInt endColor: Int, @ColorInt midColor:Int = 0){
        this.startColor = startColor
        this.endColor = endColor
        this.midColor = midColor
        updateColors()
        postInvalidate()
    }

    fun setColor(@ColorInt vararg colors:Int){
        this.colors = colors.clone()
        postInvalidate()
    }
}