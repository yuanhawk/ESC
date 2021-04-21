package tech.sutd.indoortrackingpro.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint
import tech.sutd.indoortrackingpro.R

@AndroidEntryPoint
class SplashFragment: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_splash)

        Handler().postDelayed({
            val intent = Intent(this@SplashFragment, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

        supportActionBar?.hide()
    }
}