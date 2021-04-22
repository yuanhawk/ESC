package tech.sutd.indoortrackingpro.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.SelectedMpListBinding
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import java.math.RoundingMode

class MpListAdapter : RecyclerView.Adapter<MpListAdapter.MpListViewHolder>() {

    private val TAG = "MpListAdapter"

    var mapList: List<Account_mMappingPoints> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MpListViewHolder {
        val binding = DataBindingUtil.inflate<SelectedMpListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.selected_mp_list, parent, false
        )
        return MpListViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MpListViewHolder, position: Int) {
        with(holder.binding) {
            Log.d(TAG, "onBindViewHolder: ${mapList[position].x.toString()}")
            xMap.text = mapList[position].x.div(20).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
            yMap.text = mapList[position].y.div(20).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
            floorMap.text = mapList[position].z.toInt().toString()
        }
    }

    fun sendData(mapList: List<Account_mMappingPoints>) {
        this.mapList = mapList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = mapList.size

    inner class MpListViewHolder(var binding: SelectedMpListBinding) :
        RecyclerView.ViewHolder(binding.root)
}