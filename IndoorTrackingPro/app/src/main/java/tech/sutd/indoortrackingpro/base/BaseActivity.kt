package tech.sutd.indoortrackingpro.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
abstract class BaseActivity : AppCompatActivity() {

    protected inline fun <reified T : ViewDataBinding> binding(
            @LayoutRes resId: Int
    ): Lazy<T> = lazy(LazyThreadSafetyMode.NONE) { DataBindingUtil.setContentView(this, resId) }

}