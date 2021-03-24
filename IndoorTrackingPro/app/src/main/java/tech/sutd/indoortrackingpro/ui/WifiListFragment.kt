package tech.sutd.indoortrackingpro.ui

import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentWifiListBinding
import javax.inject.Inject

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [WifiListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */

@AndroidEntryPoint
class WifiListFragment : Fragment() {

//    @Inject lateinit var wifiManager: WifiManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        val binding = DataBindingUtil.inflate<FragmentWifiListBinding>(
            inflater, R.layout.fragment_wifi_list, container, false)

//        with(binding){
//            buttonSearchForWaps.setOnClickListener {
//                @Suppress("DEPRECATION")
//                wifiManager.isWifiEnabled = true
//            }
//        }

        return binding.root
    }


}