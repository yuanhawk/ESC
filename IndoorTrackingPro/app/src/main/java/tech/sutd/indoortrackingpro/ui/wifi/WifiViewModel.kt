package tech.sutd.indoortrackingpro.ui.wifi

import android.net.wifi.ScanResult
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.toLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver
import tech.sutd.indoortrackingpro.data.WifiWrapper
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.MappingPoint
import javax.inject.Inject

@HiltViewModel
class WifiViewModel @Inject constructor(
    private val wifiWrapper: WifiWrapper,
    private val data: MediatorLiveData<List<ScanResult>>,
    private val config: RealmConfiguration
) : ViewModel() {

    private lateinit var account: LiveData<RealmList<AccessPoint>>
    private lateinit var mapping: LiveData<RealmList<MappingPoint>>

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