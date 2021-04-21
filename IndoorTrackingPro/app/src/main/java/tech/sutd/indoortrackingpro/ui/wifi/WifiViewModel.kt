package tech.sutd.indoortrackingpro.ui.wifi

import android.net.wifi.ScanResult
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.RealmList
import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.data.WifiSearchReceiver
import tech.sutd.indoortrackingpro.data.WifiWrapper
import tech.sutd.indoortrackingpro.data.helper.DbHelper
import tech.sutd.indoortrackingpro.data.helper.FirestoreHelper
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import javax.inject.Inject

@HiltViewModel
class WifiViewModel @Inject constructor(
    private val wifiWrapper: WifiWrapper,
    private val data: MediatorLiveData<List<ScanResult>>,
    private val realm: Realm,
    private val db: DbHelper,
    private val fStore: FirestoreHelper
) : ViewModel() {

    private val TAG = "WifiViewModel"

    fun insertAp(accessPoint: Account_mAccessPoints) {
        db.insertAp(accessPoint)
        fStore.insertAp(accessPoint)
    }

    private fun checkDuplicate(scanResult: ScanResult): Boolean{
        val apList = realm.where(Account::class.java).findFirst()!!.mAccessPoints
        for (ap in apList){
            if (ap.mac == scanResult.BSSID) return false
        }
        return true
    }

    fun initScan(receiver: WifiSearchReceiver): LiveData<List<ScanResult>> {
        wifiWrapper.startScan()
        data.addSource(receiver.data) {
            val listWithoutDuplicate = ArrayList<ScanResult>()
            for (scanResult in it) {
                if (checkDuplicate(scanResult)) listWithoutDuplicate.add(scanResult)
            }
            data.value = listWithoutDuplicate
        }
        return data
    }

    fun endScan(receiver: WifiSearchReceiver) {
        data.removeSource(receiver.data)
    }

    fun accessPoints(): LiveData<RealmList<Account_mAccessPoints>>? = db.retrieveApLiveData()

    fun mappingPoint(): LiveData<RealmList<Account_mMappingPoints>>? = db.retrieveMpLiveData()

    fun clearAp() {
        db.clearAp()
        fStore.clearAp()
    }

    fun clearMp() {
        db.clearMp()
        fStore.clearMp()
        fStore.clearApRecord()
    }

    fun deleteAp(id: ObjectId) {
        db.deleteAp(id)
        fStore.deleteAp(id)
    }

    fun deleteMp(id: ObjectId) {
        db.deleteMp(id)
        fStore.deleteMp(id)
    }

    fun pull() {
        db.clearAp()
        fStore.pullAp()
        db.clearMp()
        fStore.pullMp()
    }
}