package com.timothy.framework.ktx.string.span

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Shader
import android.os.Parcel
import android.os.Parcelable
import android.text.SpannableStringBuilder
import android.text.style.ReplacementSpan
import androidx.annotation.ColorInt
import androidx.core.text.inSpans


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.framework.ktx.string.span.LinearGradientColorSpan
 * @Author: MoTao
 * @Date: 2023-07-25
 * <p>
 * <p/>
 */
class LinearGradientColorSpan : ReplacementSpan, Parcelable {
    private var mSize: Float = 0F   // The width of the text
    private var mColors: IntArray = intArrayOf(Color.WHITE, Color.WHITE)
    private var mPositions: FloatArray? = null

    private var orientation:GradientOrientation = GradientOrientation.HORIZONTAL_LEFT_TO_RIGHT

    constructor(parcel: Parcel) {
        mSize = parcel.readFloat()
        mColors = parcel.createIntArray() ?: intArrayOf(Color.WHITE, Color.WHITE)
        mPositions = parcel.createFloatArray()
    }

    constructor(@ColorInt startColor:Int,
                @ColorInt endColor:Int,
                orientation:GradientOrientation = GradientOrientation.HORIZONTAL_LEFT_TO_RIGHT) {
        mColors = intArrayOf(startColor, endColor)
        this.orientation = orientation
    }

    constructor(@ColorInt  colors: IntArray,
                positions: FloatArray? = null,
                orientation:GradientOrientation = GradientOrientation.HORIZONTAL_LEFT_TO_RIGHT) {
        mColors = colors
        mPositions = positions
        this.orientation = orientation
    }

    override fun getSize(
        paint: Paint,
        text: CharSequence?,
        start: Int,
        end: Int,
        fm: Paint.FontMetricsInt?
    ): Int {
        mSize = paint.measureText(text, start, end)

        //这段不可以去掉，字体高度没设置，可能出现draw（）方法没有被调用，如果你调用SpannableStringBuilder后append一个字符串，效果也是会出来，下面这段就不需要了
        // 原因详见https://stackoverflow.com/questions/20069537/replacementspans-draw-method-isnt-called
        val metrics = paint.fontMetricsInt
        fm?.top = metrics.top
        fm?.ascent = metrics.ascent
        fm?.descent = metrics.descent
        fm?.bottom = metrics.bottom

        return mSize.toInt()
    }

    override fun draw(
        canvas: Canvas,
        text: CharSequence?,
        start: Int,
        end: Int,
        x: Float,
        top: Int,
        y: Int,
        bottom: Int,
        paint: Paint
    ) {
        text?.let {
            var xStart = 0F
            var xEnd = 0F
            var yStart = 0F
            var yEnd = 0F
            when(orientation){
                GradientOrientation.HORIZONTAL_LEFT_TO_RIGHT -> {
                    // 实现从左到右需要设置shader开始结束点坐标为左上角到右上角或者左下角到右下角
                    xEnd = mSize
                }
                GradientOrientation.HORIZONTAL_RIGHT_TO_LEFT -> {
                    // 实现从右到左需要设置shader开始结束坐标为右上角到左上角或者右下角到左下角。
                    xStart = mSize
                }
                GradientOrientation.VERTICAL_BOTTOM_TO_TOP -> {
                    // 实现从下到上需要设置shader开始结束点坐标为左下角到左上角或右下角到右上角。
                    yStart = paint.descent() - paint.ascent()
                }
                GradientOrientation.VERTICAL_TOP_TO_BOTTOM -> {
                    // 实现从上到下需要设置shader开始结束点坐标为左上角到左下角或右上角到右下角坐标。
                    yEnd = paint.descent() - paint.ascent()
                }
                GradientOrientation.DIAGONAL_LEFT_TOP_TO_RIGHT -> {
                    // 实现对角shader，需要设置开始结束点坐标左上角到右下角
                    yStart = paint.descent() - paint.ascent()
                    xEnd = mSize
                }
            }
            val mLinearGradient: LinearGradient = LinearGradient(
                /* x0 = */ xStart,
                /* y0 = */ yStart,
                /* x1 = */ xEnd,
                /* y1 = */ yEnd,
                /* colors = */ mColors,
                /* positions = */ mPositions,
                /* tile = */ Shader.TileMode.REPEAT
            )
            paint.shader = mLinearGradient
            canvas.drawText(text, start, end, x.toFloat(), y.toFloat(), paint)
        }
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeFloat(mSize)
        parcel.writeIntArray(mColors)
        parcel.writeFloatArray(mPositions)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<LinearGradientColorSpan> {
        override fun createFromParcel(parcel: Parcel): LinearGradientColorSpan {
            return LinearGradientColorSpan(parcel)
        }

        override fun newArray(size: Int): Array<LinearGradientColorSpan?> {
            return arrayOfNulls(size)
        }

        enum class GradientOrientation(val value:Int){
            HORIZONTAL_LEFT_TO_RIGHT(1), HORIZONTAL_RIGHT_TO_LEFT(2),
            VERTICAL_TOP_TO_BOTTOM(3), VERTICAL_BOTTOM_TO_TOP(4),
            DIAGONAL_LEFT_TOP_TO_RIGHT(5)
        }
    }

}

fun SpannableStringBuilder.linearGradient(@ColorInt vararg colors: Int,
                                          orientation: LinearGradientColorSpan.CREATOR.GradientOrientation = LinearGradientColorSpan.CREATOR.GradientOrientation.HORIZONTAL_LEFT_TO_RIGHT,
                                          builderAction: SpannableStringBuilder.() -> Unit){
    inSpans(LinearGradientColorSpan(colors, null, orientation), builderAction)
}