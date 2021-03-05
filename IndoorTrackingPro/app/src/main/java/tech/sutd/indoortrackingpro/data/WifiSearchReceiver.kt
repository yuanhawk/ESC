package tech.sutd.indoortrackingpro.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log
import tech.sutd.indoortrackingpro.adapter.WifiSearchAdapter
import java.util.*
import javax.inject.Inject

class WifiSearchReceiver @Inject constructor(
    private val wifiManager: WifiManager,
    private val wifiSearchAdapter: WifiSearchAdapter
) : BroadcastReceiver() {
    private lateinit var scanResultList: ArrayList<ScanResult>

    private val TAG = "WifiSearchReceiver"
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