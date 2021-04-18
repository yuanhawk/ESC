package tech.sutd.indoortrackingpro.ui.coordinates

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentSelectedMpListBinding
import tech.sutd.indoortrackingpro.model.Account_mMappingPoints
import tech.sutd.indoortrackingpro.ui.wifi.MpListAdapter
import tech.sutd.indoortrackingpro.ui.wifi.WifiViewModel
import javax.inject.Inject

@AndroidEntryPoint
class SelectedMPListFragment : Fragment() {

//    private val TAG = "SelectedMPListFragment"

    @Inject lateinit var realm: Realm
    @Inject lateinit var handler: Handler
    @Inject lateinit var adapter: MpListAdapter
    @Inject lateinit var manager: LinearLayoutManager

    private lateinit var binding: FragmentSelectedMpListBinding

    private val viewModel: WifiViewModel by hiltNavGraphViewModels(R.id.main)

    private val observer by lazy {
        Observer<RealmList<Account_mMappingPoints>> {
            adapter.sendData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        // Inflate layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_selected_mp_list, container, false)

        with(binding){
            selectedMpListRv.adapter = adapter
            selectedMpListRv.layoutManager = manager
            swipeRefresh.setOnRefreshListener {
                refreshObserver()
                swipeRefresh.isRefreshing = false
            }
        }

//        activity?.applicationContext?.let {
//            RvItemClickListener(
//                it, object : RvItemClickListener.OnItemClickListener {
//                    override fun onItemClick(view: View, position: Int) {
//                        val mappingPoint = MappingPoint()
//                        mappingPoint.x = mapList[position].x
//                        mappingPoint.y = mapList[position].y
//
//                        viewModel.mappingPoint()
//
//                        findNavController().popBackStack(R.id.selectedMPListFragment, false)
//                    }
//                }
//            )
//        }?.let { binding.selectedMpListRv.addOnItemTouchListener(it) }

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        refreshObserver()
    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            selectedMpListRv.layoutManager = null
            selectedMpListRv.adapter = null
        }
    }

    private fun refreshObserver() {
        if (viewModel.mappingPoint()?.hasActiveObservers() == true)
            viewModel.mappingPoint()?.removeObserver(observer)
        viewModel.mappingPoint()?.observe(viewLifecycleOwner, observer)
    }

}