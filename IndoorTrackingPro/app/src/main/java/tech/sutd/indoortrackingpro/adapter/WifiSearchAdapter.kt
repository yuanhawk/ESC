package tech.sutd.indoortrackingpro.adapter

import android.net.wifi.ScanResult
import android.util.Log
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

     var wifiList: ArrayList<ScanResult> = arrayListOf()
    val TAG: String = "WifiSearchAdapter"
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiSearchViewHolder {
        val binding = DataBindingUtil.inflate<ItemWifisearchBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_wifisearch, parent, false
        )
        return WifiSearchViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WifiSearchViewHolder, position: Int) {
        holder.binding.mac.text = wifiList[position].BSSID
        holder.binding.ssid.text = wifiList[position].SSID
        holder.binding.rssi.text = wifiList[position].level.toString()

    }

    override fun getItemCount(): Int = wifiList.size

    inner class WifiSearchViewHolder(var binding: ItemWifisearchBinding) : RecyclerView.ViewHolder(binding.root)
}