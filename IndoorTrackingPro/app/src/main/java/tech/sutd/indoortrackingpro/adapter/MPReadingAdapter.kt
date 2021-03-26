package tech.sutd.indoortrackingpro.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.model.MappingPoint

class MPReadingAdapter(var mappingPoint: MappingPoint): RecyclerView.Adapter<MPReadingAdapter.ViewHolder>() {





    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = LayoutInflater.from(parent.context).inflate(R.layout.item_mpreading, parent, false) as LinearLayout
        return ViewHolder(layout)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.mac.text = mappingPoint.accessPointSignalRecorded[position]!!.mac
        holder.ssid.text = mappingPoint.accessPointSignalRecorded[position]!!.ssid
        holder.rssi.text = mappingPoint.accessPointSignalRecorded[position]!!.rssi.toString()
    }

    override fun getItemCount(): Int {
        return mappingPoint.accessPointSignalRecorded.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var mac = itemView.findViewById<TextView>(R.id.mp_reading_mac)
        var ssid = itemView.findViewById<TextView>(R.id.mp_reading_ssid)
        var rssi = itemView.findViewById<TextView>(R.id.mp_reading_rssi)
    }
}