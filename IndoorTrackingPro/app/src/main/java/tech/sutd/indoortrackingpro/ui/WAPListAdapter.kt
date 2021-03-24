package tech.sutd.indoortrackingpro.ui

import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.WapListBinding

class WAPListAdapter(
    var wifiList: ArrayList<ScanResult> = ArrayList()
) : RecyclerView.Adapter<WAPListAdapter.WAPListViewHolder>() {

    inner class WAPListViewHolder(var binding: WapListBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WAPListViewHolder {
        val binding = DataBindingUtil.inflate<WapListBinding>(
        LayoutInflater.from(parent.context),
        R.layout.wap_list, parent, false
        )
        return WAPListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WAPListViewHolder, position: Int) {
        holder.binding.mac.text = wifiList[position].BSSID
        holder.binding.mac.text = wifiList[position].SSID
        holder.binding.mac.text = wifiList[position].level.toString()
    }

    override fun getItemCount(): Int = wifiList.size

}