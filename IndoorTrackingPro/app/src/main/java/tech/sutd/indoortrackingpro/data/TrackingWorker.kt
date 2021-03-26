package tech.sutd.indoortrackingpro.data

import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.*
import kotlinx.coroutines.*
import tech.sutd.indoortrackingpro.model.MappingPoint
import tech.sutd.indoortrackingpro.utils.Constants
import java.util.*
import java.util.concurrent.TimeUnit


class TrackingWorker(
        val appContext: Context,
        workerParams: WorkerParameters,

) : Worker(appContext, workerParams) {

    private val TAG = "TrackingWorker"
    private val wifiManager = appContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        wifiManager.startScan()
        // get networks
        val mResults: List<ScanResult> = wifiManager.getScanResults()
        Log.d(TAG, "New scan result: (" + mResults.size + ") networks found")
        val mappingPoint = MappingPoint(mResults)
        val intent = Intent(Constants.getIntentFilter())
        intent.putExtra(Constants.getIntentKey(), mappingPoint)
        LocalBroadcastManager.getInstance(appContext).sendBroadcast(intent)
        WorkManager.getInstance(appContext).enqueue(OneTimeWorkRequestBuilder<TrackingWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .build())
        return Result.success()
    }
}