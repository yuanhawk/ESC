package tech.sutd.indoortrackingpro.ui.recyclerViewList

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentInaccuracyListBinding
import tech.sutd.indoortrackingpro.datastore.Preferences
import tech.sutd.indoortrackingpro.databinding.FragmentSelectedApListBinding
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_Inaccuracy
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import tech.sutd.indoortrackingpro.ui.adapter.ApListAdapter
import tech.sutd.indoortrackingpro.ui.adapter.InaccuracyAdapter
import tech.sutd.indoortrackingpro.ui.tracking.TrackingViewModel
import tech.sutd.indoortrackingpro.ui.wifi.WifiViewModel
import tech.sutd.indoortrackingpro.utils.RvItemClickListener
import javax.inject.Inject

@AndroidEntryPoint
class InaccuracyListFragment : Fragment() {

    private val TAG = "SelectedAPListFragment"

    private val viewModel by hiltNavGraphViewModels<TrackingViewModel>(R.id.main)
    lateinit var adapter: InaccuracyAdapter
    @Inject lateinit var manager: LinearLayoutManager
    @Inject lateinit var pref: Preferences
    private lateinit var binding: FragmentInaccuracyListBinding

    private val observer by lazy {
        Observer<RealmList<Account_Inaccuracy>> {
            adapter.sendData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = InaccuracyAdapter()
        // Inflate layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_inaccuracy_list, container, false)

        with(binding){
            inaccuracyRecycler.adapter = adapter
            inaccuracyRecycler.layoutManager = manager

            apClearDatabase.setOnClickListener{
                AlertDialog.Builder(context)
                    .setTitle("Would you like to delete all saved entries")
                    .setPositiveButton("yes") { _, _ ->
                        viewModel.clearInAccuracy()
                        GlobalScope.launch { pref.updateCheckMp(false) }
                    }
                    .setNegativeButton("no") { _, _ -> }.show()
            }

            swipeRefresh.setOnRefreshListener {
                refreshObserver()
                swipeRefresh.isRefreshing = false
            }

            activity?.applicationContext?.let {
                RvItemClickListener(
                    it, object : RvItemClickListener.OnItemClickListener{
                    override fun onItemClick(view: View, position: Int) {
                        AlertDialog.Builder(context)
                            .setTitle("Would you like to delete this entry")
                            .setPositiveButton("yes") { _, _ ->
                                Log.d(TAG, "onItemClick: $position")
                                val id = adapter.inaccuracyList[position]?.id
                                Log.d(TAG, "onItemClick: $id")
                                if (id != null) {
                                    viewModel.deleteInAccuracy(id)
                                }
                            }
                            .setNegativeButton("no") { _, _ -> }.show()
                    }
                })
            }?.let { inaccuracyRecycler.addOnItemTouchListener(it) }
        }
        viewModel.getInaccuracyList()?.let { adapter.sendData(it) }
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        refreshObserver()
    }


    override fun onPause() {
        super.onPause()
        with(binding) {
            inaccuracyRecycler.layoutManager = null
            inaccuracyRecycler.adapter = null
        }
    }

    private fun refreshObserver() {
        if (viewModel.inaccuracy()?.hasActiveObservers() == true)
            viewModel.inaccuracy()?.removeObserver(observer)
        viewModel.inaccuracy()?.observe(viewLifecycleOwner, observer)
    }
}