package tech.sutd.indoortrackingpro.ui.wifi

import android.net.wifi.ScanResult
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import kotlinx.coroutines.runBlocking
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver
import tech.sutd.indoortrackingpro.data.WifiWrapper
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.MappingPoint
import tech.sutd.indoortrackingpro.ui.adapter.ApListAdapter
import javax.inject.Inject

@HiltViewModel
class WifiViewModel @Inject constructor(
    private val wifiWrapper: WifiWrapper,
    private val data: MediatorLiveData<List<ScanResult>>,
    private val accountMd: MediatorLiveData<List<AccessPoint>>,
    private val config: RealmConfiguration,
) : ViewModel() {

    private val TAG = "WifiViewModel"

    var apLd: MutableLiveData<AccessPoint> = MutableLiveData()
    var account: LiveData<RealmList<AccessPoint>> = MutableLiveData()
    var mapping: LiveData<RealmList<MappingPoint>> = MutableLiveData()

    fun insertAp(accessPoint: AccessPoint) {
        val realm = Realm.getInstance(config)
        realm.beginTransaction()
        val account = realm.where(Account::class.java).findFirst()
        account?.mAccessPoints?.add(accessPoint)
        realm.commitTransaction()
        apLd.value = accessPoint
        Log.d(TAG, "insertAp: ${accessPoint.mac}")
    }

    fun apLd(): LiveData<AccessPoint> = apLd

    fun initScan(receiver: WifiSearchReceiver): LiveData<List<ScanResult>> {
        wifiWrapper.startScan()
        data.addSource(receiver.data, data::setValue)
        return data
    }

    fun endScan(receiver: WifiSearchReceiver) {
        data.removeSource(receiver.data)
    }

    fun accessPoints(): LiveData<RealmList<AccessPoint>> {
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                realm.executeTransaction { transactionRealm ->
                    val wifiResults =
                        transactionRealm.where(Account::class.java).findFirst()
                    Log.d(TAG, "onSuccess: ${wifiResults?.mAccessPoints?.get(0)?.mac}")
                    account = wifiResults?.mAccessPoints?.asFlowable()
                        ?.onBackpressureBuffer()
                        ?.toLiveData() as LiveData<RealmList<AccessPoint>>
                }
            }
        })
        return account
    }

    fun mappingPoint(): LiveData<RealmList<MappingPoint>> {
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                realm.executeTransaction { transactionRealm ->
                    val wifiResults =
                        transactionRealm.where(Account::class.java).findFirst()
                    mapping = wifiResults?.mMappingPoints?.asFlowable()
                        ?.onBackpressureBuffer()
                        ?.toLiveData() as LiveData<RealmList<MappingPoint>>
                }
            }
        })
        return mapping
    }
}