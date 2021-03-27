package tech.sutd.indoortrackingpro.utils

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
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var border = Paint(Paint.ANTI_ALIAS_FLAG)
    var pos = floatArrayOf(0f, 0f)

    init {
        paint.color = Color.GRAY
        border.color = Color.WHITE
    }

    override fun setEnabled(enabled: Boolean) {
        if (enabled){

            paint.color = Color.BLUE}
        else

            paint.color = Color.GRAY
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(pos[0], pos[1], 40f, border)
        canvas.drawCircle(pos[0], pos[1], 30f, paint)
    }
}