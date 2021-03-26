package tech.sutd.indoortrackingpro.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.databinding.DataBindingUtil.setContentView
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentMainBinding
import tech.sutd.indoortrackingpro.util.CustomZoomImageView
import java.util.*

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val TAG = "MainFragment"

    private lateinit var navController: NavController

    private lateinit var location: FloatArray

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
                inflater, R.layout.fragment_main, container, false)

        with(binding) {
            buttonWifi.setOnClickListener { view ->
                view?.findNavController()?.navigate(R.id.action_mainFragment_to_wifiListFragment)
            }

            map.setImageResource(R.drawable.map)

            map.setOnTouchListener(object : View.OnTouchListener {
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    if (event?.action == MotionEvent.ACTION_UP) {
                        location = floatArrayOf(event.x, event.y)
                        Log.d(TAG, "onCreate: ${location[0]}, ${location[1]}")
                        Toast.makeText(activity,"Current location is: ${location[0]}, ${location[1]}", Toast.LENGTH_SHORT).show()

                        map.isEnabled = true
                        map.pos = location
                        map.invalidate()
                        return true
                    }
                    return false
                }
            })


            var myTimerTask: CustomTimerTask = CustomTimerTask(map)
            var myTimer: Timer = Timer()
            myTimer.schedule(myTimerTask, 100, 1000)

            fun setLocation(map: CustomZoomImageView, x: Float, y: Float) {
                map.isEnabled = true
                location = floatArrayOf(x, y)
                map.pos = location
                map.invalidate()
            }

            return binding.root
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
    }

}