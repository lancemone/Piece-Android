package com.timothy.framework.ktx.views

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapShader
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.SweepGradient
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.graphics.drawable.toBitmap
import com.timothy.framework.R
import kotlin.math.min


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.framework.ktx.views.CircleImageView
 * @Author: MoTao
 * @Date: 2023-07-13
 * <p>
 *     圆形图
 * <p/>
 */
class CircleImageView: AppCompatImageView {

    companion object{
        const val DEFAULT_RADIUS = 0
        const val DEFAULT_BORDER_WIDTH = 0
        const val DEFAULT_BORDER_COLOR = Color.WHITE
    }

    private var mRadius: Int = DEFAULT_RADIUS
    private var mBorderWidth: Int = DEFAULT_BORDER_WIDTH

    @ColorInt
    private var mBorderColour: Int = DEFAULT_BORDER_COLOR

    @ColorInt
    private var mBorderStartColour: Int = DEFAULT_BORDER_COLOR
    @ColorInt
    private var mBorderEndColour:Int = DEFAULT_BORDER_COLOR

    constructor(context: Context) : this(context, null)

    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr:Int): super(context, attrs, defStyleAttr){
        initAttrs(context = context, attributeSet = attrs, defStyleAttr = defStyleAttr)
    }

    private fun initAttrs(context: Context, attributeSet: AttributeSet?, defStyleAttr: Int) {
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.CircleImageView)
        mBorderWidth = typedArray.getDimensionPixelSize(R.styleable.CircleImageView_border_width, DEFAULT_BORDER_WIDTH)
        mBorderColour = typedArray.getColor(R.styleable.CircleImageView_border_color, DEFAULT_BORDER_COLOR)
        mBorderStartColour = typedArray.getColor(R.styleable.CircleImageView_border_start_color, 0)
        mBorderEndColour = typedArray.getColor(R.styleable.CircleImageView_border_end_color, 0)
        typedArray.recycle()
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mRadius = min(w, h) / 2
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val diameter = min(measuredWidth, measuredHeight)
        mRadius = diameter / 2
        setMeasuredDimension(diameter, diameter)
    }

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas?) {
//        super.onDraw(canvas)
        if (canvas == null){
            return
        }
        runCatching {
            val bitmap: Bitmap = drawable.toBitmap()
            val srcRadius: Float = (mRadius - mBorderWidth).toFloat()
            val viewWidth = measuredWidth
            val viewHeight = measuredHeight
            val bitmapRectF = RectF(
                /* left = */ mBorderWidth.toFloat(),
                /* top = */ mBorderWidth.toFloat(),
                /* right = */ (viewWidth - mBorderWidth).toFloat(),
                /* bottom = */ (viewHeight - mBorderWidth).toFloat()
            )
            //构建BitmapShader对象，设置为CLAMP模式，当所画图形的尺寸小于bitmap尺寸的时候，会对bitmap进行裁剪
            val bitmapShader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            // 对bitmap进行缩放
            // 对bitmap进行缩放
            val mScale = srcRadius * 2.0f / Math.min(bitmap.width, bitmap.height)
            val matrix = Matrix()
            matrix.setScale(mScale, mScale)
            bitmapShader.setLocalMatrix(matrix)
            mPaint.setShader(bitmapShader)
            mPaint.isAntiAlias = true
            // 画圆形，并对paint设置的BitmapShader中的bitmap进行裁剪处理
            canvas.drawCircle(
                /* cx = */ bitmapRectF.centerX(),
                /* cy = */ bitmapRectF.centerY(),
                /* radius = */ srcRadius,
                /* paint = */ mPaint)
            if (mBorderWidth > 0){
                drawBorder(canvas)
            }
        }
    }

    private fun drawBorder(canvas: Canvas){
        mPaint.reset()
        val mCircleRect = RectF(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat())
        mPaint.color = mBorderColour
        mPaint.style = Paint.Style.STROKE
        mPaint.strokeWidth = mBorderWidth.toFloat()
        mPaint.isAntiAlias = true
        mPaint.flags = Paint.ANTI_ALIAS_FLAG
        if (mBorderStartColour > 0 && mBorderEndColour > 0) {
            val sweepGradient = SweepGradient(
                mCircleRect.centerX(),
                mCircleRect.centerY(),
                mBorderStartColour,
                mBorderEndColour
            )
            mPaint.setShader(sweepGradient)
        }
        canvas.drawArc(
            /* oval = */ mCircleRect,
            /* startAngle = */ 0F,
            /* sweepAngle = */ 360F,
            /* useCenter = */ false,
            /* paint = */ mPaint
        )
    }
}