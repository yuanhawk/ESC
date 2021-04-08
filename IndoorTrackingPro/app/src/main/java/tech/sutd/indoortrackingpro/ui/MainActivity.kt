package tech.sutd.indoortrackingpro.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding
import tech.sutd.indoortrackingpro.utils.retrieveGpsPermission

@AndroidEntryPoint
class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val TAG = "MainActivity"

    private lateinit var location: FloatArray
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
//    private lateinit var addMappingButton: FloatingActionButton

    private val dropDownList = arrayOf("Wifi", "Coordinates")

    @SuppressLint("InflateParams")
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        retrieveGpsPermission(this)

        navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment)?.findNavController()!!

        with(binding) {
            bottomNavigation.setOnNavigationItemSelectedListener(this@MainActivity)
            bottomNavigation.setupWithNavController(navController)
            setContentView(root)
            button.setOnClickListener{ v -> setupMainMenu(PopupMenu(this@MainActivity, v)) }
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        navController.popBackStack(R.id.mainFragment, false)
        return when (item.itemId) {
            R.id.mappingFragment -> {
                navController.navigate(R.id.mappingFragment)
                true
            }
            R.id.trackingFragment -> {
                navController.navigate(R.id.trackingFragment)
                true
            }
            else -> false
        }
    }

    private fun setupMainMenu(popupMenu: PopupMenu) {
        popupMenu.setOnMenuItemClickListener { item ->
            navController.popBackStack(R.id.mainFragment, false)
            when (item.itemId) {
                R.id.wifi -> {
                    navController.navigate(R.id.wifiListFragment)
                    return@setOnMenuItemClickListener true
                }
                R.id.coordinates -> {
                    navController.navigate(R.id.coordinatesListFragment)
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }
        popupMenu.inflate(R.menu.top_drop_down_menu)
        popupMenu.show()
    }

    fun setFloatingActionBtn() {
//                    val addMappingButton = findViewById<FloatingActionButton>(R.id.fab)
        val inflater: LayoutInflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = inflater.inflate(R.layout.add_mapping,null)

        val addMappingPopUp = PopupWindow(
            view,
            LinearLayout.LayoutParams.WRAP_CONTENT,
            LinearLayout.LayoutParams.WRAP_CONTENT
        )

        val slideIn = Slide()
        slideIn.slideEdge = Gravity.TOP
        addMappingPopUp.enterTransition = slideIn

        val slideOut = Slide()
        slideOut.slideEdge = Gravity.BOTTOM
        addMappingPopUp.enterTransition = slideOut

//                val addMappingView = findViewById<TextView>(R.id.adding_coordinates)
//                val addMappingBackButton = findViewById<TextView>(R.id.back_button_add_mapping)
//
//                addMappingBackButton.setOnClickListener {
//                    addMappingPopUp.dismiss()
//                }
    }
}










