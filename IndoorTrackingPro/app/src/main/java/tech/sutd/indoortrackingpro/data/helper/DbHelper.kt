package tech.sutd.indoortrackingpro.data.helper

import androidx.lifecycle.LiveData
import io.realm.RealmList
import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.model.Account_Inaccuracy
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints

interface DbHelper {

    fun insertInAccuracy(inaccuracy: Account_Inaccuracy)

    fun insertAp(accessPoint: Account_mAccessPoints)
    fun insertMp(mappingPoint: Account_mMappingPoints)

    fun clearAp()
    fun clearMp()

    fun deleteAp(id: ObjectId)
    fun deleteMp(id: ObjectId)

    fun retrieveApLiveData(): LiveData<RealmList<Account_mAccessPoints>>?
    fun retrieveMpLiveData(): LiveData<RealmList<Account_mMappingPoints>>?
}