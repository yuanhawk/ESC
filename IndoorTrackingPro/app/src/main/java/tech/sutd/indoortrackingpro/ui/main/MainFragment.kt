package tech.sutd.indoortrackingpro.ui.main

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.realm.RealmList
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentMainBinding
import tech.sutd.indoortrackingpro.databinding.FragmentWifiListBinding
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.ui.adapter.ApListAdapter
import tech.sutd.indoortrackingpro.ui.wifi.WifiViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val TAG = "MainFragment"

    @Inject lateinit var apAdapter: ApListAdapter
    private val viewModel: WifiViewModel by hiltNavGraphViewModels(R.id.main)
    @Inject lateinit var manager: LinearLayoutManager
    lateinit var binding: FragmentMainBinding
    private val apObserver by lazy {
        Observer<RealmList<AccessPoint>> {
            apAdapter.sendData(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater, R.layout.fragment_main, container, false)
        binding.apRv.adapter = apAdapter
        binding.apRv.layoutManager = manager
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.apLd().observe(viewLifecycleOwner, apObserver)
    }

    override fun onPause() {
        super.onPause()
        if (viewModel.apLd().hasActiveObservers())
            viewModel.apLd().removeObserver(apObserver)
        viewModel.apLd().observe(viewLifecycleOwner, apObserver)
        binding.apRv.layoutManager = null
        binding.apRv.adapter = null
    }
}