package tech.sutd.indoortrackingpro.data

import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.work.WorkerInject
import androidx.work.*
import java.util.concurrent.TimeUnit

class WifiWorker @WorkerInject constructor(
    @Assisted val appContext: Context,
    @Assisted workerParams: WorkerParameters,
    private val wifiManager: WifiManager,
    private val workManager: WorkManager
) : Worker(appContext, workerParams) {

    private val TAG = "WifiWorker"
    private lateinit var results: List<ScanResult>

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
        workManager.enqueue(workRequest)
    }

    override fun doWork(): Result {
        val success = wifiManager.startScan()
        if (success) scanSuccess()
        else scanFailure()
        return Result.success()
    }
}