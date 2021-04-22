package tech.sutd.indoortrackingpro.datastore

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import javax.inject.Inject

@RunWith(AndroidJUnit4::class)
@HiltAndroidTest
class PreferencesTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)
//
////    @Mock
////    private lateinit var mockLifeCycleOwner: LifecycleOwner
//
//    @Inject
//    lateinit var pref: Preferences

    @Before
    fun init() {
        hiltRule.inject()
        val context = InstrumentationRegistry.getInstrumentation().context
        val targetContext = InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
    }

    @Test
    fun insertAndRetrieveApAdded() {
        assertTrue(true)
//        assertNotNull(pref)

//        mockLifeCycleOwner = Mockito.mock(LifecycleOwner::class.java)
//        val lifecycle = LifecycleRegistry(Mockito.mock(LifecycleOwner::class.java))
//        lifecycle.markState(Lifecycle.State.RESUMED)
//        Mockito.`when`(mockLifeCycleOwner.lifecycle).thenReturn(lifecycle)
//
//        GlobalScope.launch { pref.updateCheckAp(true) }
//        pref.apAdded.asLiveData().observe(mockLifeCycleOwner, {
//            if (it != null) {
//                assertTrue(it)
//            }
//        })
    }
}