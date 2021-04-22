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
class AddMappingFragmentTest {
    @get: Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
        val navHostController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<AddMappingFragment> {
            Navigation.setViewNavController(requireView(), navHostController)
        }
    }

    @Test
    fun check_layout_display() {
        onView(withId(R.id.addMapping))
            .check(matches(isDisplayed()))
    }

    @Test
    fun check_yes_button_clickable(){
        onView(withId(R.id.yes_button_add_mapping)).check(matches(isClickable()))
    }


}


