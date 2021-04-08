package tech.sutd.indoortrackingpro.ui.wifi

import android.net.wifi.ScanResult
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.realm.Realm
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.WapListBinding
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.model.Account
import java.io.Serializable

class WifiListAdapter(
    private val realm: Realm
) : RecyclerView.Adapter<WifiListAdapter.WAPListViewHolder>() {

    private var wifiList: List<ScanResult> = listOf()
    private var selectedWifiList: List<ScanResult> = listOf()

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

            if (select.isChecked) {
                selectedWifiList.toMutableList().add(wifiList[position])
            }
        }
    }

    fun sendData(wifiList: List<ScanResult>) {
        this.wifiList = wifiList
        notifyDataSetChanged()
    }

    fun selectWap() {
        realm.beginTransaction()
        val account = realm.where(Account::class.java).findFirst()!!
        for (wifi in selectedWifiList) {
            val accessPoint = AccessPoint()
            accessPoint.mac = wifi.BSSID
            accessPoint.ssid = wifi.SSID
            account.mAccessPoints.add(accessPoint)
            realm.commitTransaction()
        }
    }

    override fun getItemCount(): Int = wifiList.size

    inner class WAPListViewHolder(var binding: WapListBinding) :
        RecyclerView.ViewHolder(binding.root)

}