package tech.sutd.indoortrackingpro.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val TAG = "MainActivity"
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)

    private lateinit var location: FloatArray

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            map.setImageResource(R.drawable.map)

            map.setOnTouchListener(object : View.OnTouchListener{
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    if (event?.action == MotionEvent.ACTION_UP) {
                        location = floatArrayOf(event.x, event.y)
                        Log.d(TAG, "onCreate: ${location[0]}, ${location[1]}")

                        map.isEnabled = true
                        map.pos = location
                        map.invalidate()
                        return true
                    }
                    return false
                }
            })
        }
    }

}








