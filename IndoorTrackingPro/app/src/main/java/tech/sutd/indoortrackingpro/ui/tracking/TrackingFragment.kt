package tech.sutd.indoortrackingpro.ui.tracking

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentTrackingBinding

@AndroidEntryPoint
class TrackingFragment : Fragment() {
    lateinit var  binding: FragmentTrackingBinding
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate<FragmentTrackingBinding>(inflater, R.layout.fragment_tracking, container, false)

//        // Mapping Button to go from trackingFragment -> mappingFragment
//        mappingButton.setOnClickListener { view ->
//            view?.findNavController()?.navigate(R.id.action_mappingFragment_to_trackingFragment)
//        }
        val model: TrackingViewModel by viewModels()
        model.initWifiScan(this)
        with(binding){
            trackingMap.setImageResource(R.drawable.map)
            trackingMap.isEnabled = true;
            trackingMap.pos[0] = 300.0f
            trackingMap.pos[1] = 500.0f
            trackingMap.invalidate();
        }


        return binding.root
    }
}