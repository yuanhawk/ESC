package tech.sutd.indoortrackingpro.ui.wifi

import android.net.wifi.ScanResult
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver
import tech.sutd.indoortrackingpro.data.WifiWrapper
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import javax.inject.Inject

@HiltViewModel
class WifiViewModel @Inject constructor(
    private val wifiWrapper: WifiWrapper,
    private val data: MediatorLiveData<List<ScanResult>>,
    private val accountMd: MediatorLiveData<List<Account_mAccessPoints>>,
    private val config: RealmConfiguration
) : ViewModel() {

    private val TAG = "WifiViewModel"

    var account: LiveData<RealmList<Account_mAccessPoints>>? = MutableLiveData()
    var mapping: LiveData<RealmList<Account_mMappingPoints>>? = MutableLiveData()

    fun insertAp(accessPoint: Account_mAccessPoints) {
        val realm = Realm.getInstance(config)
        realm.beginTransaction()
        val account = realm.where(Account::class.java).findFirst()
        account?.mAccessPoints?.add(accessPoint)
        realm.commitTransaction()
        Log.d(TAG, "insertAp: ${accessPoint.mac}")
    }

    fun initScan(receiver: WifiSearchReceiver): LiveData<List<ScanResult>> {
        wifiWrapper.startScan()
        data.addSource(receiver.data, data::setValue)
        return data
    }

    fun endScan(receiver: WifiSearchReceiver) {
        data.removeSource(receiver.data)
    }

    fun accessPoints(): LiveData<RealmList<Account_mAccessPoints>>? {
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                realm.executeTransaction { transactionRealm ->
                    val wifiResults =
                        transactionRealm.where(Account::class.java).findFirst()
                    account = wifiResults?.mAccessPoints?.asFlowable()
                        ?.onBackpressureBuffer()
                        ?.toLiveData()
                }
            }
        })
        return account
    }

    fun mappingPoint(): LiveData<RealmList<Account_mMappingPoints>>? {
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                realm.executeTransaction { transactionRealm ->
                    val wifiResults =
                        transactionRealm.where(Account::class.java).findFirst()
                    mapping = wifiResults?.mMappingPoints?.asFlowable()
                        ?.onBackpressureBuffer()
                        ?.toLiveData()
                }
            }
        })
        return mapping
    }
}