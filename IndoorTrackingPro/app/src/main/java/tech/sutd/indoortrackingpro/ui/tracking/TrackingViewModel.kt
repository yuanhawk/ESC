package tech.sutd.indoortrackingpro.ui.tracking

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.util.Log
import androidx.lifecycle.*
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.data.TrackingWorker
import tech.sutd.indoortrackingpro.data.helper.AlgoHelper
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Coordinate
import tech.sutd.indoortrackingpro.model.MappingPoint
import tech.sutd.indoortrackingpro.ui.wifi.WifiListFragment
import tech.sutd.indoortrackingpro.utils.intentFilter
import tech.sutd.indoortrackingpro.utils.intentKey
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val workManager: WorkManager,
    private val algoHelper: AlgoHelper,
    private val realm: Realm
) : ViewModel() {

    private val TAG = "TrackingViewModel"

    private lateinit var broadcastReceiver: BroadcastReceiver

    private val trackingRequest by lazy {
        OneTimeWorkRequestBuilder<TrackingWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
    }

    private lateinit var coordinates: LiveData<Coordinate>

    fun initWifiScan(fragment: TrackingFragment) {
        workManager.enqueue(trackingRequest)
        broadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                val wifiData = intent.getParcelableExtra<MappingPoint>(intentKey)
                val coordinate = algoHelper.predictCoordinate(wifiData!!, realm.where(Account::class.java).findFirst()!!, "WKNN" )

                Log.d(TAG, "receive")
                Log.d(TAG, coordinate!!.longitude.toString())
                Log.d(TAG, coordinate!!.latitude.toString())
                coordinates = MutableLiveData(coordinate)
           with(fragment.binding) {
               trackingMap.setImageResource(tech.sutd.indoortrackingpro.R.drawable.map)
               trackingMap.isEnabled = true;
               trackingMap.pos[0] = coordinate!!.longitude.toFloat()
               trackingMap.pos[1] = coordinate!!.latitude.toFloat()
               trackingMap.invalidate();
           }
//                xText.text = coordinate.longitude.toString()
//                yText.text = coordinate.latitude.toString()
            }
        }
        fragment.context?.let {
            LocalBroadcastManager.getInstance(it)
                .registerReceiver(broadcastReceiver, IntentFilter(intentFilter))
        }
    }

    fun cancelWifiScan(fragment: TrackingFragment) {
        workManager.cancelWorkById(trackingRequest.id)
        fragment.context?.let {
            LocalBroadcastManager.getInstance(it)
                .unregisterReceiver(broadcastReceiver)
        }
    }

}