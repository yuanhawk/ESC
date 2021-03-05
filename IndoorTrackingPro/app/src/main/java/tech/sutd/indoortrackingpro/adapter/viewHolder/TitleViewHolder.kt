package tech.sutd.indoortrackingpro.adapter.viewHolder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tech.sutd.indoortrackingpro.R

class TitleViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
    val title: TextView = itemView.findViewById(R.id.point_section_title)
}