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
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentTrackingBinding
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.ui.wifi.WifiViewModel
import tech.sutd.indoortrackingpro.utils.touchCoord
import tech.sutd.indoortrackingpro.utils.trackingCoord
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class TrackingFragment : Fragment() {
    @Inject lateinit var bundle: Bundle
    private lateinit var binding: FragmentTrackingBinding
    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracking, container, false)


        with(binding){
            trackingMap.setOnTouchListener(
                object : View.OnTouchListener {
                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        if (event?.action == MotionEvent.ACTION_UP) {
                            val z = trackingRealFloor.text.toString().toInt().toFloat()
                            val location = floatArrayOf(event.x, event.y, z)
                            //Log.d(ContentValues.TAG, "onCreate: ${location[0]}, ${location[1]}")
                            val trackingLocation = floatArrayOf(trackingMap.pos[0], trackingMap.pos[1], trackingPredictedFloor.text.toString().toFloat())

                            bundle.putFloatArray(touchCoord, location)
                            bundle.putFloatArray(trackingCoord, trackingLocation)
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
        return binding.root
    }

    override fun onResume() {
        super.onResume()

    }
    override fun onPause() {
        super.onPause()
    }
}