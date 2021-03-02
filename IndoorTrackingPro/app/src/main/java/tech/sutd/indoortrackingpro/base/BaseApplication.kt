package tech.sutd.indoortrackingpro.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import io.realm.Realm

@HiltAndroidApp
class BaseApplication : Application(){

    override fun onCreate(): Unit {
        super.onCreate()
        Realm.init(this)
    }
}