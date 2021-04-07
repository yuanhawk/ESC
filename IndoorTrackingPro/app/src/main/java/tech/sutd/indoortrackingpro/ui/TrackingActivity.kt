package tech.sutd.indoortrackingpro.ui

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.*
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.core.predictCoordinate
import tech.sutd.indoortrackingpro.data.TrackingWorker
import tech.sutd.indoortrackingpro.data.WifiWorker
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.MappingPoint
import tech.sutd.indoortrackingpro.utils.Constants
import java.util.concurrent.TimeUnit
import javax.inject.Inject
@AndroidEntryPoint
class TrackingActivity: BaseActivity() {
    val TAG = "TrackingActivity"
    @Inject
    lateinit var realm: Realm
    lateinit var xText: TextView
    lateinit var yText: TextView
    lateinit var trackingRequest: WorkRequest
    lateinit var broadcastReceiver: BroadcastReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)
        initUI()
        trackingRequest = OneTimeWorkRequestBuilder<TrackingWorker>()
                .setInitialDelay(5, TimeUnit.SECONDS)
                .build()
        broadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                val wifiData = intent.getParcelableExtra<MappingPoint>(Constants.getIntentKey())
                val coordinate = predictCoordinate(wifiData!!, realm.where(Account::class.java).findFirst()!!, "WKNN" )
                Log.d(TAG, "receive")
                xText.text = coordinate!!.longitude.toString()
                yText.text = coordinate!!.latitude.toString()
            }

        }
        LocalBroadcastManager.getInstance(this).registerReceiver(broadcastReceiver, IntentFilter(Constants.getIntentFilter()))
    }

    override fun onResume() {
        WorkManager.getInstance(this).enqueue(trackingRequest)
        super.onResume()
    }
    override fun onPause() {
        WorkManager.getInstance(this).cancelWorkById(trackingRequest.id)
        super.onPause()
    }
    private fun initUI(){
        xText = findViewById(R.id.tracking_x)
        yText = findViewById(R.id.tracking_y)
    }

}