package tech.sutd.indoortrackingpro.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFERENCES_NAME = "data_store"
val Context.dataStore: DataStore<Preferences> by preferencesDataStore(PREFERENCES_NAME)

class Preferences (
    val context: Context
) {
    private val dataStore = context.dataStore

    companion object {
        val AP_ADDED = booleanPreferencesKey("ap_added")
        val MP_ADDED = booleanPreferencesKey("mp_added")
        val SCAN_DONE = booleanPreferencesKey("scan_done")
        val MAP_NUM = intPreferencesKey("map_num")
    }

    suspend fun updateCheckAp(checkAp: Boolean) {
        dataStore.edit {
            it[AP_ADDED] = checkAp
        }
    }

    suspend fun updateCheckMp(checkMp: Boolean) {
        dataStore.edit {
            it[MP_ADDED] = checkMp
        }
    }

    suspend fun scanDone(scanDone: Boolean) {
        dataStore.edit {
            it[SCAN_DONE] = scanDone
        }
    }

    suspend fun floorNum(mapNum: Int) {
        dataStore.edit {
            it[MAP_NUM] = mapNum
        }
    }

    val apAdded: Flow<Boolean?> = dataStore.data.map {
        it[AP_ADDED]
    }

    val mpAdded: Flow<Boolean?> = dataStore.data.map {
        it[MP_ADDED]
    }

    val scanDone: Flow<Boolean?> = dataStore.data.map {
        it[SCAN_DONE]
    }

    val floorNum: Flow<Int> = dataStore.data.map {
        it[MAP_NUM] ?: 1
    }
}