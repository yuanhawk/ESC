package tech.sutd.indoortrackingpro.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding
import tech.sutd.indoortrackingpro.databinding.FragmentMainBinding

@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = DataBindingUtil.inflate<FragmentMainBinding>(
            inflater, R.layout.fragment_main, container, false)

        // Wifi Button to go from mainFragment -> wifiListFragment
        with(binding) {
            buttonWifi.setOnClickListener{ view ->
                view?.findNavController()?.navigate(R.id.action_mainFragment_to_wifiListFragment)
            }
        }

        // Coordinates List Button to go from mainFragment -> coordinatesListFragment
        with(binding) {
            buttonCoordinatesList.setOnClickListener{ view ->
                view?.findNavController()?.navigate(R.id.action_mainFragment_to_coordinatesListFragment)
            }
        }

        return binding.root
    }

}