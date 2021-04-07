

package tech.sutd.indoortrackingpro.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import dagger.hilt.android.AndroidEntryPoint
import io.realm.Realm
import tech.sutd.indoortrackingpro.R
import tech.sutd.indoortrackingpro.adapter.MPReadingAdapter
import tech.sutd.indoortrackingpro.base.BaseActivity
import tech.sutd.indoortrackingpro.data.MPWorker
import tech.sutd.indoortrackingpro.model.Account
import tech.sutd.indoortrackingpro.model.MappingPoint
import javax.inject.Inject

@AndroidEntryPoint
class AddMappingPointB: BaseActivity() {
    @Inject
    lateinit var realm: Realm
    lateinit var saveButton: Button
    lateinit var xValue: EditText
    lateinit var yValue: EditText
    lateinit var recyclerView: RecyclerView
    var handler: Handler = Handler(Looper.myLooper()!!)
    lateinit var layoutManager: LinearLayoutManager
    lateinit var rvAdapter: MPReadingAdapter
    lateinit var mappingPoint: MappingPoint
    lateinit var workManager: WorkManager
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_mappingpoint)
        workManager = WorkManager.getInstance(this)
        if (realm.where(Account::class.java).findFirst()?.mAccessPoints!!.size == 0) {
            Toast.makeText(this, "Please add Access Points first!", Toast.LENGTH_LONG).show()
            this.finish()
        }
        initUI()
        startScan()
    }

    override fun onResume() {
        super.onResume()
        startScan()
    }

    fun initUI() {
        layoutManager = LinearLayoutManager(this)
        saveButton = findViewById(R.id.add_mp_save_button)
        xValue = findViewById(R.id.add_mp_x)
        yValue = findViewById(R.id.add_mp_y)

        recyclerView = findViewById(R.id.add_mp_rv)
        saveButton.isEnabled = false
        recyclerView.layoutManager = layoutManager
        saveButton.setOnClickListener {
            realm.beginTransaction()
            mappingPoint.x = xValue.text.toString().toDouble()
            mappingPoint.y = yValue.text.toString().toDouble()
            realm.where(Account::class.java).findFirst()!!.mMappingPoints.add(mappingPoint)
            realm.commitTransaction()
            this.finish()
        }
    }

    fun startScan() {
        val workRequest: WorkRequest =
                OneTimeWorkRequestBuilder<MPWorker>().build()
        workManager.enqueue(workRequest)
        rvAdapter = MPReadingAdapter(mappingPoint)
        recyclerView.adapter = rvAdapter
    }

}