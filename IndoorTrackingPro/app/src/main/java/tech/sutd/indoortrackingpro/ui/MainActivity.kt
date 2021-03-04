package tech.sutd.indoortrackingpro.ui

import android.graphics.Color
import android.os.Bundle
import com.google.android.gms.maps.CameraUpdateFactory

import com.google.android.gms.maps.GoogleMap

import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding




@AndroidEntryPoint
class MainActivity : BaseActivity() {
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {

            val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
            mapFragment?.getMapAsync(callback)
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
        val markerLoc = LatLng((1.340701176012829 + 1.3413275446492632) / 2 , (103.9618445759041 + 103.96312593771422) /2 )
        val circleOptions = CircleOptions()
                .center(markerLoc)
                .radius(1.0).strokeColor(Color.BLUE)
                .fillColor(Color.argb(128, 0, 0, 255))



        val circle = googleMap.addCircle(circleOptions)




//        val floorPlanLoc = LatLng(1.340990, 103.962602)
        val bounds = LatLngBounds(LatLng(1.340701176012829, 103.9618445759041),
        LatLng(1.3413275446492632, 103.96312593771422));

            googleMap.addMarker(MarkerOptions().position(markerLoc)).setIcon(BitmapDescriptorFactory.defaultMarker())
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(markerLoc))

        val floorPlan = GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.drawable.floor_plan_level_1)).positionFromBounds(bounds)
        googleMap.addGroundOverlay(floorPlan)

        }


}



