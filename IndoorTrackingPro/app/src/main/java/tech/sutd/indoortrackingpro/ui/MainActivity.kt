package tech.sutd.indoortrackingpro.ui

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.MotionEvent
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.NavGraph
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.onNavDestinationSelected
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding
import tech.sutd.indoortrackingpro.util.CustomZoomImageView
import java.util.*

@AndroidEntryPoint
class MainActivity : BaseActivity() {

    private val TAG = "MainActivity"

    private lateinit var location: FloatArray
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    private val dropDownList = arrayOf("Wifi", "Coordinates")

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment)?.findNavController()!!

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.mappingFragment, R.id.trackingFragment))
        setupActionBarWithNavController(navController, appBarConfiguration)

        with(binding) {
            bottomNavigation.setupWithNavController(navController)
            setContentView(root)
        }

        val dropDownAdapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, dropDownList)
        dropDownAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item)

        with(binding) {
            dropDown.adapter = dropDownAdapter
            dropDown.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onNothingSelected(parent: AdapterView<*>?) {
                }

                override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                    if (dropDown.selectedItemPosition == 0) {

                    }

                    if (dropDown.selectedItemPosition == 1) {

                    }
                }
            }
        }
    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.drop_down_menu, menu)
//        return true
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
////        when (item.itemId) {
////            wifi.setOnClickListener{ view ->
////                view?.findNavController()?.navigate(R.id.action_mainFragment_to_wifiListFragment)
////            R.id.list_of_ref_points_button -> {
//////                intent = Intent(this, ProjectDetailActivity::class.java)
//////                startActivity(intent)
////            }
////        }
//        val navController = findNavController(R.id.nav_host_fragment)
//        return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//    }
}










