package tech.sutd.indoortrackingpro.adapter
import android.content.Context
import android.view.ContextThemeWrapper
import androidx.recyclerview.widget.RecyclerView
import android.view.GestureDetector
import android.view.View
import android.view.MotionEvent

class RvItemClickListener(context: Context,
                          recyclerView: RecyclerView,
                          private val listener: OnItemClickListener?): RecyclerView.OnItemTouchListener {

    private val gestureDetector = GestureDetector(context, object: GestureDetector.SimpleOnGestureListener() {
        override fun onSingleTapUp(e: MotionEvent?): Boolean {
            return true;
        }
    })
    public interface OnItemClickListener{
        fun onItemClick(view: View, position: Int)
    }

    override fun onInterceptTouchEvent(rv: RecyclerView, e: MotionEvent): Boolean {
        val childView: View? = rv.findChildViewUnder(e.x, e.y)
        if (childView != null && listener != null && gestureDetector.onTouchEvent(e)){
            listener.onItemClick(childView, rv.getChildAdapterPosition(childView))
        }
        return false
    }

    override fun onTouchEvent(rv: RecyclerView, e: MotionEvent) {
    }

    override fun onRequestDisallowInterceptTouchEvent(disallowIntercept: Boolean) {
    }


}