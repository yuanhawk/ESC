package tech.sutd.indoortrackingpro.ui

import android.Manifest
import tech.sutd.indoortrackingpro.model.Account
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import androidx.core.content.ContextCompat
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import io.github.luizgrp.sectionedrecyclerviewadapter.SectionParameters

import io.github.luizgrp.sectionedrecyclerviewadapter.SectionedRecyclerViewAdapter
import io.realm.Realm
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.adapter.viewHolder.AccessPointSection
import tech.sutd.indoortrackingpro.adapter.viewHolder.MappingPointSection
import tech.sutd.indoortrackingpro.model.AccessPoint
import tech.sutd.indoortrackingpro.model.MappingPoint
import tech.sutd.indoortrackingpro.utils.Constants



class ProjectDetail: AppCompatActivity(){
    private lateinit var addApButton: Button
    private lateinit var addMpButton: Button
    private lateinit var recyclerView: RecyclerView
    private lateinit var apCounts: TextView
    private lateinit var mpCounts: TextView
    private lateinit var account: Account
    private lateinit var realm: Realm
    private lateinit var apSection: AccessPointSection
    private lateinit var mpSection: MappingPointSection
    private lateinit var sectionAdapter: SectionedRecyclerViewAdapter
    private lateinit var layoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_projectdetail)
        realm = Realm.getDefaultInstance()
        //Right now we only deal with one account. So simply use findFirst()
        account = realm.where(Account::class.java).findFirst()!!
        init_UI()
    }

    private fun init_UI(){
        addApButton = findViewById(R.id.button_add_ap)
        addMpButton = findViewById(R.id.button_add_mp)
        recyclerView = findViewById(R.id.projectDetail_recyclerView)
        apCounts = findViewById(R.id.projectdetail_ap_count)
        mpCounts = findViewById(R.id.projectdetail_mp_count)
        setCounts()
        sectionAdapter = SectionedRecyclerViewAdapter()
        var sectionParameters: SectionParameters = SectionParameters.builder().headerResourceId(R.layout.item_point_header).itemResourceId(
            R.layout.item_points
        ).build()
        apSection = AccessPointSection(sectionParameters)
        mpSection = MappingPointSection(sectionParameters)
        val accessPointList = ArrayList<AccessPoint>()
        accessPointList.addAll(realm.copyFromRealm(account.mAccessPoints))
        val mappingPointList = ArrayList<MappingPoint>()
        mappingPointList.addAll(realm.copyFromRealm(account.mMappingPoints))
        apSection.setAccessPoints(accessPointList)
        mpSection.setMappingPoints(mappingPointList)
        sectionAdapter.addSection(apSection)
        sectionAdapter.addSection(mpSection)
        layoutManager = LinearLayoutManager(this)
        recyclerView.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        recyclerView.layoutManager = layoutManager
        recyclerView.adapter = sectionAdapter
    }

    private fun setCounts(){
        if (account.mAccessPoints.size != 0) apCounts.setText(account.mAccessPoints.size)
        if (account.mMappingPoints.size != 0) mpCounts.setText(account.mMappingPoints.size)
    }

    override fun onResume() {
        super.onResume()
        sectionAdapter.notifyDataSetChanged()
        setCounts()
    }
}