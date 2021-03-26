package tech.sutd.indoortrackingpro.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.ScanResult
import android.net.wifi.WifiManager
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.adapter.MPReadingAdapter
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.ListAP
import tech.sutd.indoortrackingpro.model.MappingPoint
import javax.inject.Inject
import tech.sutd.indoortrackingpro.utils.Constants
@AndroidEntryPoint
class AddMappingPointActivity: BaseActivity() {
    @Inject
    lateinit var realm: Realm
    lateinit var saveButton: Button
    lateinit var xValue: EditText
    lateinit var yValue: EditText
    lateinit var recyclerView: RecyclerView
    lateinit var wifiManager: WifiManager
    lateinit var wifiReceiver: BroadcastReceiver
    var readings: HashMap<String, ArrayList<Int>> = HashMap()
    var done = true;
    var count = 0
    var handler: Handler = Handler(Looper.myLooper()!!)
    lateinit var apList: RealmList<AccessPoint>
    lateinit var mappingPoint: MappingPoint
    val TAG = "AddMappingPoint"
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rvAdapter: MPReadingAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_addmappingpoint)


//TODO
        apList = realm.where(Account::class.java).findFirst()?.mAccessPoints!!
        if (apList.size == 0){
            Toast.makeText(this, "Please add Access Points first!", Toast.LENGTH_LONG).show()
            this.finish()
        }
        mappingPoint = MappingPoint()
        for (ap in apList){
            mappingPoint.accessPointSignalRecorded.add(AccessPoint(ap))
        }
        wifiManager = applicationContext.getSystemService(Context.WIFI_SERVICE) as WifiManager
        wifiReceiver = object : BroadcastReceiver(){
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
                        //Log.d(TAG, ap.mac + scanResult.level)
                    }
                }
            }
        }
        initUI()

    }

    override fun onResume() {
        super.onResume()
        registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        if (done){
            done = false
            startScan()
        }
    }
    fun initUI(){
        layoutManager = LinearLayoutManager(this)
        saveButton = findViewById(R.id.add_mp_save_button)
        xValue = findViewById(R.id.add_mp_x)
        yValue = findViewById(R.id.add_mp_y)

        recyclerView = findViewById(R.id.add_mp_rv)
        recyclerView.layoutManager = layoutManager
        rvAdapter = MPReadingAdapter(mappingPoint)
        recyclerView.adapter = rvAdapter
        saveButton.isEnabled = false
        saveButton.setOnClickListener {
            realm.beginTransaction()
            mappingPoint.x = xValue.text.toString().toDouble()
            mappingPoint.y = yValue.text.toString().toDouble()
            val v = realm.where(Account::class.java).findFirst()!!.mMappingPoints
            v.add(mappingPoint)
            realm.commitTransaction()
            this.finish()
        }
    }

    fun startScan(){
        handler.postDelayed({
            wifiManager.startScan()
            if (count < Constants.getScanBatch()) startScan()
            else finishScan()
        },Constants.getFetchInterval())
    }

    fun finishScan(){
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
        saveButton.isEnabled = true
        rvAdapter.notifyDataSetChanged()
    }
}