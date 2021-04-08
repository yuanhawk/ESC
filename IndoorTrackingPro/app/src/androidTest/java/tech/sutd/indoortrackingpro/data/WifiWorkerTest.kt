package tech.sutd.indoortrackingpro.data

import android.content.Context
import android.net.wifi.WifiManager
import android.util.Log
import androidx.lifecycle.LifecycleOwner
import androidx.test.espresso.matcher.ViewMatchers.assertThat
import androidx.test.platform.app.InstrumentationRegistry
import androidx.work.Configuration
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import androidx.work.impl.utils.SynchronousExecutor
import androidx.work.testing.WorkManagerTestInitHelper
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import junit.framework.Assert.assertNotNull
import org.hamcrest.core.Is.`is`
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

    private lateinit var context: Context
    private lateinit var targetContext: Context
    private lateinit var configuration: Configuration
    private lateinit var workManager: WorkManager

    @Before
    fun init() {
        hiltRule.inject()
        context = InstrumentationRegistry.getInstrumentation().context
        targetContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
        configuration = Configuration.Builder()
                .setExecutor(SynchronousExecutor())
                .setMinimumLoggingLevel(Log.DEBUG)
                .build()
        // Initialize WorkManager using the WorkManagerTestInitHelper.
        WorkManagerTestInitHelper.initializeTestWorkManager(targetContext, configuration)
        workManager = WorkManager.getInstance(targetContext)
    }

    @Test
    fun testScanResult() {
        val results = wifiManager.scanResults
        for (result in results) {
            assertNotNull(result.SSID)
            assertNotNull(result.level)
        }
    }

    @Test
    fun testWorkManager() {
//        // Create request
//        val request = OneTimeWorkRequestBuilder<WifiWorker>()
//                .build()
//
//        val workManager = WorkManager.getInstance(targetContext)
//        // Enqueue and wait for result. This also runs the Worker synchronously
//        // because we are using a SynchronousExecutor.
//        workManager.enqueue(request).result.get()
//        // Get WorkInfo and outputData
//        val outputData = workManager.getWorkInfoById(request.id).get().outputData
//
//        // Only resultSuccess data will be retrieved!
//        assertNotNull(outputData)
    }
}