package tech.sutd.indoortrackingpro.ui

import tech.sutd.indoortrackingpro.model.Account
import android.os.Bundle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import dagger.hilt.android.AndroidEntryPoint

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.realm.Realm
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.adapter.viewHolder.AccessPointSection
import tech.sutd.indoortrackingpro.adapter.viewHolder.MappingPointSection
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.databinding.ActivityProjectDetailBinding
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.model.MappingPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProjectDetailActivity : BaseActivity(){

    @Inject lateinit var realm: Realm
    @Inject lateinit var sectionAdapter: SectionedRecyclerViewAdapter
    @Inject lateinit var apSection: AccessPointSection
    @Inject lateinit var mpSection: MappingPointSection
    @Inject lateinit var layoutManager: LinearLayoutManager
    @Inject lateinit var itemDecoration: DividerItemDecoration

    private val binding by binding<ActivityProjectDetailBinding>(R.layout.activity_project_detail)
    private lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //Right now we only deal with one account. So simply use findFirst()
        account = realm.where(Account::class.java).findFirst()!!
        init_UI()
    }

    private fun init_UI(){
        setCounts()
        val accessPointList = ArrayList<AccessPoint>()
        accessPointList.addAll(realm.copyFromRealm(account.mAccessPoints))
        val mappingPointList = ArrayList<MappingPoint>()
        mappingPointList.addAll(realm.copyFromRealm(account.mMappingPoints))
        apSection.setAccessPoints(accessPointList)
        mpSection.setMappingPoints(mappingPointList)

        sectionAdapter.addSection(apSection)
        sectionAdapter.addSection(mpSection)

        binding.apRv.addItemDecoration(itemDecoration)
        binding.apRv.layoutManager = layoutManager
        binding.apRv.adapter = sectionAdapter
    }

    private fun setCounts(){
        if (account.mAccessPoints.size != 0) binding.apCount.setText(account.mAccessPoints.size)
        if (account.mMappingPoints.size != 0) binding.mpCount.setText(account.mMappingPoints.size)
    }

    override fun onResume() {
        super.onResume()
        sectionAdapter.notifyDataSetChanged()
        setCounts()
    }

    override fun onPause() {
        super.onPause()
        binding.apRv.removeItemDecoration(itemDecoration)
        binding.apRv.layoutManager = null
        binding.apRv.adapter = null
    }
}