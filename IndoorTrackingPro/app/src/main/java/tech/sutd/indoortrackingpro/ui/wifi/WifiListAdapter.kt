package tech.sutd.indoortrackingpro.ui.wifi

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmList
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.WapListBinding
import tech.sutd.indoortrackingpro.model.AP
import javax.inject.Inject

class WifiListAdapter @Inject constructor(
) : RecyclerView.Adapter<WifiListAdapter.WAPListViewHolder>() {

    private var list: List<AP> = RealmList()

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
        }
    }

    fun sendData(list: List<AP>) {
        this.list = list
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = list.size

    inner class WAPListViewHolder(var binding: WapListBinding): RecyclerView.ViewHolder(binding.root)

}