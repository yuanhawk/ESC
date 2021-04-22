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
import io.realm.RealmList
import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.data.TrackingWorker
import tech.sutd.indoortrackingpro.data.helper.AlgoHelper
import tech.sutd.indoortrackingpro.data.helper.DbHelper
import tech.sutd.indoortrackingpro.data.helper.FirestoreHelper
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_Inaccuracy
import tech.sutd.indoortrackingpro.model.Coordinate
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import tech.sutd.indoortrackingpro.utils.intentFilter
import tech.sutd.indoortrackingpro.utils.intentKey
import java.util.concurrent.TimeUnit
import javax.inject.Inject

@HiltViewModel
class TrackingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val workManager: WorkManager,
    private val algoHelper: AlgoHelper,
    private val realm: Realm,
    private val db: DbHelper,
    private val fStore: FirestoreHelper
) : ViewModel() {

    private val TAG = "TrackingViewModel"

    private lateinit var broadcastReceiver: BroadcastReceiver

    private val trackingRequest by lazy {
        OneTimeWorkRequestBuilder<TrackingWorker>()
            .setInitialDelay(5, TimeUnit.SECONDS)
            .build()
    }

    fun inaccuracy(): LiveData<RealmList<Account_Inaccuracy>>? = db.retrieveRecordInAccuracyLiveData()

    var floorNumber = MutableLiveData<Int>(1)
    private var coordinates: MutableLiveData<Coordinate> = MutableLiveData(Coordinate(300.0, 300.0, 1.0))

    fun initWifiScan(fragment: TrackingFragment) {
        workManager.enqueue(trackingRequest)
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                val wifiData = intent.getParcelableExtra<Account_mMappingPoints>(intentKey)
                val account = realm.where(Account::class.java).findFirst()
                if (wifiData != null) {
                    val coordinate = account?.let {
                        algoHelper.predictCoordinate(
                            wifiData,
                            it,
                            "WKNN"
                        )
                    }

                    Log.d(TAG, "receive")
                    Log.d(TAG, coordinate?.longitude.toString())
                    Log.d(TAG, coordinate?.latitude.toString())
                    Log.d(TAG, coordinate?.z.toString())
                    coordinates.value = coordinate
                }
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

    fun coordinates(): LiveData<Coordinate> = coordinates

    fun insertInAccuracy(inaccuracy: Account_Inaccuracy) {
        db.insertInAccuracy(inaccuracy)
        fStore.insertInaccuracy(inaccuracy)
    }


    fun getInaccuracyList() = realm.where(Account::class.java).findFirst()?.Inaccuracy

    fun getInaccuracyListFloor(floor: Int): RealmList<Account_Inaccuracy> {
        val res:RealmList<Account_Inaccuracy> = RealmList()
        if (getInaccuracyList() != null) {
            for (inaccuracyRecord in getInaccuracyList()!!) {
                if (inaccuracyRecord.z.toInt() == floor) {
                    res.add(inaccuracyRecord)
                }
            }
            return res
        }
        return RealmList()
    }

    fun deleteInAccuracy(id: ObjectId) {
        db.deleteInAccuracy(id)
        fStore.deleteInaccuracy(id)
    }

    fun clearInAccuracy() {
        db.clearInAccuracy()
        fStore.clearInaccuracy()
    }

}