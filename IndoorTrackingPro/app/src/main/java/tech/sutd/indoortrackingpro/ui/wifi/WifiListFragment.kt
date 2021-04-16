package tech.sutd.indoortrackingpro.ui.wifi

import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.MutableLiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver
import tech.sutd.indoortrackingpro.databinding.FragmentWifiListBinding
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.MappingPoint
import tech.sutd.indoortrackingpro.ui.MainActivity
import tech.sutd.indoortrackingpro.ui.adapter.WifiListAdapter
import tech.sutd.indoortrackingpro.utils.RvItemClickListener
import javax.inject.Inject

@AndroidEntryPoint
class WifiListFragment : Fragment() {

    private val TAG = "WifiListFragment"

    @Inject lateinit var handler: Handler
    @Inject lateinit var adapter: WifiListAdapter
    @Inject lateinit var manager: LinearLayoutManager
    @Inject lateinit var wifiReceiver: WifiSearchReceiver
    @Inject lateinit var config: RealmConfiguration
    lateinit var binding: FragmentWifiListBinding

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
        }

        binding.wifiListRv.layoutManager = manager
        binding.wifiListRv.adapter = adapter

        activity?.applicationContext?.let {
            RvItemClickListener(
                it, object : RvItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
//                        val realm2 = Realm.getInstance(config)
//                        var mpList: RealmList<MappingPoint> = realm2.where(Account::class.java).findFirst()?.mMappingPoints!!
                        if (!MainActivity.mpAdded){
                        val accessPoint = AccessPoint()
                        accessPoint.mac = adapter.wifiList[position].BSSID
                        accessPoint.ssid = adapter.wifiList[position].SSID
                        Log.d(TAG, "onItemClick: ${accessPoint.mac}")

                        viewModel.insertAp(accessPoint)
                        MainActivity.apAdded = true

                        Toast.makeText(context, "Added to WAP list successfully", Toast.LENGTH_SHORT).show()
                        findNavController().popBackStack(R.id.selectedAPListFragment, false)
                        findNavController().navigate(R.id.action_wifiListFragment_to_selectedAPListFragment)
                    } else{
                        Toast.makeText(context, "Unable to add AP after adding MP",Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            )
        }?.let { binding.wifiListRv.addOnItemTouchListener(it) }

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