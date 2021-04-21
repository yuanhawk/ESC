package tech.sutd.indoortrackingpro.utils

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import com.otaliastudios.zoom.ZoomImageView
import io.realm.RealmList
import tech.sutd.indoortrackingpro.model.Account_Inaccuracy

class CustomZoomImageView(
    context: Context,
    attrs: AttributeSet?
) : ZoomImageView(context, attrs) {
    //the bullet point for where the user taps
    private var paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var border = Paint(Paint.ANTI_ALIAS_FLAG)
    //the bullet point for mapping points
    private var secondPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var secondBorder = Paint(Paint.ANTI_ALIAS_FLAG)
    //if we want to show all the inacuracy on map
    var inaccuracyEnabled = false
    //the position where the user taps
    var pos = floatArrayOf(0f, 0f)
    //list of positions for mapping points
    var secondPosList = listOf(floatArrayOf(0f, 0f))
    var inaccuracyList: RealmList<Account_Inaccuracy> = RealmList()
    init {
        paint.color = Color.GRAY
        border.color = Color.WHITE
        secondBorder.color = Color.WHITE
        secondPaint.color = Color.CYAN
    }

    override fun setEnabled(enabled: Boolean) {
        if (enabled){
            paint.color = Color.BLUE}
        else {
            paint.color = Color.GRAY
        }

    }


    @SuppressLint("DrawAllocation")
    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawCircle(pos[0], pos[1], 40f, border)
        canvas.drawCircle(pos[0], pos[1], 30f, paint)
        for (secondPos in secondPosList){
            canvas.drawCircle(secondPos[0], secondPos[1], 40f, secondBorder)
            canvas.drawCircle(secondPos[0],secondPos[1], 30f, secondPaint)
        }
        if (inaccuracyEnabled){
            Log.d("Draw", "drawing")
            val redPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            redPaint.color = Color.RED
            val yellowPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            yellowPaint.color = Color.YELLOW
            val greenPaint = Paint(Paint.ANTI_ALIAS_FLAG)
            greenPaint.color = Color.GREEN
            for (inaccuracyRecord: Account_Inaccuracy in inaccuracyList){
                Log.d("Draw", ""+ inaccuracyRecord.x +" " + inaccuracyRecord.y)
                if (inaccuracyRecord.inaccuracy > 10) canvas.drawCircle(inaccuracyRecord.x.toFloat(), inaccuracyRecord.y.toFloat(), 20f, redPaint)
                else if (inaccuracyRecord.inaccuracy > 5) canvas.drawCircle(inaccuracyRecord.x.toFloat(), inaccuracyRecord.y.toFloat(), 20f, yellowPaint)
                else canvas.drawCircle(inaccuracyRecord.x.toFloat(), inaccuracyRecord.y.toFloat(), 20f, greenPaint)
            }
        }
    }
}