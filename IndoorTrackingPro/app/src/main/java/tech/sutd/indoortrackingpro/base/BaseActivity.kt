package tech.sutd.indoortrackingpro.base

import android.os.Bundle
import android.util.Log
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.AndroidEntryPoint
import io.realm.mongodb.sync.SyncSession
import tech.sutd.indoortrackingpro.data.helper.AlgoHelper
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private val TAG = "BaseActivity"

    @Inject lateinit var algoHelper: AlgoHelper
    @Inject lateinit var syncSession: SyncSession

    protected inline fun <reified T : ViewDataBinding> binding(
            @LayoutRes resId: Int
    ): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.setContentView(this, resId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.v("EXAMPLE", "Sync state: ${syncSession.connectionState}")
        syncSession.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.v("EXAMPLE", "Sync state: ${syncSession.connectionState}")
        syncSession.stop()
    }
}