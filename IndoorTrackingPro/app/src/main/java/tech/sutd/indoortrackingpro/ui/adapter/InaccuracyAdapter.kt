package tech.sutd.indoortrackingpro.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import io.realm.RealmList
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentInaccuracyListBinding
import tech.sutd.indoortrackingpro.databinding.InaccuracyListBinding
import tech.sutd.indoortrackingpro.databinding.SelectedMpListBinding
import tech.sutd.indoortrackingpro.model.Account_Inaccuracy
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import java.math.RoundingMode

class InaccuracyAdapter : RecyclerView.Adapter<InaccuracyAdapter.InaccuracyViewHolder>() {

    private val TAG = "MpListAdapter"

    private var inaccuracyList: RealmList<Account_Inaccuracy> = RealmList()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InaccuracyViewHolder {
        val binding = DataBindingUtil.inflate<InaccuracyListBinding>(
            LayoutInflater.from(parent.context),
            R.layout.inaccuracy_list, parent, false
        )
        return InaccuracyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: InaccuracyViewHolder, position: Int) {
        with(holder.binding) {
            inaccuracyListX.text = (inaccuracyList[position]?.x!!/20.0).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
            inaccracyListY.text = (inaccuracyList[position]?.y!!/20.0).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
            inaccracyListInaccuracy.text = (inaccuracyList[position]?.inaccuracy!!/20.0).toBigDecimal().setScale(2, RoundingMode.UP).toDouble().toString()
        }
    }

    fun sendData(inaccuracyList: RealmList<Account_Inaccuracy>) {
        this.inaccuracyList = inaccuracyList
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = inaccuracyList.size

    inner class InaccuracyViewHolder(var binding: InaccuracyListBinding) :
        RecyclerView.ViewHolder(binding.root)
}