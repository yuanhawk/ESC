package tech.sutd.indoortrackingpro.ui.recyclerViewList

import android.app.AlertDialog
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import io.realm.RealmList
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.databinding.FragmentInaccuracyListBinding
import tech.sutd.indoortrackingpro.datastore.Preferences
import tech.sutd.indoortrackingpro.databinding.FragmentSelectedApListBinding
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.Account_mAccessPoints
import tech.sutd.indoortrackingpro.ui.adapter.ApListAdapter
import tech.sutd.indoortrackingpro.ui.adapter.InaccuracyAdapter
import tech.sutd.indoortrackingpro.ui.wifi.WifiViewModel
import tech.sutd.indoortrackingpro.utils.RvItemClickListener
import javax.inject.Inject

@AndroidEntryPoint
class InaccuracyListFragment : Fragment() {

    private val TAG = "SelectedAPListFragment"

    @Inject lateinit var realm: Realm
    lateinit var adapter: InaccuracyAdapter
    @Inject lateinit var manager: LinearLayoutManager
    @Inject lateinit var pref: Preferences
    private lateinit var binding: FragmentInaccuracyListBinding



    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        adapter = InaccuracyAdapter()
        // Inflate layout for this fragment
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_inaccuracy_list, container, false)

        with(binding){
            inaccuracyRecycler.adapter = adapter
            inaccuracyRecycler.layoutManager = manager
        }
        adapter.sendData(realm.where(Account::class.java).findFirst()!!.Inaccuracy)
        return binding.root
    }


    override fun onPause() {
        super.onPause()
        with(binding) {
            inaccuracyRecycler.layoutManager = null
            inaccuracyRecycler.adapter = null
        }
    }



}