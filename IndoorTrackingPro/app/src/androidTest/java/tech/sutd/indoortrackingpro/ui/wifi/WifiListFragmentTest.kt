package tech.sutd.indoortrackingpro.ui.wifi
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.launchFragmentInHiltContainer
import tech.sutd.indoortrackingpro.ui.main.MainFragment
import tech.sutd.indoortrackingpro.ui.mapping.MappingFragment

@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class WifiListFragmentTest {

    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        val navHostController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<WifiListFragment> {
            Navigation.setViewNavController(requireView(), navHostController)
        }
    }

    @Test
    fun test_layout(){
        onView(withId(R.id.wifi_list_fragment)).check(matches(isDisplayed()))
    }

    @Test
    fun test_button(){
        onView(withId(R.id.button_search_for_waps)).check(matches(isClickable()))
    }

    @Test
    fun test_recycler_view(){
        onView(withId(R.id.wifi_list_rv)).check(matches(isDisplayed()))
    }








}