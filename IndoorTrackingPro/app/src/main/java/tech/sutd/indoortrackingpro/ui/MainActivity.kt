package tech.sutd.indoortrackingpro.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding
import java.util.*

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
                        Toast.makeText(this@MainActivity,"Current location is: ${location[0]}, ${location[1]}", Toast.LENGTH_SHORT).show()

                        map.isEnabled = true
                        map.pos = location
                        map.invalidate()
                        return true
                    }
                    return false
                }
            })


            var  myTimerTask : CustomTimerTask = CustomTimerTask(map)
            var myTimer : Timer = Timer()
            myTimer.schedule(myTimerTask,100 , 1000)








        }
    }

//    fun setLocation( map : CustomZoomImageView , x : Float , y : Float){
//        map.isEnabled = true
//        location = floatArrayOf(x,y)
//        map.pos = location
//        map.invalidate()
//    }



    }










