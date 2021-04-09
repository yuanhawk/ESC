package tech.sutd.indoortrackingpro.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.SelectedApListBinding
import tech.sutd.indoortrackingpro.model.AccessPoint

class ApListAdapter :
    RecyclerView.Adapter<ApListAdapter.ApListViewHolder>() {

    private val TAG = "ApListAdapter"

    private var wifiList: List<AccessPoint> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ApListViewHolder {
        val binding = DataBindingUtil.inflate<SelectedApListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.selected_ap_list, parent, false
        )
        return ApListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ApListViewHolder, position: Int) {
        with(holder.binding) {
            Log.d(TAG, "onBindViewHolder: ${wifiList[position].mac}")
            mac.text = wifiList[position].mac
            ssid.text = wifiList[position].ssid
        }
    }

    fun sendData(wifiList: List<AccessPoint>) {
        this.wifiList = wifiList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = wifiList.size

    inner class ApListViewHolder(var binding: SelectedApListBinding) :
        RecyclerView.ViewHolder(binding.root)
}