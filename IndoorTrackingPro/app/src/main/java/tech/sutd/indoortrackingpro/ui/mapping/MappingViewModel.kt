package tech.sutd.indoortrackingpro.ui.mapping

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import org.bson.types.ObjectId
import tech.sutd.indoortrackingpro.data.helper.DbHelper
import tech.sutd.indoortrackingpro.data.helper.FirestoreHelper
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_Inaccuracy
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import tech.sutd.indoortrackingpro.model.Coordinate
import javax.inject.Inject

@HiltViewModel
class MappingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val realm: Realm,
    private val db: DbHelper,
    private val fStore: FirestoreHelper
) : ViewModel() {

    private val TAG = "mappingViewModel"
    var floorNumber: MutableLiveData<Int> = MutableLiveData(1)

    fun insertMp(coord: FloatArray, mappingPt: Account_mMappingPoints) {
        mappingPt.x = coord[0].toDouble()
        mappingPt.y = coord[1].toDouble()
        mappingPt.z = coord[2].toDouble()
        db.insertMp(mappingPt)
        fStore.insertMp(mappingPt)

        for (i in 0 until realm.where(Account::class.java).findFirst()!!.mMappingPoints.size) {
            val mappingPointJustAdded = realm.where(Account::class.java).findFirst()!!.mMappingPoints[i]
            for (ap in mappingPointJustAdded!!.accessPointSignalRecorded) {
                Log.d(TAG + 2, "${ap.mac} ${ap.rssi}")
            }
        }
    }

    fun getMappingPositions(): ArrayList<FloatArray>{
        val mappingPointPositions = ArrayList<FloatArray>()
        val mappingPointList =  realm.where(Account::class.java).findFirst()!!.mMappingPoints
        for (mappingPoint in mappingPointList){
            mappingPointPositions.add(floatArrayOf(mappingPoint.x.toFloat(), mappingPoint.y.toFloat()))
        }
        return mappingPointPositions
    }

    fun getMappingPositionsFloor(floor: Int): ArrayList<FloatArray>{
        val mappingPointPositions = ArrayList<FloatArray>()
        val mappingPointList =  realm.where(Account::class.java).findFirst()?.mMappingPoints
        if (mappingPointList != null) {
            for (mappingPoint in mappingPointList){
                if (mappingPoint.z.toInt() == floor) mappingPointPositions.add(floatArrayOf(mappingPoint.x.toFloat(), mappingPoint.y.toFloat()))
            }
        }
        return mappingPointPositions
    }
}