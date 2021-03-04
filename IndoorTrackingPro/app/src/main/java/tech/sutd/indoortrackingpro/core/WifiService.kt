package tech.sutd.indoortrackingpro.core

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.IBinder
import android.util.Log
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@AndroidEntryPoint
class WifiService : Service() {
    private val TAG = "WifiService"

    private lateinit var results: List<ScanResult>

    private lateinit var wifiManager: WifiManager


    val wifiScanReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val success = intent?.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success == true) scanSuccess()
            else scanFailure()
        }
    }

    @SuppressWarnings("deprecation")
    override fun onCreate() {
        super.onCreate()

        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val intentFilter = IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        registerReceiver(wifiScanReceiver, intentFilter)


        val success = wifiManager.startScan()
        if (!success) scanFailure()
    }


    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(wifiScanReceiver)
    }


    private fun scanFailure() {
        results = wifiManager.scanResults
        Log.d(TAG, "scanFailure: $results")
    }

    private fun scanSuccess() {
        results = wifiManager.scanResults

        for (scanResults in results) {
            val ssid = scanResults.SSID
            val level = scanResults.level
            Log.d(TAG, "scanSuccess: $ssid, $level")
        }

    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}