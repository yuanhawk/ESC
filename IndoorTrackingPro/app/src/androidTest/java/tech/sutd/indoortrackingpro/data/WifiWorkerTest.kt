package tech.sutd.indoortrackingpro.data

import android.net.wifi.WifiManager
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertNotNull
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
class WifiWorkerTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var wifiManager: WifiManager

    @Before
    fun init() {
        hiltRule.inject()
    }

    @Test
    fun testScanResult() {
        val results = wifiManager.scanResults
        for (result in results) {
            assertNotNull(result.SSID)
            assertNotNull(result.level)
        }
    }
}