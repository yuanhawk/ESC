package tech.sutd.indoortrackingpro.adapter.viewHolder

import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView

import java.util.ArrayList
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters
import io.github.luizgrp.sectionedrecyclerviewadapter.Section
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.model.MappingPoint

/**
 * To show all selected wifi access points in a recycler view.
 * Use io.github.luizgrp lib to show access points and mapping points in different section inside a common recycler view
 *
 */
class MappingPointSection(sectionParameters: SectionParameters?) : Section(sectionParameters) {
    private var mappingPoints = ArrayList<MappingPoint>()
    override fun getContentItemsTotal(): Int = mappingPoints.size

    override fun getItemViewHolder(view: View): RecyclerView.ViewHolder = MappingPointViewHolder(view)

    override fun onBindItemViewHolder(holder: RecyclerView.ViewHolder?, position: Int) {
        val viewHolder = holder as MappingPointViewHolder
        viewHolder.x.text = mappingPoints[position].latitude.toString()
        viewHolder.y.text = mappingPoints[position].longitude.toString()
    }
    class MappingPointViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val x: TextView = itemView.findViewById(R.id.item_mp_x)
        val y: TextView = itemView.findViewById(R.id.item_mp_y)
    }

    override fun onBindHeaderViewHolder(holder: RecyclerView.ViewHolder?) {
        super.onBindHeaderViewHolder(holder)
        (holder as TitleViewHolder).title.text = "Mapping Points"
    }

    override fun getHeaderViewHolder(view: View): RecyclerView.ViewHolder {
        return TitleViewHolder(view)
    }

    fun setMappingPoints(list: ArrayList<MappingPoint>){
        this.mappingPoints = list
    }
}