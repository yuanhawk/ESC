package tech.sutd.indoortrackingpro.ui

import android.content.Intent
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
    val addAP_request_code = 1000
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
        //val accessPointList = ArrayList<AccessPoint>()
        //accessPointList.addAll(account.mAccessPoints)
        //val mappingPointList = ArrayList<MappingPoint>()
        //mappingPointList.addAll(account.mMappingPoints)
        apSection.setAccessPoints(account.mAccessPoints)
        mpSection.setMappingPoints(account.mMappingPoints)

        sectionAdapter.addSection(apSection)
        sectionAdapter.addSection(mpSection)
        binding.buttonAddMp.setOnClickListener {
            val intent = Intent(this@ProjectDetailActivity, AddMappingPointActivity::class.java)
            startActivity(intent)
        }

        binding.buttonAddAp.setOnClickListener {
            val intent = Intent(this@ProjectDetailActivity, WifiSearchActivity::class.java)
            startActivityForResult(intent, addAP_request_code)
        }
        binding.btnLocateMe.setOnClickListener {
            val intent = Intent(this@ProjectDetailActivity, TrackingActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setCounts(){
        if (account.mAccessPoints.size != 0) binding.apCount.text = account.mAccessPoints.size.toString()
        if (account.mMappingPoints.size != 0) binding.mpCount.text = account.mMappingPoints.size.toString()
    }

    override fun onResume() {
        super.onResume()
        //sectionAdapter.notifyDataSetChanged()
        setCounts()
        binding.apRv.addItemDecoration(itemDecoration)
        binding.apRv.layoutManager = layoutManager
        binding.apRv.adapter = sectionAdapter
    }

    override fun onPause() {
        super.onPause()
        binding.apRv.removeItemDecoration(itemDecoration)
        binding.apRv.layoutManager = null
        binding.apRv.adapter = null
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == addAP_request_code && resultCode == RESULT_OK){
            var accessPoint:AccessPoint = data!!.getParcelableExtra(WifiSearchActivity.returnKey)!!
            realm.beginTransaction()
            account.mAccessPoints.add(accessPoint)
            realm.commitTransaction()
            apSection.setAccessPoints(account.mAccessPoints)
            mpSection.setMappingPoints(account.mMappingPoints)
            sectionAdapter.notifyDataSetChanged()
        }
    }
}