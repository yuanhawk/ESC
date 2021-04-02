package tech.sutd.indoortrackingpro.ui.coordinates

import android.net.wifi.WifiManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentCoordinatesListBinding
import javax.inject.Inject

@AndroidEntryPoint
class CoordinatesListFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

        // Inflate layout for this fragment
        val binding = DataBindingUtil.inflate<FragmentCoordinatesListBinding>(
            inflater, R.layout.fragment_coordinates_list, container, false)

        // Back Button to go from coordinatesListFragment -> mainFragment
        with(binding) {
            coordinatesListBackButton.setOnClickListener{ view ->
                view?.findNavController()?.navigate(R.id.action_coordinatesListFragment_to_mainFragment)
            }
        }

        return binding.root
    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        val coordinatesAdapter = CoordinatesListAdapter()
//
//        coordinates_recyclerView.apply {
//            layoutManager = LinearLayoutManager(context)
//            sethasFixedSize(true)
//            adapter = coordinatesAdapter
//        }
//
//        viewLifecycleOwner.lifecycleScope.launch {
//            viewModel.coordinates.collectLatest {
//                coordinatesAdapter.submitData(it)
//            }
//        }
//    }
}