package tech.sutd.indoortrackingpro.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

import java.util.ArrayList
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.model.AccessPoint

/**
 * To show all selected wifi access points in a recycler view.
 * Use io.github.luizgrp lib to show access points and mapping points in different section inside a common recycler view
 *
 */
class AccessPointSection(sectionParameters: SectionParameters) : Section(sectionParameters) {
    private var accessPoints = ArrayList<AccessPoint>()
    override fun getContentItemsTotal(): Int {
        return accessPoints.size
    }

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder {
        return AccessPointViewHolder(view)
    }

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewHolder = holder as AccessPointViewHolder
        viewHolder.mac.text = accessPoints[position].mac
        viewHolder.ssid.text = accessPoints[position].ssid
    }

    fun setAccessPoints(accessPoints: ArrayList<AccessPoint>){
        this.accessPoints = accessPoints
    }



    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        super.onBindHeaderViewHolder(holder)
        val viewHolder = holder as TitleViewHolder
        viewHolder.title.text = "AccessPoints"
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return TitleViewHolder(view)
    }

    class AccessPointViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val mac: TextView = itemView.findViewById(R.id.item_ap_mac)
        val ssid: TextView = itemView.findViewWithTag(R.id.item_ap_ssid)
    }

}