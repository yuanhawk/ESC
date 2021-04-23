package tech.sutd.indoortrackingpro.ui.wifi

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentFactory
import tech.sutd.indoortrackingpro.ui.adapter.ApListAdapter
import tech.sutd.indoortrackingpro.ui.adapter.WifiListAdapter
import tech.sutd.indoortrackingpro.ui.recyclerViewList.SelectedAPListFragment
import javax.inject.Inject

class ApListFragmentFactory @Inject constructor(
    private val apListAdapter: ApListAdapter):
        FragmentFactory(){
    override fun instantiate(classLoader: ClassLoader, className: String): Fragment {
        return when(className){
            SelectedAPListFragment::class.java.name -> SelectedAPListFragment(apListAdapter)
            else -> super.instantiate(classLoader, className)
        }



    }
        }

