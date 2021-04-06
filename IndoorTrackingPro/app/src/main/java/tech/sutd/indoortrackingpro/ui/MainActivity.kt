package tech.sutd.indoortrackingpro.ui

import android.annotation.SuppressLint
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.transition.Slide
import android.transition.TransitionManager
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.utils.retrieveGpsPermission
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding

@AndroidEntryPoint
class MainActivity : BaseActivity() {

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

        val appBarConfiguration =
            AppBarConfiguration(setOf(R.id.mappingFragment, R.id.trackingFragment))
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

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    if (dropDown.selectedItemPosition == 0) {

                    }

                    if (dropDown.selectedItemPosition == 1) {

                    }
                }
            }
        }

        with(binding) {
        }
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










