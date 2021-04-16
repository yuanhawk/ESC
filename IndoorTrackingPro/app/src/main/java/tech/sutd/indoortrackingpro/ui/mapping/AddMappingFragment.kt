package tech.sutd.indoortrackingpro.ui.mapping

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.data.AddMappingPointReceiver
import tech.sutd.indoortrackingpro.databinding.AddMappingBinding
import tech.sutd.indoortrackingpro.databinding.FragmentMainBinding
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.ui.MainActivity
import tech.sutd.indoortrackingpro.ui.wifi.WifiListFragment
import tech.sutd.indoortrackingpro.ui.wifi.WifiViewModel
import tech.sutd.indoortrackingpro.utils.coord
import javax.inject.Inject
@AndroidEntryPoint
class AddMappingFragment: BottomSheetDialogFragment() {

    private val viewModel: MappingViewModel by hiltNavGraphViewModels(R.id.main)
    lateinit var binding: AddMappingBinding
    lateinit var wifiReceiver: AddMappingPointReceiver
    @Inject lateinit var config: RealmConfiguration





    val TAG = "addMapping"

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View {

        binding = DataBindingUtil.inflate<AddMappingBinding>(
            inflater,
            R.layout.add_mapping,
            container,
            false
        )


        with(binding) {

            val coordinate = arguments?.getFloatArray(coord)

            backButtonAddMapping.setOnClickListener {
                if (findNavController().previousBackStackEntry?.equals(R.id.mappingFragment) == true)
                    findNavController().popBackStack(R.id.mappingFragment, false)
                findNavController().navigate(R.id.action_addMappingDialog_to_mappingFragment)
            }

            yesButtonAddMapping.setOnClickListener {
//                val realm1  = Realm.getInstance(config)
//                var apList: RealmList<AccessPoint> = realm1.where(Account::class.java).findFirst()?.mAccessPoints!!
                if (!MainActivity.apAdded) {
//                    findNavController().popBackStack(R.id.mainFragment, false)
                    Toast.makeText(
                        activity,
                        "Wifi Access Points are required first. Please proceed to add Access Points first",
                        Toast.LENGTH_SHORT
                    ).show()
//                    findNavController().navigate(R.id.action_selectedMPListFragment_to_wifiListFragment)
//                    val intent = Intent(context,WifiListFragment::class.java)
//                            startActivity(intent)

                } else{
                    findNavController().popBackStack(R.id.mainFragment, false)
                    viewModel.insertMp(coordinate!!, wifiReceiver.mappingPoint)
                    Toast.makeText(activity, "Coordinates added!", Toast.LENGTH_SHORT).show()
                    MainActivity.mpAdded = true
                    findNavController().navigate(R.id.action_mainFragment_to_selectedMPListFragment)
//                }

                yesButtonAddMapping.isEnabled = false
                xAddMapping.text = coordinate?.get(0)?.toString() ?: ""
                yAddMapping.text = coordinate?.get(1)?.toString() ?: ""
                isCancelable = false}
            } }
            val realm = Realm.getInstance(config)
            wifiReceiver = AddMappingPointReceiver(this, realm)
            return binding.root

    }

    override fun onResume() {
        super.onResume()
        requireActivity().registerReceiver(wifiReceiver, IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION))
        wifiReceiver.start()
    }

    override fun onPause() {
        super.onPause()
        requireActivity().unregisterReceiver(wifiReceiver)
    }
}