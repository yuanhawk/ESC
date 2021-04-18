package tech.sutd.indoortrackingpro.ui.wifi

import android.net.wifi.ScanResult
import android.util.Log
import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
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
    private val realm: Realm
) : ViewModel() {

    private val TAG = "WifiViewModel"

    var account: LiveData<RealmList<Account_mAccessPoints>>? = MutableLiveData()
    var mapping: LiveData<RealmList<Account_mMappingPoints>>? = MutableLiveData()

    fun insertAp(accessPoint: Account_mAccessPoints) {
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
        realm.executeTransaction { transactionRealm ->
            val wifiResults =
                transactionRealm.where(Account::class.java).findFirst()
            account = wifiResults?.mAccessPoints?.asFlowable()
                ?.onBackpressureBuffer()
                ?.toLiveData()
        }
        return account
    }

    fun mappingPoint(): LiveData<RealmList<Account_mMappingPoints>>? {
        realm.executeTransaction { transactionRealm ->
            val wifiResults =
                transactionRealm.where(Account::class.java).findFirst()
            mapping = wifiResults?.mMappingPoints?.asFlowable()
                ?.onBackpressureBuffer()
                ?.toLiveData()
        }
        return mapping
    }

    fun clearAp() {
        realm.executeTransaction { r ->
            r.delete(Account_mAccessPoints::class.java)
        }
    }

    fun clearMp() {
        realm.executeTransaction { r ->
            r.delete(Account_mMappingPoints::class.java)
        }
    }
}