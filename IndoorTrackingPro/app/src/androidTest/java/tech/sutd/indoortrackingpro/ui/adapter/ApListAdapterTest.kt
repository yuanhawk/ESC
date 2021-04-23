package tech.sutd.indoortrackingpro.ui.adapter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.filters.MediumTest
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.launchFragmentInHiltContainer
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.ui.recyclerViewList.SelectedAPListFragment
import tech.sutd.indoortrackingpro.ui.wifi.ApListFragmentFactory
import javax.inject.Inject


@HiltAndroidTest
@MediumTest
@ExperimentalCoroutinesApi
class ApListAdapterTest{

    @get: Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var fragmentFactory: ApListFragmentFactory

    @Before
    fun setup() {
    hiltRule.inject()
    }

    @Test
    fun test_wifi_adapter() {
        val navController = mock(NavController::class.java)
        val accessPoints = arrayListOf(Account_mAccessPoints()) as List<Account_mAccessPoints>
        launchFragmentInHiltContainer<SelectedAPListFragment>(fragmentFactory = fragmentFactory ) {
            Navigation.setViewNavController(requireView(),navController)
            adapter.wifiList = accessPoints
        }
        onView(withId(R.id.selected_ap_list_rv)).perform(RecyclerViewActions.actionOnItemAtPosition<ApListAdapter.ApListViewHolder>(0,click()))
        assert(accessPoints[0].mac == "")
    }


    }



