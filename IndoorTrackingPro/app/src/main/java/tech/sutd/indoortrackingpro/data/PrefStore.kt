package tech.sutd.indoortrackingpro.data

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import tech.sutd.indoortrackingpro.model.WifiPreferences
import java.io.IOException
import javax.inject.Inject

private const val WIFI_PREF = "wifi_pref"
private val Context.dataStore by preferencesDataStore(WIFI_PREF)

object PrefStoreKeys {
    val SELECTED = booleanPreferencesKey("selected")
}

class PrefStore @Inject constructor(
    @ApplicationContext val context: Context
) {

    val prefCounterFlow: Flow<WifiPreferences> = context.dataStore.data
        .catch { e ->
            if (e is IOException)
                emit(emptyPreferences())
            else
                throw e
        }
        .map { pref ->
            val selected = pref[PrefStoreKeys.SELECTED] ?: false
            WifiPreferences(selected)
        }

    suspend fun setPref(selected: Boolean) {
        context.dataStore.edit {
            it[PrefStoreKeys.SELECTED] = selected
        }
    }
}