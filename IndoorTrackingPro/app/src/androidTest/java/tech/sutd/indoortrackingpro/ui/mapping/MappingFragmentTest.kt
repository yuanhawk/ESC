package tech.sutd.indoortrackingpro.ui.mapping

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


@MediumTest
@HiltAndroidTest
@ExperimentalCoroutinesApi
class MappingFragmentTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        val navHostController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<MappingFragment> {
            Navigation.setViewNavController(requireView(), navHostController)
        }
    }

    @Test
    fun check_layout_display() {
        onView(withId(R.id.mappingFragment))
            .check(matches(isDisplayed()))
    }

    @Test
    fun check_map_layout_display(){
        onView(withId(R.id.fragment_map)).check(matches(isDisplayed()))
    }

    @Test
    fun check_map_displayed(){
        onView(withId(R.id.map)).check(matches(isDisplayed()))
    }

    @Test
    fun check_change_floor_button_displayed(){
        onView(withId(R.id.mapping_change_floor_button)).check(matches(isDisplayed()))
    }

    @Test
    fun check_change_floor_button_clickable(){
        onView(withId(R.id.mapping_change_floor_button)).check(matches(isClickable()))
    }


}

