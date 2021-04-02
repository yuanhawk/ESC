package tech.sutd.indoortrackingpro.data

import android.annotation.SuppressLint
import android.content.Context
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.hilt.work.HiltWorker
import androidx.work.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import kotlinx.coroutines.*
import tech.sutd.indoortrackingpro.model.AP
import tech.sutd.indoortrackingpro.model.ListAP
import java.util.concurrent.TimeUnit

// Mapping & testing, select ap, return data collected all the access pt selected, submit to db
// Testing mode to return coordinates

@HiltWorker
class WifiWorker @AssistedInject constructor(
        @Assisted val appContext: Context,
        @Assisted workerParams: WorkerParameters,
        private val wifiManager: WifiManager,
        private val workManager: WorkManager,
        private val config: RealmConfiguration
) : Worker(appContext, workerParams) {

    private val TAG = "WifiWorker"
    private lateinit var results: List<ScanResult>

    val workRequest = OneTimeWorkRequestBuilder<WifiWorker>()
            .setInitialDelay(30, TimeUnit.SECONDS)
            .build()

    private fun scanFailure() {
        workManager.enqueue(workRequest)
        results = wifiManager.scanResults
        Log.d(TAG, "scanFailure: $results")
    }

    private fun scanSuccess(): Data {
        results = wifiManager.scanResults
        CoroutineScope(Dispatchers.IO).launch { insertIntoDb(results) }
        val data = Data.Builder()
                .putStringArray("scanResults", arrayOf(results.toTypedArray().toString()))
                .build()
        return data
    }

    @SuppressLint("HardwareIds")
    private fun insertIntoDb(results: List<ScanResult>) = runBlocking {
        val realm = Realm.getInstance(config)
        val apList = RealmList<AP>()
        for (scanResults in results) {
            val ap = AP(
                scanResults.BSSID,
                scanResults.SSID,
                scanResults.level
            )
            apList.add(ap)
            Log.d(TAG, "insertIntoDb: ${scanResults.BSSID}, ${scanResults.SSID}, ${scanResults.level}")
        }

        val listAp = ListAP(apList)
        realm.executeTransactionAsync { innerRealm ->
            innerRealm.insert(listAp)
        }
        realm.close()
    }

    @Suppress("DEPRECATION")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun doWork(): Result {
        if (wifiManager.startScan())
            return Result.success(scanSuccess())
        else scanFailure()
        return Result.success()
    }
}