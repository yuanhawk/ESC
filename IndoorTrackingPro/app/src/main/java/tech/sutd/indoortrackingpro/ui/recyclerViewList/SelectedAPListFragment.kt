package tech.sutd.indoortrackingpro.ui.recyclerViewList

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.asLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.datastore.Preferences
import tech.sutd.indoortrackingpro.databinding.FragmentSelectedApListBinding
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.ui.adapter.ApListAdapter
import tech.sutd.indoortrackingpro.ui.wifi.WifiViewModel
import tech.sutd.indoortrackingpro.utils.RvItemClickListener
import javax.inject.Inject

@AndroidEntryPoint
class SelectedAPListFragment : Fragment() {

    private val TAG = "SelectedAPListFragment"
    @Inject lateinit var handler: Handler
    @Inject lateinit var adapter: ApListAdapter
    @Inject lateinit var manager: LinearLayoutManager
    @Inject lateinit var pref: Preferences

    private lateinit var binding: FragmentSelectedApListBinding

    private val viewModel: WifiViewModel by hiltNavGraphViewModels(R.id.main)

    private val observer by lazy {
        Observer<RealmList<Account_mAccessPoints>> {
//            Log.d(TAG, "onResume: ${it[0]?.mac}")
            adapter.sendData(it)
            if (adapter.wifiList.isEmpty())
                GlobalScope.launch { pref.updateCheckAp(false) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_selected_ap_list, container, false)

        with(binding){
            selectedApListRv.adapter = adapter
            selectedApListRv.layoutManager = manager

            swipeRefresh.setOnRefreshListener {
                refreshObserver()
                swipeRefresh.isRefreshing = false
            }

            pref.mpAdded.asLiveData().observe(viewLifecycleOwner, { mpAdded ->
                if (mpAdded != true) {
                    apClearDatabase.setOnClickListener {
                        AlertDialog.Builder(context)
                            .setTitle("Would you like to delete all saved entries")
                            .setPositiveButton("yes") { _, _ ->
                                viewModel.clearAp()
                                GlobalScope.launch { pref.updateCheckAp(false) }
                            }
                            .setNegativeButton("no") { _, _ -> }.show()
                    }

                    activity?.applicationContext?.let {
                        RvItemClickListener(
                            it, object : RvItemClickListener.OnItemClickListener {
                                override fun onItemClick(view: View, position: Int) {
                                    AlertDialog.Builder(context)
                                        .setTitle("Would you like to delete this entry")
                                        .setPositiveButton("yes") { _, _ ->
                                            Log.d(TAG, "onItemClick: $position")
                                            val id = adapter.wifiList[position].id
                                            Log.d(TAG, "onItemClick: $id")
                                            viewModel.deleteAp(id)
                                        }
                                        .setNegativeButton("no") { _, _ -> }.show()
                                }
                            }
                        )
                    }?.let { selectedApListRv.addOnItemTouchListener(it) }
                } else Toast.makeText(context, "Please remove all your mapping points before adding APs", Toast.LENGTH_SHORT).show()
            })
        }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        refreshObserver()
    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            selectedApListRv.layoutManager = null
            selectedApListRv.adapter = null
        }
    }

    private fun refreshObserver() {
        if (viewModel.accessPoints()?.hasActiveObservers() == true)
            viewModel.accessPoints()?.removeObserver(observer)
        viewModel.accessPoints()?.observe(viewLifecycleOwner, observer)
    }

}