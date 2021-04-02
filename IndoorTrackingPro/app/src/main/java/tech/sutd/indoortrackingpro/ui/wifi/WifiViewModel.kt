package tech.sutd.indoortrackingpro.ui.wifi

import android.util.Log
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmList
import tech.sutd.indoortrackingpro.data.WifiWorker
import tech.sutd.indoortrackingpro.model.AP
import tech.sutd.indoortrackingpro.model.ListAP
import javax.inject.Inject

@HiltViewModel
class WifiViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val workManager: WorkManager,
    private val config: RealmConfiguration,
) : ViewModel() {

    private val TAG = "WifiViewModel"

    private var wifi: LiveData<RealmList<AP>> = MutableLiveData()

    private val workRequest by lazy {
        OneTimeWorkRequestBuilder<WifiWorker>()
                .addTag("wifiRequest")
                .build()
    }

    @Suppress("DEPRECATION")
    fun initWifiScan(fragment: Fragment) {
        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id)
                .observe(fragment.viewLifecycleOwner) { workInfo ->
                    if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                        Toast.makeText(fragment.context, "Data Retrieved", Toast.LENGTH_SHORT).show()
                    }
                }
    }

    fun observeWifiScan(): LiveData<RealmList<AP>> {
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                realm.executeTransaction { transactionRealm ->
                    val wifiResults = transactionRealm.where(ListAP::class.java).findFirst()
                    wifi = (wifiResults?.apList?.asFlowable()
                        ?.onBackpressureLatest()
                        ?.toLiveData() as LiveData<RealmList<AP>>?)!!
                    Log.d(TAG, "onSuccess: ${wifiResults?.apList?.get(0)?.mac}" )
                }
            }
        })
        return wifi
    }

}