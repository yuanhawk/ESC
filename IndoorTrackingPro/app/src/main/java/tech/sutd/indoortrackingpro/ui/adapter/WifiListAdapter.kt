package tech.sutd.indoortrackingpro.ui.adapter

import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.WapListBinding
import javax.inject.Inject

// Removed on next iteration if not used

class WifiListAdapter : RecyclerView.Adapter<WifiListAdapter.WAPListViewHolder>() {

    var wifiList: List<ScanResult> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WAPListViewHolder {
        val binding = DataBindingUtil.inflate<WapListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.wap_list, parent, false
        )
        return WAPListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: WAPListViewHolder, position: Int) {
        with(holder.binding) {
            mac.text = wifiList[position].BSSID
            ssid.text = wifiList[position].SSID
            rssi.text = wifiList[position].level.toString()
        }
    }

    fun sendData(wifiList: List<ScanResult>) {
        this.wifiList = wifiList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = wifiList.size

    inner class WAPListViewHolder(var binding: WapListBinding) :
        RecyclerView.ViewHolder(binding.root)

}