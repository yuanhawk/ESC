package tech.sutd.indoortrackingpro.ui.tracking

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.asLiveData
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentTrackingBinding
import tech.sutd.indoortrackingpro.datastore.Preferences
import tech.sutd.indoortrackingpro.utils.hideKeyboardFrom
import tech.sutd.indoortrackingpro.utils.touchCoord
import tech.sutd.indoortrackingpro.utils.trackingCoord
import javax.inject.Inject
import kotlin.math.roundToInt


@AndroidEntryPoint
class TrackingFragment : Fragment() {

    private val TAG = "TrackingFragment"

    @Inject lateinit var bundle: Bundle
    @Inject lateinit var pref: Preferences

    private lateinit var binding: FragmentTrackingBinding
    val model:  TrackingViewModel by hiltNavGraphViewModels(R.id.main)

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracking, container, false)

        with(binding) {

            pref.floorNum.asLiveData().observe(viewLifecycleOwner, { floorNum ->
                trackingRealFloor.setText(floorNum.toString())

                if (floorNum == 1) {
                    model.coordinates().observe(viewLifecycleOwner, {
                        trackingMap.setImageResource(R.drawable.map)
                        trackingMap.isEnabled = true
                        trackingMap.pos[0] = it.longitude.toFloat()
                        trackingMap.pos[1] = it.latitude.toFloat()
                        if (it.z.isNaN()) trackingPredictedFloor.text = 0.toString()
                        else trackingPredictedFloor.text = it.z.roundToInt().toString()
                        trackingMap.invalidate()
                        Log.d("prediction", "" + trackingMap.pos[0] + "   " + trackingMap.pos[1])
                    })
                } else if (floorNum == 2) {
                    model.coordinates().observe(viewLifecycleOwner, {
                        trackingMap.setImageResource(R.drawable.map_2)
                        trackingMap.isEnabled = true
                        trackingMap.pos[0] = it.longitude.toFloat()
                        trackingMap.pos[1] = it.latitude.toFloat()
                        if (it.z.isNaN()) trackingPredictedFloor.text = 0.toString()
                        else trackingPredictedFloor.text = it.z.roundToInt().toString()
                        trackingMap.invalidate()
                        Log.d("prediction", "" + trackingMap.pos[0] + "   " + trackingMap.pos[1])
                    })
                }
            })
        }

        model.floorNumber.observe(viewLifecycleOwner, {
            binding.trackingMap.inaccuracyList =
                model.getInaccuracyListFloor(model.floorNumber.value!!)
            binding.trackingMap.inaccuracyEnabled = true
            binding.trackingMap.invalidate()
        })
        with(binding){

            trackingChangeFloorButton.setOnClickListener {
                model.floorNumber.value =  binding.trackingRealFloor.text.toString().toInt()
            }

            trackingMap.inaccuracyList = model.getInaccuracyListFloor(model.floorNumber.value!!)

            trackingMap.setOnTouchListener(
                object : View.OnTouchListener {
                    override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                        if (event?.action == MotionEvent.ACTION_UP) {
                            val z = trackingRealFloor.text.toString().toInt().toFloat()
                            val location = floatArrayOf(event.x, event.y, z)
                            //Log.d(ContentValues.TAG, "onCreate: ${location[0]}, ${location[1]}")
                            val trackingLocation = floatArrayOf(
                                trackingMap.pos[0],
                                trackingMap.pos[1],
                                trackingPredictedFloor.text.toString().toFloat()
                            )

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
        model.initWifiScan(this)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        with(binding) {
            trackingChangeFloorButton.setOnClickListener {
                val int = trackingRealFloor.text.toString().toInt()
                Log.d(TAG, "onCreateView: ${trackingRealFloor.text}")
                if (int == 1 || int == 2)
                    GlobalScope.launch { pref.floorNum(int) }
                view?.let { it1 -> context?.let { it2 -> hideKeyboardFrom(it2, it1) } }
            }
        }
    }

    override fun onPause() {
        super.onPause()
        model.cancelWifiScan(this)
    }

}