package com.timothy.widget.image

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.appcompat.widget.AppCompatImageView


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.widget.image.SplitterImageView
 * @Author: MoTao
 * @Date: 2023-08-16
 * <p>
 *     分割图片
 * <p/>
 */


class SplitterImageView : AppCompatImageView{

    companion object{
        const val DEFAULT_ROW_COUNT = 1 // 默认行数
        const val DEFAULT_COLUMN_COUNT = 1 // 默认列数

    }

    private var rowCount = DEFAULT_ROW_COUNT   // 行数
    private var columnCount = DEFAULT_COLUMN_COUNT // 列数

    @ColorInt
    private var divisionColor = Color.WHITE

    private var divisionWidth = 1

    private val bitmapWidthPer = 0 // 每个小bitmap的宽

    private val bitmapHeightPer = 0 // 每个小bitmap的高

    private val divisionPaint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)

    constructor(context: Context): this(context, null)
    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr:Int): super(context, attrs, defStyleAttr){
        initAttr(context, attrs)
    }

    private fun initAttr(context: Context, attrs: AttributeSet?){
        runCatching {
            val arrays = context.obtainStyledAttributes(
                attrs, R.styleable.SplitterImageView, 0, 0
            )
            divisionColor = arrays.getColor(R.styleable.SplitterImageView_division_color, Color.WHITE)
//            divisionWidth = arrays.getDimensionPixelSize(R.styleable.SplitterImageView_division_width, 1.dp)
            arrays.recycle()
        }
    }
}