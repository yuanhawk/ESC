package tech.sutd.indoortrackingpro.ui

import android.content.Context
import android.net.wifi.ScanResult
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.CoordinatesListBinding
//import tech.sutd.indoortrackingpro.ui.fragments.CoordinatesModel
//import tech.sutd.indoortrackingpro.ui.fragments.CoordinatesViewHolder

//class CoordinatesListAdapter(
//        var coordinatesList: ArrayList<String> = ArrayList()) :
//    RecyclerView.Adapter<CoordinatesListAdapter.CoordinatesListViewHolder>() {
//
//    inner class CoordinatesListViewHolder(var binding: CoordinatesListBinding) : RecyclerView.ViewHolder(binding.root)
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoordinatesListViewHolder{
//        val binding = DataBindingUtil.inflate<CoordinatesListBinding>(
//            LayoutInflater.from(parent.context),
//            R.layout.coordinates_list, parent, false
//        )
//
//        return CoordinatesListViewHolder(binding)
//    }
//
//    override fun onBindViewHolder(holder: CoordinatesListViewHolder, position: Int) {
//        holder.binding.coordinatesList.text = coordinatesList[position]
//    }
//
//    override fun getItemCount(): Int = coordinatesList.size
//}
//
//val coordinates_comparator = object : DiffUtil.ItemCallback<CoordinatesModel> () {
//    override fun areItemsTheSame(oldItem: CoordinatesModel, newItem: CoordinatesModel): Boolean =
//        oldItem.id == newItem.id
//
//    override fun areContentsTheSame(oldItem: CoordinatesModel, newItem: CoordinatesModel): Boolean =
//        oldItem == newItem
//}
//
//class CoordinatesListAdapter : PagingDataAdapter<CoordinatesModel, CoordinatesViewHolder>(coordinates_comparator) {
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CoordinatesViewHolder =
//        CoordinatesViewHolder(
//                DataBindingUtil.inflate(
//                    LayoutInflater.from(parent.context),
//                    R.layout.coordinates_list, parent, false
//                )
//        )
//}