package tech.sutd.indoortrackingpro.ui.mapping

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import javax.inject.Inject

@HiltViewModel
class MappingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val realm: Realm
) : ViewModel() {

    private val TAG = "mappingViewModel"

    fun insertMp(coord: FloatArray, mappingPt: Account_mMappingPoints) {
        mappingPt.x = coord[0].toDouble()
        mappingPt.y = coord[1].toDouble()

        realm.beginTransaction()
        for (ap in mappingPt.accessPointSignalRecorded){
            Log.d( TAG+1, "${ap.mac} ${ap.rssi}")
        }
        val v = realm.where(Account::class.java).findFirst()!!.mMappingPoints
        v.add(mappingPt)

        realm.commitTransaction()
        for (i in 0 until realm.where(Account::class.java).findFirst()!!.mMappingPoints.size) {
            val mappingPointJustAdded = realm.where(Account::class.java).findFirst()!!.mMappingPoints[i]
            for (ap in mappingPointJustAdded!!.accessPointSignalRecorded) {
                Log.d(TAG + 2, "${ap.mac} ${ap.rssi}")
            }
        }
    }
}