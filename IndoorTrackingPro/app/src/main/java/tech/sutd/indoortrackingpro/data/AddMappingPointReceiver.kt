package tech.sutd.indoortrackingpro.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.os.Handler
import android.util.Log
import android.widget.Toast
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tech.sutd.indoortrackingpro.datastore.Preferences
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints_accessPointsSignalRecorded
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import tech.sutd.indoortrackingpro.utils.*
import javax.inject.Inject

class AddMappingPointReceiver @Inject constructor(
    var context: Context,
    var handler: Handler,
    var realm: Realm,
    var wifiWrapper: WifiWrapper,
    var pref: Preferences
) : BroadcastReceiver() {

//    private lateinit var binding: AddMappingBinding

    var readings: HashMap<String, ArrayList<Int>> = HashMap()
    var done = false
    var count = 0
    var apList: RealmList<Account_mAccessPoints>? =
        realm.where(Account::class.java).findFirst()?.mAccessPoints
    var mappingPoint = Account_mMappingPoints()
    val TAG = "addingMappingReceiver"

    init {
        if (apList != null) {
            if (apList?.size == 0) {
                Toast.makeText(
                    context,
                    "Please add Access Points first!",
                    Toast.LENGTH_LONG
                ).show()
            }

            for (ap in apList!!) {
                Log.d(TAG, ap.mac)
                mappingPoint.accessPointSignalRecorded.add(
                    Account_mMappingPoints_accessPointsSignalRecorded(ap)
                )
            }
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        val scanResults: List<ScanResult> = wifiWrapper.scanResults()
        count++
        if (apList != null) {
            for (ap in apList!!) {
                for (scanResult in scanResults) {
                    if (ap.mac == scanResult.BSSID) {
                        if (readings.containsKey(ap.mac)) readings[ap.mac]!!.add(scanResult.level)
                        else {
                            val newlist = ArrayList<Int>()
                            newlist.add(scanResult.level)
                            readings[ap.mac] = newlist
                        }
                    }
                    Log.d(TAG, ap.mac + scanResult.level)
                }
            }
        }
    }

    fun start() {
        count = 0
        readings.clear()
        if (done) {
            GlobalScope.launch { pref.scanDone(false) }
            done = false
            startScan()
        }
    }

    private fun startScan() {
        handler.postDelayed({
            wifiWrapper.startScan()
            if (count < scanBatch) startScan()
            else finishScan()
        }, fetchInterval)
    }

    private fun finishScan() {
        done = true
        GlobalScope.launch { pref.scanDone(true) }
        for (mac in readings.keys) {
            val value = readings[mac]?.sum()?.toDouble()?.div(readings[mac]!!.size)
            for (ap in mappingPoint.accessPointSignalRecorded) {
                if (mac == ap.mac) {
                    ap.rssi = value!!
                    Log.d(TAG, "$mac $value")
                }
            }
        }
//        binding.yesButtonAddMapping.isEnabled = true
//        Toast.makeText(
//            context,
//            "Scanning is done, press button to save the mappingPoint",
//            Toast.LENGTH_LONG
//        ).show()
        //activity.rvAdapter.notifyDataSetChanged()
    }
}