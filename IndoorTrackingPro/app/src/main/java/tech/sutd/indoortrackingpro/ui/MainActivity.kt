package tech.sutd.indoortrackingpro.ui


import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.otaliastudios.zoom.ZoomImageView
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding




@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)
    private val TAG : String = "Map"




    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
            val map : ZoomImageView = findViewById(R.id.map)
            map.setImageResource(R.drawable.map)

            View.OnCapturedPointerListener(motionEvent : MotionEvent): Boolean{
                val horizontalOffset: Float = motionEvent.x
                val verticalOffset: Float = motionEvent.y
                Toast.makeText(
                    this@MainActivity,
                    "x : $horizontalOffset , y : $verticalOffset",
                    Toast.LENGTH_SHORT
                ).show();
                return true


             }


            }
    }




    }








