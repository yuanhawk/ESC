package tech.sutd.indoortrackingpro.adapter

import android.net.wifi.ScanResult
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.ViewGroup

import androidx.databinding.DataBindingUtil
import io.realm.Realm
import io.realm.RealmConfiguration
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.ItemWifisearchBinding
import tech.sutd.indoortrackingpro.model.AP
import tech.sutd.indoortrackingpro.model.ListAP
import java.util.ArrayList
import javax.inject.Inject

class WifiSearchAdapter @Inject constructor(
        private val realm: Realm,
        private val config: RealmConfiguration
) : RecyclerView.Adapter<WifiSearchAdapter.WifiSearchViewHolder>() {

    lateinit var wifiList: ArrayList<ScanResult>

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiSearchViewHolder {
        val binding = DataBindingUtil.inflate<ItemWifisearchBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_wifisearch, parent, false
        )
        return WifiSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WifiSearchViewHolder, position: Int) {
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                realm.executeTransaction { transactionRealm ->
                    val wifiResults = transactionRealm.where(ListAP::class.java).findFirst()
                    with(holder.binding) {
                        mac.text = wifiResults?.apList?.get(position)?.mac
                        ssid.text = wifiResults?.apList?.get(position)?.ssid
                        rssi.text = wifiResults?.apList?.get(position)?.rssi.toString()
                    }
                }
            }
        })
    }

    override fun getItemCount(): Int = 20

    inner class WifiSearchViewHolder(var binding: ItemWifisearchBinding) : RecyclerView.ViewHolder(binding.root)
}