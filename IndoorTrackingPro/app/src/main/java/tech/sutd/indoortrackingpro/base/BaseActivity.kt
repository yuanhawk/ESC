package tech.sutd.indoortrackingpro.base

import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.viewModels
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.data.helper.AlgoHelper
import tech.sutd.indoortrackingpro.ui.wifi.WifiViewModel
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    private val TAG = "BaseActivity"

    @Inject lateinit var algoHelper: AlgoHelper
    private val viewModel by viewModels<WifiViewModel>()

    protected inline fun <reified T : ViewDataBinding> binding(
            @LayoutRes resId: Int
    ): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.setContentView(this, resId) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.pull()
    }
}