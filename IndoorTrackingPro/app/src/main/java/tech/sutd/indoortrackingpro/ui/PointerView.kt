package tech.sutd.indoortrackingpro.ui

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View

class PointerView(
    context: Context,
    attrs: AttributeSet?
) : View(context, attrs) {
    private var paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    var pos = floatArrayOf(0f, 0f)

    init {
        paint.color = Color.GRAY
    }
    
    override fun setEnabled(enabled: Boolean) {
        if (enabled)
            paint.color = Color.RED
        else
            paint.color = Color.GRAY
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        canvas?.drawCircle(pos[0]/3.5f, pos[1]*1.5f, 30f, paint)
    }
}