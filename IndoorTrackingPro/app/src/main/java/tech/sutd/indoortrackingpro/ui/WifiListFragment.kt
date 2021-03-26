package tech.sutd.indoortrackingpro.ui

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
import java.util.*
import kotlin.collections.ArrayList

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

    val data = arrayListOf<ArrayList<String>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = DataBindingUtil.inflate<FragmentWifiListBinding>(
            inflater, R.layout.fragment_wifi_list, container, false)



        with(binding){
            buttonSearchForWaps.setOnClickListener {

                Toast.makeText(activity,"Hello", Toast.LENGTH_SHORT).show()
                data.clear()
                for (i in 0..5) {
                    val temp = ArrayList<String>()
                    for (j in 0..3){
                        temp.add("BSSID$i")
                        temp.add("SSID$i")
                        temp.add("RSSI$i")
                    }
                    data.add(temp)
                }
            }
        }
        val adapter = WAPListAdapter(this, data)
        binding.wifiListRv.layoutManager = LinearLayoutManager(context)
        binding.wifiListRv.adapter = adapter

        return binding.root
    }


}