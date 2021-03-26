package tech.sutd.indoortrackingpro.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentTrackingBinding

@AndroidEntryPoint
class TrackingFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentTrackingBinding>(inflater, R.layout.fragment_tracking, container, false)

//        // Mapping Button to go from trackingFragment -> mappingFragment
//        mappingButton.setOnClickListener { view ->
//            view?.findNavController()?.navigate(R.id.action_mappingFragment_to_trackingFragment)
//        }

        return binding.root
    }
}