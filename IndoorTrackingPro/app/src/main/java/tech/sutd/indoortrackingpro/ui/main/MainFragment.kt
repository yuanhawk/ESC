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
import tech.sutd.indoortrackingpro.ui.wifi.MpListAdapter
import tech.sutd.indoortrackingpro.ui.wifi.WifiViewModel
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {

    private val TAG = "MainFragment"

    @Inject lateinit var apAdapter: ApListAdapter
    @Inject lateinit var mpAdapter: MpListAdapter
    private val viewModel: WifiViewModel by hiltNavGraphViewModels(R.id.main)
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate(
            inflater, R.layout.fragment_main, container, false)

        with(binding) {
            apRv.adapter = apAdapter
            apRv.layoutManager = LinearLayoutManager(context)
//            mpRV.adapter = mpAdapter
//            mpRv.layoutManager = LinearLayoutManager(context)
        }

//        val ap = AccessPoint()
//        ap.mac = "50"
//        ap.ssid = "1s4"
//        apAdapter.sendData(ap)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        viewModel.accessPoints()?.observe(viewLifecycleOwner, {
//            Log.d(TAG, "onResume: ${it[0]?.mac}")
            apAdapter.sendData(it)
            apAdapter.notifyDataSetChanged()
        })

        viewModel.mappingPoint()?.observe(viewLifecycleOwner, {
//            Log.d(TAG, "onResume: ${it[0]?.x}")
            mpAdapter.sendData(it)
            apAdapter.notifyDataSetChanged()
        })
    }

    override fun onPause() {
        super.onPause()
        with(binding) {
            apRv.layoutManager = null
            apRv.adapter = null
//            mpRv.adapter = null
//            mpRv.layoutManager = null
        }

    }
}