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
class WifiService @Inject constructor(
    @ApplicationContext val context: Context,
    val wifiManager: WifiManager
) : Service() {

    private val TAG = "WifiService"

    private lateinit var results: List<ScanResult>

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
        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        context.registerReceiver(wifiScanReceiver, intentFilter)

        val success = wifiManager.startScan()
        if (!success) scanFailure()
    }

    private fun scanFailure() {
        results = wifiManager.scanResults
        Log.d(TAG, "scanFailure: $results")
    }

    private fun scanSuccess() {
        results = wifiManager.scanResults
        Log.d(TAG, "scanSuccess: $results")
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }
}