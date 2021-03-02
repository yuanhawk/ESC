package tech.sutd.indoortrackingpro.adapter

import android.net.wifi.ScanResult
import androidx.recyclerview.widget.RecyclerView

import android.view.LayoutInflater
import android.view.View
import tech.sutd.indoortrackingpro.model.AccessPoint
import android.view.ViewGroup

import android.widget.LinearLayout

import android.widget.TextView
import tech.sutd.indoortrackingpro.R

class WifiSearchAdapter(var wifiList: ArrayList<ScanResult> = ArrayList()): RecyclerView.Adapter<WifiSearchAdapter.WifiSearchViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WifiSearchViewHolder {
        var itemLayout: LinearLayout =
            LayoutInflater.from(parent.context).inflate(R.layout.item_wifisearch, parent, false) as LinearLayout
        return WifiSearchViewHolder(itemLayout)
    }

    override fun onBindViewHolder(holder: WifiSearchViewHolder, position: Int) {
        holder.mac.text = wifiList[position].BSSID
        holder.ssid.text = wifiList[position].SSID
        holder.rssi.text = wifiList[position].level.toString()

    }

    override fun getItemCount(): Int {
        return wifiList.size
    }

    class WifiSearchViewHolder(v: LinearLayout): RecyclerView.ViewHolder(v) {
        var mac: TextView = v.findViewById(R.id.wifi_mac)
        var ssid: TextView = v.findViewById(R.id.wifi_ssid)
        var rssi: TextView = v.findViewById(R.id.wifi_rssi)

    }
}