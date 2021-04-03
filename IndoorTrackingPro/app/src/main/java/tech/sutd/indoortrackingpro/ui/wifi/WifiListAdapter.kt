package tech.sutd.indoortrackingpro.ui.wifi

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.emptyPreferences
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmList
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.data.PrefStore
import tech.sutd.indoortrackingpro.data.PrefStoreKeys
import tech.sutd.indoortrackingpro.databinding.WapListBinding
import tech.sutd.indoortrackingpro.model.AP
import tech.sutd.indoortrackingpro.model.WifiPreferences
import java.io.IOException
import java.util.concurrent.Flow
import javax.inject.Inject

class WifiListAdapter @Inject constructor(
    private val prefStore: PrefStore,
) : RecyclerView.Adapter<WifiListAdapter.WAPListViewHolder>() {

    private val TAG = "WifiListAdapter"

    private var list: List<AP> = RealmList()
//    private val dataStore by lazy { prefStore.prefCounterFlow.asLiveData() }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WAPListViewHolder {
        val binding = DataBindingUtil.inflate<WapListBinding>(
                LayoutInflater.from(parent.context),
                R.layout.wap_list, parent, false
        )
        return WAPListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WAPListViewHolder, position: Int) {
        with(holder.binding) {
            mac.text = list[position].mac
            ssid.text = list[position].ssid
            rssi.text = list[position].rssi.toString()

//            lifecycleOwner?.let { it ->
//                dataStore.observe(it) {
//                    select.isChecked = it.select
//                }
//            }
//
//            select.setOnClickListener {
//                runBlocking { prefStore.setPref(true) }
//            }
        }
    }

    fun sendData(list: List<AP>) {
        this.list = list
        Log.d(TAG, "sendData: ${list.size}")
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    inner class WAPListViewHolder(var binding: WapListBinding): RecyclerView.ViewHolder(binding.root)

}