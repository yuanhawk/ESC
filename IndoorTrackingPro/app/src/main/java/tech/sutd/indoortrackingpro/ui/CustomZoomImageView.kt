package tech.sutd.indoortrackingpro.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import com.otaliastudios.zoom.ZoomImageView

class CustomZoomImageView(
    context: Context,
    attrs: AttributeSet?
) : ZoomImageView(context, attrs) {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var pos = floatArrayOf(0f, 0f)

    init {
        paint.color = Color.GRAY
    }

    override fun setEnabled(enabled: Boolean) {
        if (enabled)
            paint.color = Color.CYAN
        else
            paint.color = Color.GRAY
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(pos[0], pos[1], 30f, paint)
    }
}