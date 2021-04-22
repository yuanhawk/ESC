package tech.sutd.indoortrackingpro.utils

import android.os.Build
import android.view.WindowInsets
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

@Suppress("DEPRECATION")
fun AppCompatActivity.applyFullScreenWindow() {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R)
        window.insetsController?.hide(WindowInsets.Type.statusBars())
    else
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
}