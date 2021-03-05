package tech.sutd.indoortrackingpro.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding
import tech.sutd.indoortrackingpro.ui.fragments.HomeFragment
import tech.sutd.indoortrackingpro.ui.fragments.MappingFragment
import tech.sutd.indoortrackingpro.ui.fragments.TrackingFragment


@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {
//            HomeFragment()
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
        // val homeFragment = supportFragmentManager.findFragmentById(R.id.home) as SupportMapFragment
//        val trackingFragment = TrackingFragment()
//        val mappingFragment = MappingFragment()


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home_button -> supportFragmentManager.beginTransaction().
                replace(R.id.fragment_container, HomeFragment()).commit()
                R.id.ic_mapping_button -> mapFragment?.let { it1 ->
                    supportFragmentManager.beginTransaction().replace(R.id.fragment_container, it1).commit()
                }
//                R.id.ic_tracking_button -> supportFragmentManager.beginTransaction().
//                replace(R.id.fragment_container, homeFragment).commit()
            }
            true
        }

    }

    private val callback = OnMapReadyCallback { googleMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        val sydney = LatLng(1.3521, 103.8198)
        googleMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    private fun makeCurrentFragment(fragment: Fragment) =
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.fragment_container, fragment)
            commit()
        }
}