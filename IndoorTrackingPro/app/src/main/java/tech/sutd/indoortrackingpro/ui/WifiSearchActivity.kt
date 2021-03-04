package tech.sutd.indoortrackingpro.ui

import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.adapter.WifiSearchAdapter
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver
import tech.sutd.indoortrackingpro.databinding.ActivityWifiSearchBinding
import javax.inject.Inject

@AndroidEntryPoint
class WifiSearchActivity : BaseActivity() {

    val TAG = "Wifi Search Activity"
    @Inject lateinit var wifiManager: WifiManager
    @Inject lateinit var layoutManager: LinearLayoutManager

    @Inject lateinit var wifiReceiver: WifiSearchReceiver
    @Inject lateinit var wifiSearchAdapter: WifiSearchAdapter
    @Inject lateinit var itemDecoration: DividerItemDecoration
    @Inject lateinit var handler: Handler
    @Inject lateinit var itemAnimator: DefaultItemAnimator

    private val binding by binding<ActivityWifiSearchBinding>(R.layout.activity_wifi_search)

    //TODO: Use wifiService
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "set content")
        init()
        @Suppress("DEPRECATION")
        if (!wifiManager.isWifiEnabled) wifiManager.isWifiEnabled = true
        binding.wifiListRv.layoutManager = layoutManager
        //TODO: change to other decoration
        binding.wifiListRv.addItemDecoration(itemDecoration)
        binding.wifiListRv.itemAnimator = itemAnimator
        binding.wifiListRv.adapter = wifiSearchAdapter

    }

    override fun onResume() {

        //if (!wifiManager.isWifiEnabled) wifiManager.isWifiEnabled = true
        registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        //TODO: Deprecation
        handler.postDelayed({
            wifiManager.startScan()
            Thread.currentThread().interrupt()
        }, 1000)
        super.onResume()
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(wifiReceiver)
        binding.wifiListRv.removeItemDecoration(itemDecoration)
        binding.wifiListRv.layoutManager = null
        binding.wifiListRv.itemAnimator = null
        binding.wifiListRv.adapter = null
    }
    private fun init(){
        binding.refreshBtn.setOnClickListener {
            handler.postDelayed({
                @Suppress("DEPRECATION")
                wifiManager.startScan()
            }, 1000)
        }
    }

}