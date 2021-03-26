package tech.sutd.indoortrackingpro.data

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Handler
import android.os.Looper
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
import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.model.*
import tech.sutd.indoortrackingpro.utils.Constants
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*
import java.util.concurrent.TimeUnit

// Mapping & testing, select ap, return data collected all the access pt selected, submit to db
// Testing mode to return coordinates


class MPWorker  constructor(
        val appContext: Context,
        val workerParams: WorkerParameters,
        val realm: Realm
) : Worker(appContext, workerParams) {
    private var count = 0
    private var wifiManager = appContext.applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    private var apList = realm.where(Account::class.java).findFirst()?.mAccessPoints!!
    private var mappingPoint: MappingPoint = MappingPoint()
    private var readings: HashMap<String, ArrayList<Int>> = HashMap()
    private var handler: Handler = Handler(Looper.myLooper()!!)
    private val TAG = "MPWorker"

    private val  wifiReceiver = object : BroadcastReceiver(){
        override fun onReceive(context: Context, intent: Intent) {
            val success = intent.getBooleanExtra(WifiManager.EXTRA_RESULTS_UPDATED, false)
            if (success) {
                scanSuccess()
            } else {
                scanFailure()
            }
        }
    }
    init {
        for (ap in apList) {
            mappingPoint.accessPointSignalRecorded.add(AccessPoint(ap))
        }

        val intentFilter = IntentFilter()
        intentFilter.addAction(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION)
        appContext.registerReceiver(wifiReceiver, intentFilter)
    }

    fun startScan(){
        handler.postDelayed({
            wifiManager.startScan()
            if (count < Constants.getScanBatch()) startScan()
            else finishScan()
        }, Constants.getFetchInterval())
    }

    fun finishScan(){
        for (mac in readings.keys){
            val value = readings[mac]?.sum()?.toDouble()?.div(readings[mac]!!.size)
            for (ap in mappingPoint.accessPointSignalRecorded){
                if (mac == ap.mac) {
                    ap.rssi = value!!
                }
            }
        }

    }

    private fun scanFailure() {
        val results = wifiManager.scanResults
        Log.d(TAG, "scanFailure: $results")
    }
    private fun scanSuccess(){
        var scanResults: List<ScanResult> = wifiManager.scanResults
        count++
        for (ap in apList){
            for (scanResult in scanResults){
                if (ap.mac == scanResult.BSSID) {
                    if (readings.containsKey(ap.mac)) readings[ap.mac]!!.add(scanResult.level)
                    else {
                        var newlist = ArrayList<Int>()
                        newlist.add(scanResult.level)
                        readings[ap.mac] = newlist
                    }
                }
                //Log.d(TAG, ap.mac + scanResult.level)
            }
        }
    }

    @SuppressLint("HardwareIds")
    private fun insertIntoDb(results: List<ScanResult>) = runBlocking {

        realm.executeTransaction() { innerRealm ->
            innerRealm.insert(mappingPoint)
        }
    }

    override fun doWork(): Result {
        startScan()
        return Result.success()

    }
}