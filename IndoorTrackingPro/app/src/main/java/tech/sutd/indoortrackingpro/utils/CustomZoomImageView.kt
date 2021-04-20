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
    //the bullet point for where the user taps
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var border = Paint(Paint.ANTI_ALIAS_FLAG)
    //the bullet point for mapping points
    private var secondPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var secondBorder = Paint(Paint.ANTI_ALIAS_FLAG);
    //the position where the user taps
    var pos = floatArrayOf(0f, 0f)
    //list of positions for mapping points
    var secondPosList = listOf<FloatArray>(floatArrayOf(0f, 0f))

    init {
        paint.color = Color.GRAY
        border.color = Color.WHITE
        secondBorder.color = Color.WHITE
        secondPaint.color = Color.GREEN
    }

    override fun setEnabled(enabled: Boolean) {
        if (enabled){
            paint.color = Color.BLUE}
        else {
            paint.color = Color.GRAY
        }

    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(pos[0], pos[1], 40f, border)
        canvas.drawCircle(pos[0], pos[1], 30f, paint)
        for (secondPos in secondPosList){
            canvas.drawCircle(secondPos[0], secondPos[1], 40f, secondBorder)
            canvas.drawCircle(secondPos[0],secondPos[1], 30f, secondPaint)
        }
    }
}