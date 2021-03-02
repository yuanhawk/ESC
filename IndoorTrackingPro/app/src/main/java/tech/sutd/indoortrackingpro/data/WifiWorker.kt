package tech.sutd.indoortrackingpro.data

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltWorker
class WifiWorker @AssistedInject constructor(
        @Assisted val appContext: Context,
        @Assisted workerParams: WorkerParameters,
) : Worker(appContext, workerParams) {

    private val TAG = "WifiWorker"
    private lateinit var results: List<ScanResult>
    private lateinit var wifiManager: WifiManager

    val workRequest = OneTimeWorkRequestBuilder<WifiWorker>()
            .setInitialDelay(10, TimeUnit.SECONDS)
            .build()


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
        WorkManager.getInstance(appContext).enqueue(workRequest)
    }

    override fun doWork(): Result {
        wifiManager = appContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        val success = wifiManager.startScan()
        if (success) scanSuccess()
        else scanFailure()
        return Result.success()
    }
}