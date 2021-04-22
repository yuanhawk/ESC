package tech.sutd.indoortrackingpro.ui.recyclerViewList


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
class SelectedMPListFragmentTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        val navHostController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<SelectedMPListFragment> {
            Navigation.setViewNavController(requireView(), navHostController)
        }
    }

    @Test
    fun check_layout_is_displayed() {
        onView(withId(R.id.swipe_refresh))
            .check(matches(isDisplayed()))
    }

    @Test
    fun check_recycle_view_displayed(){
        onView(withId(R.id.selected_mp_list_rv)).check(matches(isDisplayed()))
    }

    @Test
    fun check_button_displayed(){
        onView(withId(R.id.mp_clear_database)).check(matches(isDisplayed()))
    }

    @Test
    fun check_button_clickable(){
        onView(withId(R.id.mp_clear_database)).check(matches(isClickable()))
    }


}
