package tech.sutd.indoortrackingpro.base

import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.data.helper.AlgoHelper
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Coordinate
import tech.sutd.indoortrackingpro.model.MappingPoint
import javax.inject.Inject

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    @Inject
    lateinit var algoHelper: AlgoHelper

    protected inline fun <reified T : ViewDataBinding> binding(
            @LayoutRes resId: Int
    ): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.setContentView(this, resId) }

    fun predictCoordinate(
            wifiData: MappingPoint,
            account: Account,
            whichAlgo: String
    ): Coordinate =
        algoHelper.predictCoordinate(wifiData, account, whichAlgo)!!
}