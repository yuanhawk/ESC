package tech.sutd.indoortrackingpro.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import tech.sutd.indoortrackingpro.R

@RunWith(AndroidJUnit4::class)
class MapFragmentTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Before
    fun init() {
        activityRule.scenario.onActivity { activity ->
            activity.supportFragmentManager.beginTransaction()
        }
    }

    @Test
    fun testMapFragment() {
        onView(withId(R.id.activity_main)).check(matches(isDisplayed()))
    }
}