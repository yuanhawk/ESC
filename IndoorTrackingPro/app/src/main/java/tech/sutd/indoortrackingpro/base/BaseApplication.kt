package tech.sutd.indoortrackingpro.base

import android.app.Application
import android.util.Log
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm
import io.realm.RealmConfiguration
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.utils.Constants
import java.util.*

@HiltAndroidApp
class BaseApplication : Application(){
    private lateinit var realm: Realm
    private val TAG = "REALM"
    override fun onCreate(): Unit {
        super.onCreate()
        Realm.init(this)

    }
}