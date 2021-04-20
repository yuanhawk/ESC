package tech.sutd.indoortrackingpro.ui.tracking

import android.annotation.SuppressLint
import android.content.ContentValues
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentTrackingBinding
import tech.sutd.indoortrackingpro.utils.touchCoord
import tech.sutd.indoortrackingpro.utils.trackingCoord
import javax.inject.Inject

@AndroidEntryPoint
class TrackingFragment : Fragment() {
    @Inject lateinit var bundle: Bundle
    private lateinit var binding: FragmentTrackingBinding
    val model:  TrackingViewModel by viewModels()
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracking, container, false)

        model.coordinates().observe(viewLifecycleOwner, {
            with(binding) {
                trackingMap.setImageResource(R.drawable.map)
                trackingMap.isEnabled = true
                trackingMap.pos[0] = it.longitude.toFloat()
                trackingMap.pos[1] = it.latitude.toFloat()
                trackingMap.invalidate()
            }
        })

        //TODO
        with(binding){
            trackingMap.setOnTouchListener(
                object : View.OnTouchListener {
                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        if (event?.action == MotionEvent.ACTION_UP) {
                            val location = floatArrayOf(event.x, event.y)
                            //Log.d(ContentValues.TAG, "onCreate: ${location[0]}, ${location[1]}")


                            bundle.putFloatArray(touchCoord, location)
                            bundle.putFloatArray(trackingCoord, trackingMap.pos)
                            findNavController().navigate(
                                R.id.action_trackingFragment_to_recordInaccuracyFragment,
                                bundle
                            )

                            trackingMap.isEnabled = true
                            trackingMap.secondPosList = listOf(location)
                            trackingMap.invalidate()
                            return true
                        }
                        return false
                    }
                })
        }
        model.initWifiScan(this)
//        with(binding){
//            trackingMap.setImageResource(R.drawable.map)
//            trackingMap.isEnabled = true
//            trackingMap.pos[0] = 300.0f
//            trackingMap.pos[1] = 500.0f
//            trackingMap.invalidate()
//        }


        return binding.root
    }

    override fun onPause() {
        super.onPause()
        model.cancelWifiScan(this)
    }
}