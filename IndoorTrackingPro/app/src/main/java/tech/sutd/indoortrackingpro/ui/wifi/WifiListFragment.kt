package tech.sutd.indoortrackingpro.ui.wifi

import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver
import tech.sutd.indoortrackingpro.databinding.FragmentWifiListBinding
import javax.inject.Inject

@AndroidEntryPoint
class WifiListFragment : Fragment() {

    @Inject lateinit var handler: Handler
    @Inject lateinit var adapter: WifiListAdapter
    @Inject lateinit var manager: LinearLayoutManager
    @Inject lateinit var wifiReceiver: WifiSearchReceiver

    private lateinit var binding: FragmentWifiListBinding
//    private val observer: Observer<RealmList<AP>> by lazy {
//        Observer<RealmList<AP>> { adapter.sendData(it) }
//    }

    private val viewModel: WifiViewModel by hiltNavGraphViewModels(R.id.main)

    val data = arrayListOf<ArrayList<String>>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_wifi_list, container, false)

        with(binding){
            buttonSearchForWaps.setOnClickListener {

                Toast.makeText(activity,"Initializing Wifi Scan, Please Wait...", Toast.LENGTH_SHORT).show()
            }

            buttonSelectWaps.setOnClickListener {
                Toast.makeText(activity, "WAPs Selected", Toast.LENGTH_SHORT).show()
            }
        }

        binding.wifiListRv.layoutManager = manager
        binding.wifiListRv.adapter = adapter

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        activity?.registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        viewModel.initScan(wifiReceiver).observe(viewLifecycleOwner, {
            adapter.sendData(it)
        })
    }

    override fun onPause() {
        super.onPause()
        viewModel.endScan(wifiReceiver)
        with(binding) {
            wifiListRv.layoutManager = null
            wifiListRv.adapter = null
        }
    }


}