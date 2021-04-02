package tech.sutd.indoortrackingpro.ui.wifi

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import tech.sutd.indoortrackingpro.R

class WifiListAdapter(
    private val context: WifiListFragment,
    private val data: ArrayList<ArrayList<String>>
) : RecyclerView.Adapter<WifiListAdapter.WAPListViewHolder>() {

    inner class WAPListViewHolder internal constructor(itemView: View) : RecyclerView.ViewHolder(itemView){
        private var mac: TextView = itemView.findViewById(R.id.mac)
        private var ssid: TextView = itemView.findViewById(R.id.ssid)
        private var rssi: TextView = itemView.findViewById(R.id.rssi)
//        private var checkBox: CheckBox = itemView.findViewById(R.id.checkbox_delete)

        internal fun bind(position: Int){
            mac.text = data[position][0]
            ssid.text = data[position][1]
            rssi.text = data[position][2]
        }

//        init{
//            itemView.setOnClickListener { checkBox.isChecked = !checkBox.isChecked }
//            checkBox.setOnCheckedChangeListener{_, isChecked ->
//                val itemText = data[adapterPosition]
//
//            }
//        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WAPListViewHolder {
        return WAPListViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.wap_list, parent, false)
        )


    }

    override fun onBindViewHolder(holder: WAPListViewHolder, position: Int) {

//        val itemText = data[position]

    }

    override fun getItemCount(): Int = 10

}