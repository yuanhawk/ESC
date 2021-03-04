package tech.sutd.indoortrackingpro.ui
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.PersistableBundle
import android.util.Log
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.adapter.WifiSearchAdapter
import java.util.*

class WifiSearchActivity(): AppCompatActivity(){
    val TAG = "Wifi Search Activity"
    private lateinit var refreshButton: Button
    private lateinit var wifiListRecyclerView: RecyclerView
    private lateinit var layoutManager: LinearLayoutManager

    private  lateinit var wifiManager: WifiManager
    private  lateinit var wifiReceiver: BroadcastReceiver
    private lateinit var scanResultList: ArrayList<ScanResult>
    private  lateinit var wifiSearchAdapter: WifiSearchAdapter
    @Suppress("DEPRECATION")
    private val handler = Handler()
    //TODO: Use wifiService
    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wifisearch)
        Log.d(TAG, "set content")
        init()
        wifiReceiver = WifiSearchReceiver()
        wifiSearchAdapter = WifiSearchAdapter()
        wifiManager = application.getSystemService(Context.WIFI_SERVICE) as WifiManager
        @Suppress("DEPRECATION")
        if (!wifiManager.isWifiEnabled) wifiManager.isWifiEnabled = true
        layoutManager = LinearLayoutManager(this)
        wifiListRecyclerView.layoutManager = layoutManager
        //TODO: change to other decoration
        wifiListRecyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                DividerItemDecoration.VERTICAL
            )
        )
        wifiListRecyclerView.itemAnimator = DefaultItemAnimator()
        wifiListRecyclerView.adapter = wifiSearchAdapter

    }

    override fun onResume() {

        //if (!wifiManager.isWifiEnabled) wifiManager.isWifiEnabled = true
        registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        //TODO: Deprecation
        handler.postDelayed({
            wifiManager.startScan()
        }, 1000)
        super.onResume()
    }

    override fun onPause() {
        unregisterReceiver(wifiReceiver)
        super.onPause()
    }
    private fun init(){
        refreshButton = findViewById(R.id.wifi_search_refresh_button)
        wifiListRecyclerView = findViewById(R.id.wifi_search_recyclerview)
        refreshButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                handler.postDelayed(object : Runnable {
                    override fun run() {
                        @Suppress("DEPRECATION")
                        wifiManager.startScan();
                    }
                }, 1000)
            }
        })

    }
    inner class WifiSearchReceiver(): BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (!success){
                Log.v(TAG, "scan failed")
                return
            }
            scanResultList = wifiManager.scanResults as ArrayList<ScanResult>
            Collections.sort(scanResultList, object : Comparator<ScanResult> {
                override fun compare(o1: ScanResult?, o2: ScanResult?): Int {
                    if (o1 == null) {
                        return 1
                    }
                    if (o2 == null) {
                        return -1
                    }
                    return o2.level - o1.level
                }
            })
            wifiSearchAdapter.wifiList = scanResultList
            wifiSearchAdapter.notifyDataSetChanged()
            val wifiCount = scanResultList.size
            Log.v(TAG, "Wi-Fi Scan Results ... Count:$wifiCount")
            for (i in 0..wifiCount - 1) {
                Log.v(TAG, "  BSSID       =" + scanResultList[i].BSSID)
                Log.v(TAG, "  SSID        =" + scanResultList[i].SSID)
                Log.v(TAG, "  Capabilities=" + scanResultList[i].capabilities)
                Log.v(TAG, "  Frequency   =" + scanResultList[i].frequency)
                Log.v(TAG, "  Level       =" + scanResultList[i].level)
                Log.v(TAG, "---------------")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        /*if (wifiManager.isWifiEnabled) {
            wifiManager.isWifiEnabled = false
        }*/
    }
}