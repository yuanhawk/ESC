package tech.sutd.indoortrackingpro.adapter

import android.net.wifi.ScanResult
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import tech.sutd.indoortrackingpro.model.AccessPoint
import android.view.ViewGroup

import android.widget.LinearLayout

import android.widget.TextView
import androidx.databinding.DataBindingUtil
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.ItemWifisearchBinding

class WifiSearchAdapter(
    var wifiList: ArrayList<ScanResult> = ArrayList()
): RecyclerView.Adapter<WifiSearchAdapter.WifiSearchViewHolder>(){

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