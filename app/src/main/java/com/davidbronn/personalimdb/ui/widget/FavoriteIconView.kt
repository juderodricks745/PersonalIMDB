package com.davidbronn.personalimdb.ui.widget

import android.content.Context
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.DrawableRes
import androidx.appcompat.content.res.AppCompatResources
import com.davidbronn.personalimdb.R

/**
 * Created by Jude on 18/March/2020
 */
class FavoriteIconView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    init {
        init()
    }

    private var arcRectF: RectF? = null
    private var arcPaint: Paint? = null
    private var iconDrawableRect: Rect? = null
    private var iconDrawablePaint: Paint? = null
    private var drawable: Drawable? = null
    private var canvas: Canvas? = null

    private fun init() {
        arcRectF = RectF()
        arcPaint = Paint().apply {
            color = Color.WHITE
            style = Paint.Style.FILL_AND_STROKE
            isAntiAlias = true
        }

        iconDrawableRect = Rect()
        iconDrawablePaint = Paint().apply {
            color = Color.TRANSPARENT
            style = Paint.Style.FILL
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        this.canvas = canvas
        // Drawing Arc
        arcRectF?.set(5f, -height.toFloat(), width + width.toFloat(), height - 5.toFloat())
        canvas.drawArc(arcRectF!!, 90f, 90f, true, arcPaint!!)

        iconDrawableRect?.set(height / 3, height / 16, width - width / 16, height - height / 3)
        drawIcon(R.drawable.ic_unfilled)
    }

    fun drawIcon(@DrawableRes resourceId: Int) {
        drawable = AppCompatResources.getDrawable(context, resourceId)
        drawable?.bounds = iconDrawableRect!!
        drawable?.draw(canvas!!)

    }
}