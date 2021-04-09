package tech.sutd.indoortrackingpro.data

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import io.realm.Realm
import io.realm.RealmList
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.MappingPoint
import tech.sutd.indoortrackingpro.ui.mapping.AddMappingFragment
import tech.sutd.indoortrackingpro.utils.*

class AddMappingPointReceiver(var fragment: AddMappingFragment, var realm: Realm): BroadcastReceiver() {

    var readings: HashMap<String, ArrayList<Int>> = HashMap()
    var done = true
    var count = 0
    var handler: Handler = Handler(Looper.myLooper()!!)
    var apList: RealmList<AccessPoint> = realm.where(Account::class.java).findFirst()?.mAccessPoints!!
    var mappingPoint= MappingPoint()
    var wifiManager = fragment.requireActivity().applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
    val TAG = "addingMappingReceiver"
    init {
        if (apList.size == 0){
            Toast.makeText(fragment.requireActivity(), "Please add Access Points first!", Toast.LENGTH_LONG).show()
        }

        for (ap in apList){
            Log.d(TAG, ap.mac)
            mappingPoint.accessPointSignalRecorded.add(AccessPoint(ap))
        }
    }
    override fun onReceive(context: Context?, intent: Intent?) {
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
                Log.d(fragment.TAG, ap.mac + scanResult.level)
            }
        }
    }

    fun start(){

        if (done){
            done = false
            startScan()
        }
    }

    private fun startScan(){
        handler.postDelayed({
            wifiManager.startScan()
            if (count < scanBatch) startScan()
            else finishScan()
        }, fetchInterval)
    }

    private fun finishScan(){
        done = true
        for (mac in readings.keys){
            val value = readings[mac]?.sum()?.toDouble()?.div(readings[mac]!!.size)
            for (ap in mappingPoint.accessPointSignalRecorded){
                if (mac == ap.mac) {
                    ap.rssi = value!!
                    Log.d( TAG, "$mac $value")
                }
            }
        }
        fragment.binding.yesButtonAddMapping.isEnabled = true
        Toast.makeText(fragment.requireActivity(), "Scanning is done, press button to save the mappingPoint", Toast.LENGTH_LONG).show()
        //activity.rvAdapter.notifyDataSetChanged()
    }
}