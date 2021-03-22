package tech.sutd.indoortrackingpro.ui

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding
import tech.sutd.indoortrackingpro.ui.fragments.HomeFragment
import tech.sutd.indoortrackingpro.ui.fragments.MappingFragment
import tech.sutd.indoortrackingpro.ui.fragments.TrackingFragment

import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val TAG = "MainActivity"
    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)

    private lateinit var location: FloatArray

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        with(binding) {

            map.setImageResource(R.drawable.map)

            map.setOnTouchListener(object : View.OnTouchListener{
                override fun onTouch(v: View?, event: MotionEvent?): Boolean {
                    if (event?.action == MotionEvent.ACTION_UP) {
                        location = floatArrayOf(event.x, event.y)
                        Log.d(TAG, "onCreate: ${location[0]}, ${location[1]}")
                        Toast.makeText(this@MainActivity,"Current location is: ${location[0]}, ${location[1]}", Toast.LENGTH_SHORT).show()

                        map.isEnabled = true
                        map.pos = location
                        map.invalidate()
                        return true
                    }
                    return false
                }
            })






        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
      
    


        binding.bottomNavigation.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.ic_home_button -> beginTransaction(HomeFragment())
                R.id.ic_mapping_button -> beginTransaction(MappingFragment())
//                R.id.ic_tracking_button -> supportFragmentManager.beginTransaction().
//                replace(R.id.fragment_container, homeFragment).commit()
            }
            true
        }

    }

