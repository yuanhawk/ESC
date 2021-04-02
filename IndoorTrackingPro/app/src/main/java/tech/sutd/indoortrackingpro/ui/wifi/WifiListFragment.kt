package tech.sutd.indoortrackingpro.ui.wifi

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentWifiListBinding
import tech.sutd.indoortrackingpro.ui.wifi.WAPListAdapter
import java.util.*
import kotlin.collections.ArrayList

@AndroidEntryPoint
class WifiListFragment : Fragment() {

    val data = arrayListOf<ArrayList<String>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = DataBindingUtil.inflate<FragmentWifiListBinding>(
            inflater, R.layout.fragment_wifi_list, container, false)

        with(binding){
            buttonSearchForWaps.setOnClickListener {

                Toast.makeText(activity,"Hello", Toast.LENGTH_SHORT).show()
//                data.clear()
//                for (i in 0..5) {
//                    val temp = ArrayList<String>()
//                    for (j in 0..3){
//                        temp.add("BSSID$i")
//                        temp.add("SSID$i")
//                        temp.add("RSSI$i")
//                    }
//                    data.add(temp)
//                }
            }

            buttonSelectWaps.setOnClickListener {
                Toast.makeText(activity, "WAPs Selected", Toast.LENGTH_SHORT).show()
            }

        }
        val adapter = WAPListAdapter(this, data)
        binding.wifiListRv.layoutManager = LinearLayoutManager(context)
        binding.wifiListRv.adapter = adapter

        return binding.root
    }


}