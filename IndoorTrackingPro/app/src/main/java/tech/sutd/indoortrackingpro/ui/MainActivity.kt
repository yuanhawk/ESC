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

                        map.pos = location
                        map.invalidate()
                        return true
                    }
                    return false
                }
            })

//            map.setOnClickListener{v ->
//                val location = IntArray(2)
//                v.getLocationOnScreen(location)
//                Log.d(TAG, "onCreate: ${location[0]}, ${location[1]}")
//
//                val floats = FloatArray(2)
//                for (i in 0..1) {
//                    floats[i] = location[i].toFloat()
//                }
//
//                map.elevation = 0f
//                binding.pointer.visibility = View.VISIBLE
//                binding.pointer.isEnabled = true
//                binding.pointer.pos = floats
//                binding.pointer.elevation = 90f
//                map.invalidate()
//            }
        }
    }

}








