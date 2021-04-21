package tech.sutd.indoortrackingpro.ui.tracking
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.launchFragmentInHiltContainer
import tech.sutd.indoortrackingpro.ui.mapping.MappingFragment


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class TrackingFragmentTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        val navHostController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<TrackingFragment> {
            Navigation.setViewNavController(requireView(), navHostController)
        }
    }

    @Test
    fun check_layout_display() {
        onView(withId(R.id.trackingFragment))
            .check(matches(isDisplayed()))
    }

    @Test
    fun check_map_layout_display(){
        onView(withId(R.id.fragment_tracking_map)).check(matches(isDisplayed()))
    }

    @Test
    fun check_map_displayed(){
        onView(withId(R.id.tracking_map)).check(matches(isDisplayed()))
    }

    @Test
    fun check_change_floor_button_displayed(){
        onView(withId(R.id.tracking_change_floor_button)).check(matches(isDisplayed()))
    }

    @Test
    fun check_change_floor_button_clickable(){
        onView(withId(R.id.tracking_change_floor_button)).check(matches(isClickable()))
    }


}


