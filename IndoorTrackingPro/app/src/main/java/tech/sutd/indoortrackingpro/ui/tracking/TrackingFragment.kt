package tech.sutd.indoortrackingpro.ui.tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentTrackingBinding

@AndroidEntryPoint
class TrackingFragment : Fragment() {

    private lateinit var binding: FragmentTrackingBinding

    private val viewModel: TrackingViewModel by hiltNavGraphViewModels(R.id.main)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_tracking, container, false)

        viewModel.coordinates().observe(viewLifecycleOwner, {
            with(binding) {
                trackingMap.setImageResource(R.drawable.map)
                trackingMap.isEnabled = true
                trackingMap.pos[0] = it.longitude.toFloat()
                trackingMap.pos[1] = it.latitude.toFloat()
                trackingMap.invalidate()
            }
        })
        
        viewModel.initWifiScan(this)
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
        viewModel.cancelWifiScan(this)
    }
}