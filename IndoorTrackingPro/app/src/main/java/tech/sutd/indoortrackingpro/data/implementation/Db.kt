package tech.sutd.indoortrackingpro.data.implementation

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.toLiveData
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.data.helper.DbHelper
import tech.sutd.indoortrackingpro.datastore.Preferences
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_Inaccuracy
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import javax.inject.Inject

class Db @Inject constructor(
    private val realm: Realm,
    private val pref: Preferences
) : DbHelper {

    private val TAG = "Db"

    var account: LiveData<RealmList<Account_mAccessPoints>>? = MutableLiveData()
    var mapping: LiveData<RealmList<Account_mMappingPoints>>? = MutableLiveData()
    var inaccuracy: LiveData<RealmList<Account_Inaccuracy>>? = MutableLiveData()

    override fun insertInAccuracy(inaccuracy: Account_Inaccuracy) {
        realm.beginTransaction()
        val account = realm.where(Account::class.java).findFirst()
        account?.Inaccuracy?.add(inaccuracy)
        realm.commitTransaction()
    }

    override fun insertAp(accessPoint: Account_mAccessPoints) {
        realm.beginTransaction()
        val account = realm.where(Account::class.java).findFirst()
        account?.mAccessPoints?.add(accessPoint)
        realm.commitTransaction()
        Log.d(TAG, "insertAp: ${accessPoint.mac}")
        GlobalScope.launch { pref.updateCheckAp(true) }
    }

    override fun insertMp(mappingPoint: Account_mMappingPoints) {
        Log.d( TAG+1, "Inserting MP")
        realm.beginTransaction()
        for (ap in mappingPoint.accessPointSignalRecorded){
            Log.d( TAG+1, "${ap.mac} ${ap.rssi}")
        }
        val v = realm.where(Account::class.java).findFirst()!!.mMappingPoints
        v.add(mappingPoint)
        realm.commitTransaction()
        GlobalScope.launch { pref.updateCheckMp(true) }
    }

    override fun clearAp() {
        realm.executeTransaction { r ->
            r.delete(Account_mAccessPoints::class.java)
        }
    }

    override fun clearMp() {
        realm.executeTransaction { r ->
            r.delete(Account_mMappingPoints::class.java)
        }
    }

    override fun clearInAccuracy() {
        realm.executeTransaction { r ->
            r.delete(Account_Inaccuracy::class.java)
        }
    }

    override fun deleteMp(id: ObjectId) {
        realm.beginTransaction()
        val mp = realm.where(Account_mMappingPoints::class.java)
            .equalTo("id", id)
            .findFirst()
        mp?.deleteFromRealm()
        realm.commitTransaction()
    }

    override fun deleteAp(id: ObjectId) {
        realm.beginTransaction()
        val ap = realm.where(Account_mAccessPoints::class.java)
            .equalTo("id", id)
            .findFirst()
        ap?.deleteFromRealm()
        realm.commitTransaction()
    }

    override fun deleteInAccuracy(id: ObjectId) {
        realm.beginTransaction()
        val inaccuracy = realm.where(Account_Inaccuracy::class.java)
            .equalTo("id", id)
            .findFirst()
        inaccuracy?.deleteFromRealm()
        realm.commitTransaction()
    }

    override fun retrieveApLiveData(): LiveData<RealmList<Account_mAccessPoints>>? {
        realm.executeTransaction { transactionRealm ->
            val wifiResults =
                transactionRealm.where(Account::class.java).findFirst()
            account = wifiResults?.mAccessPoints?.asFlowable()
                ?.onBackpressureBuffer()
                ?.toLiveData()
        }
        return account
    }

    override fun retrieveMpLiveData(): LiveData<RealmList<Account_mMappingPoints>>? {
        realm.executeTransaction { transactionRealm ->
            val wifiResults =
                transactionRealm.where(Account::class.java).findFirst()
            mapping = wifiResults?.mMappingPoints?.asFlowable()
                ?.onBackpressureBuffer()
                ?.toLiveData()
        }
        return mapping
    }

    override fun retrieveRecordInAccuracyLiveData(): LiveData<RealmList<Account_Inaccuracy>>? {
        realm.executeTransaction { transactionRealm ->
            val wifiResults =
                transactionRealm.where(Account::class.java).findFirst()
            inaccuracy = wifiResults?.Inaccuracy?.asFlowable()
                ?.onBackpressureBuffer()
                ?.toLiveData()
        }
        return inaccuracy
    }
}