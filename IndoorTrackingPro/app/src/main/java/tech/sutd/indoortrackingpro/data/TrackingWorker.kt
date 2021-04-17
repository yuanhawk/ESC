package tech.sutd.indoortrackingpro.data

import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import tech.sutd.indoortrackingpro.utils.intentFilter
import tech.sutd.indoortrackingpro.utils.intentKey
import java.util.concurrent.TimeUnit

@HiltWorker
class TrackingWorker @AssistedInject constructor(
        @Assisted val appContext: Context,
        @Assisted workerParams: WorkerParameters,
        private val wifiWrapper: WifiWrapper,
        val workManager: WorkManager
) : Worker(appContext, workerParams) {

    private val TAG = "TrackingWorker"
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        wifiWrapper.startScan()
        // get networks
        val mResults: List<ScanResult> = wifiWrapper.scanResults()
        Log.d(TAG, "New scan result: (" + mResults.size + ") networks found")
        val mappingPoint = Account_mMappingPoints(mResults)
        val intent = Intent(intentFilter)
        intent.putExtra(intentKey, mappingPoint)
        LocalBroadcastManager.getInstance(appContext).sendBroadcast(intent)
        workManager.enqueue(OneTimeWorkRequestBuilder<TrackingWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .build())
        return Result.success()
    }
}