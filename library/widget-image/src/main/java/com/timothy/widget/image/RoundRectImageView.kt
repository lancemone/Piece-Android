package com.timothy.widget.image

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.LinearGradient
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.graphics.Shader
import android.graphics.Xfermode
import android.icu.lang.UCharacter.DecompositionType
import android.os.Build
import android.util.AttributeSet
import androidx.annotation.ColorInt
import androidx.annotation.IntDef
import androidx.appcompat.widget.AppCompatImageView
import kotlin.math.min


/**
 * @Project: Piece
 * @ClassPath:  com.timothy.widget.image.RoundRectImageView
 * @Author: MoTao
 * @Date: 2023-08-03
 * <p>
 *     圆角矩形图片控件,支持圆角、圆形显示
 *     可绘制边框，圆形时可绘制内外两层边框,支持边框不覆盖图片
 *     可绘制遮罩
 *     <a href="https://github.com/shehuan/NiceImageView"/>
 * <p/>
 */
class RoundRectImageView : AppCompatImageView{

    companion object{
        const val CIRCLE = 0
        const val FILLET = 1
    }

    private var isCircle = false // 是否显示为圆形，如果为圆形则设置的corner无效

    var isCoverImage = false // border、inner_border是否覆盖图片
        set(value) {
            field = value
            initSrcRectF()
            invalidate()
        }

    @ColorInt
    var borderColor = Color.WHITE // 边框颜色
        set(value) {
            field = value
            invalidate()
        }


    var borderWidth = 0
        set(value) {
            field = value
            calculateRadiiAndRectF()
        }

    var innerBorderWidth = 0 // 内层边框宽度
        set(value) {
            field = value
            calculateRadiiAndRectF()
        }

    @ColorInt
    private var innerBorderColor = Color.WHITE // 内层边框充色
        set(value) {
            field = value
            invalidate()
        }

    private var cornerRadius = 0 // 统一设置圆角半径，优先级高于单独设置每个角的半径
        set(value) {
            field = value
            calculateRadiiAndRectF()
        }

    private var cornerTopLeftRadius = 0 // 左上角圆角半径
        set(value) {
            field = value
            calculateRadiiAndRectF()
        }

    private var cornerTopRightRadius = 0 // 右上角圆角半径
        set(value) {
            field = value
            calculateRadiiAndRectF()
        }

    private var cornerBottomLeftRadius = 0 // 左下角圆角半径
        set(value) {
            field = value
            calculateRadiiAndRectF()
        }

    private var cornerBottomRightRadius = 0 // 右下角圆角半径
        set(value) {
            field = value
            calculateRadiiAndRectF()
        }

    @ColorInt
    private var maskColor = 0 // 遮罩颜色
        set(value) {
            field = value
            invalidate()
        }

    private var borderShader: Shader? = null
        set(value) {
            field = value
            invalidate()
        }

    private var innerBorderShader:Shader? = null
        set(value) {
            field = value
            invalidate()
        }

    private var width = 0
    private var height = 0
    private var radius = 0f

    private val borderRadii: FloatArray = FloatArray(8)
    private val srcRadii: FloatArray = FloatArray(8)

    private var srcRectF: RectF = RectF() // 图片占的矩形区域

    private val borderRectF: RectF = RectF() // 边框的矩形区域
    private val innerBorderRectF: RectF = RectF() // 内边框的矩形区域

    private var paint: Paint = Paint()
    private var path: Path = Path()  // 用来裁剪图片的ptah
    private var srcPath: Path = Path() // 图片区域大小的path

    constructor(context: Context): this(context, null)

    constructor(context: Context, attrs: AttributeSet?): this(context, attrs, defStyleAttr = 0)

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr:Int) : super(context, attrs, defStyleAttr) {
        runCatching {
            val array = context.obtainStyledAttributes(
                /* set = */ attrs,
                /* attrs = */R.styleable.RoundRectImageView,
                /* defStyleAttr = */0,
                /* defStyleRes = */0
            )
            isCoverImage = array.getBoolean(R.styleable.RoundRectImageView_is_cover_image, false)
            isCircle = array.getInt(R.styleable.RoundRectImageView_image_shape, FILLET) == CIRCLE
            borderWidth = array.getDimensionPixelSize(R.styleable.RoundRectImageView_border_width, 0)
            borderColor = array.getColor(R.styleable.RoundRectImageView_border_color, Color.WHITE)
            innerBorderWidth = array.getDimensionPixelSize(R.styleable.RoundRectImageView_inner_border_width, 0)
            innerBorderColor = array.getColor(R.styleable.RoundRectImageView_inner_border_color, Color.WHITE)
            cornerRadius = array.getDimensionPixelSize(R.styleable.RoundRectImageView_corner_radius, 0)
            cornerTopLeftRadius = array.getDimensionPixelSize(R.styleable.RoundRectImageView_corner_top_left_radius, 0)
            cornerTopRightRadius = array.getDimensionPixelSize(R.styleable.RoundRectImageView_corner_top_right_radius, 0)
            cornerBottomLeftRadius = array.getDimensionPixelSize(R.styleable.RoundRectImageView_corner_bottom_left_radius, 0)
            cornerBottomRightRadius = array.getDimensionPixelSize(R.styleable.RoundRectImageView_corner_bottom_right_radius, 0)
            maskColor = array.getColor(R.styleable.RoundRectImageView_mask_color, maskColor)
            array.recycle()

            calculateRadii()
//            clearInnerBorderWidth()
        }
    }

    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        // 使用图形混合模式来显示指定区域的图片
        canvas.saveLayer(srcRectF, null)
        if (!isCoverImage) {
            val sx = 1.0f * (width - 2 * borderWidth - 2 * innerBorderWidth) / width
            val sy = 1.0f * (height - 2 * borderWidth - 2 * innerBorderWidth) / height
            // 缩小画布，使图片内容不被borders覆盖
            canvas.scale(sx, sy, width / 2.0f, height / 2.0f)
        }
        super.onDraw(canvas)
        paint.reset()
        path.reset()
        if (isCircle){
            path.addCircle(width / 2.0f, height / 2.0f, radius, Path.Direction.CCW)
        }else {
            path.addRoundRect(srcRectF, srcRadii, Path.Direction.CCW)
        }

        paint.isAntiAlias = true
        paint.style = Paint.Style.FILL
        paint.setXfermode(/* xfermode = */ PorterDuffXfermode(/* mode = */ PorterDuff.Mode.DST_OUT))

//        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.O_MR1){
//            canvas?.drawPath(path, paint)
//        }else {
//            srcPath.addRect(srcRectF, Path.Direction.CCW)
//            srcPath.op(path, Path.Op.DIFFERENCE)   // 计算tempPath和path的差集
//            canvas?.drawPath(srcPath, paint)
//        }

        srcPath.reset()
        srcPath.addRect(srcRectF, Path.Direction.CCW)
        srcPath.op(path, Path.Op.DIFFERENCE)   // 计算tempPath和path的差集
        canvas.drawPath(srcPath, paint)
        paint.setXfermode(null)
        if (maskColor != 0){
            paint.color = maskColor
            canvas.drawPath(path, paint)
        }
        // 恢复画布
        canvas.restore()
        drawBorders(canvas)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        width = w
        height = h
        initBorderRectF()
        initInnerBorderRectF()
        initSrcRectF()
    }

    private fun drawBorders(canvas: Canvas?) {
        if (isCircle) {
            if (borderWidth > 0) {
                drawCircleBorder(canvas, borderWidth, borderColor, radius - borderWidth / 2.0f, false)
            }
            if (innerBorderWidth > 0) {
                drawCircleBorder(
                    canvas, innerBorderWidth, innerBorderColor,
                    radius - borderWidth - innerBorderWidth / 2.0f
                )
            }
        } else {
            if (borderWidth > 0) {
                drawRectFBorder(canvas, borderWidth, borderColor, borderRectF, borderRadii, false)
            }
            if (innerBorderWidth > 0){
                drawRectFBorder(canvas, innerBorderWidth, innerBorderColor, innerBorderRectF, borderRadii)
            }
        }
    }

    private fun drawCircleBorder(
        canvas: Canvas?,
        borderWidth: Int,
        borderColor: Int,
        radius: Float,
        isInner: Boolean = false
    ) {
        initBorderPaint(borderWidth, borderColor, isInner)
        path.addCircle(width / 2.0f, height / 2.0f, radius, Path.Direction.CCW)
        canvas?.drawPath(path, paint)
    }

    private fun drawRectFBorder(
        canvas: Canvas?,
        borderWidth: Int,
        borderColor: Int,
        rectF: RectF,
        radii: FloatArray,
        isInner: Boolean = false
    ) {
        initBorderPaint(borderWidth, borderColor, isInner)
        path.addRoundRect(rectF, radii, Path.Direction.CCW)
        canvas?.drawPath(path, paint)
    }

    private fun initBorderPaint(borderWidth: Int, borderColor: Int, isInner: Boolean) {
        path.reset()
        paint.strokeWidth = borderWidth.toFloat()
        paint.color = borderColor
        paint.style = Paint.Style.STROKE
        if (isInner){
            innerBorderShader?.let { paint.shader = it }
        }else{
            borderShader?.let { paint.shader = it }
        }
    }

    /**
     * 计算RectF的圆角半径
     */
    private fun calculateRadii(){
        if (isCircle){
            return
        }

        if (cornerRadius > 0) {
            for (i in borderRadii.indices) {
                borderRadii[i] = cornerRadius.toFloat()
                srcRadii[i] = cornerRadius - borderWidth / 2f
            }
        } else {
            borderRadii[1] = cornerTopLeftRadius.toFloat()
            borderRadii[0] = borderRadii[1]
            borderRadii[3] = cornerTopRightRadius.toFloat()
            borderRadii[2] = borderRadii[3]
            borderRadii[5] = cornerBottomRightRadius.toFloat()
            borderRadii[4] = borderRadii[5]
            borderRadii[7] = cornerBottomLeftRadius.toFloat()
            borderRadii[6] = borderRadii[7]
            srcRadii[1] = cornerTopLeftRadius - borderWidth / 2.0f
            srcRadii[0] = srcRadii[1]
            srcRadii[3] = cornerTopRightRadius - borderWidth / 2.0f
            srcRadii[2] = srcRadii[3]
            srcRadii[5] = cornerBottomRightRadius - borderWidth / 2.0f
            srcRadii[4] = srcRadii[5]
            srcRadii[7] = cornerBottomLeftRadius - borderWidth / 2.0f
            srcRadii[6] = srcRadii[7]
        }
    }

    /**
     * 目前圆角矩形情况下不支持inner_border，需要将其置0
     */
    private fun clearInnerBorderWidth() {
        if (!isCircle) {
            innerBorderWidth = 0
        }
    }

    /**
     * 计算外边框的RectF
     */
    private fun initBorderRectF() {
        if (!isCircle) {
            borderRectF.set(
                borderWidth / 2.0f,
                borderWidth / 2.0f,
                width - borderWidth / 2.0f,
                height - borderWidth / 2.0f
            )
        }
    }

    private fun initInnerBorderRectF(){
        if (!isCircle) {
            innerBorderRectF.set(
                borderWidth + innerBorderWidth / 2.0f,
                borderWidth + innerBorderWidth / 2.0f,
                width - borderWidth - innerBorderWidth / 2.0f,
                height - borderWidth - innerBorderWidth / 2.0f
            )
        }
    }

    /**
     * 计算图片原始区域的RectF
     */
    private fun initSrcRectF() {
        if (isCircle) {
            radius = min(width, height) / 2.0f
            srcRectF.set(
                width / 2.0f - radius,
                height / 2.0f - radius,
                width / 2.0f + radius,
                height / 2.0f + radius
            )
        } else {
            if (isCoverImage) {
                srcRectF = borderRectF
            }else{
                srcRectF.set(
                    0f,
                    0f,
                    width.toFloat(),
                    height.toFloat()
                )
            }
        }
    }

    private fun calculateRadiiAndRectF() {
//        if (reset) {
//            cornerRadius = 0
//        }
        calculateRadii()
        initBorderRectF()
        initInnerBorderRectF()
        invalidate()
    }

    fun setImageShape(@RoundRectImageShapeType imageShape: Int) {
        isCircle = imageShape == DecompositionType.CIRCLE
        initSrcRectF()
        invalidate()
    }

    fun isCircle(): Boolean {
        return isCircle
    }

    @Retention(AnnotationRetention.SOURCE)
    @IntDef(CIRCLE, FILLET)
    private annotation class RoundRectImageShapeType

}