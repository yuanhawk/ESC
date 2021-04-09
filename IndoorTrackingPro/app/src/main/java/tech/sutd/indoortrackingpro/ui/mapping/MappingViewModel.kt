package tech.sutd.indoortrackingpro.ui.mapping

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.MappingPoint
import javax.inject.Inject

@HiltViewModel
class MappingViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val config: RealmConfiguration,
) : ViewModel() {

    private val TAG = "MappingViewModel"

    fun insertMp(coord: FloatArray) {
        val mappingPt = MappingPoint()
        mappingPt.x = coord[0].toDouble()
        mappingPt.y = coord[1].toDouble()

        val realm = Realm.getInstance(config)
        realm.beginTransaction()
        val account = realm.where(Account::class.java).findFirst()
        account?.mMappingPoints?.add(mappingPt)
        realm.commitTransaction()
        Log.d(TAG, "insertAp: ${coord[0]}")
    }
}