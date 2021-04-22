package tech.sutd.indoortrackingpro.ui

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityMainBinding
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.utils.retrieveGpsPermission
import java.util.*
import javax.inject.Inject


@AndroidEntryPoint
class MainActivity : BaseActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private val TAG = "MainActivity"

    @Inject lateinit var realm: Realm
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

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
            button.setOnClickListener { v -> setupMainMenu(PopupMenu(this@MainActivity, v)) }
        }

        if (realm.where(Account::class.java).findAll().isEmpty()) {
            Log.d("Create Realm", "Realm")
            realm.executeTransactionAsync(
                Realm.Transaction { realm ->
                    realm.createObject(Account::class.java, UUID.randomUUID().toString())
                },
                Realm.Transaction.OnError {
                    Log.d("REALM", "Fail to create realm")
                }
            )
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
                R.id.wifi_ap_list -> {
                    navController.navigate(R.id.wifiListFragment)
                    return@setOnMenuItemClickListener true
                }
                R.id.selected_ap_list -> {
                    navController.navigate(R.id.selectedAPListFragment)
                    return@setOnMenuItemClickListener true
                }
                R.id.selected_mp_list -> {
                    navController.navigate(R.id.selectedMPListFragment)
                    return@setOnMenuItemClickListener true
                }
                R.id.inaccuracy_list -> {
                    navController.navigate(R.id.inaccuracyListFragment)
                    return@setOnMenuItemClickListener true
                }
                else -> false
            }
        }
        popupMenu.inflate(R.menu.top_drop_down_menu)

        try {
            val fieldMPopup = PopupMenu::class.java.getDeclaredField("mPopup")
            fieldMPopup.isAccessible = true
            val mPopup = fieldMPopup.get(popupMenu)
            mPopup.javaClass
                .getDeclaredMethod("setForceShowIcon", Boolean::class.java)
                .invoke(mPopup, true)
        }

        catch (e: Exception) {
            Log.e("Main", "Error showing menu icons.", e)
        }

        finally {
            popupMenu.show()
        }
    }
}










