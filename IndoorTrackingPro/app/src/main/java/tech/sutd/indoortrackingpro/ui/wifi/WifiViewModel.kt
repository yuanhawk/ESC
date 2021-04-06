package tech.sutd.indoortrackingpro.ui.wifi

import android.widget.Toast
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

    fun initWifiScan(fragment: WifiListFragment) {
        workManager.enqueue(workRequest)
        workManager.getWorkInfoByIdLiveData(workRequest.id)
                .observe(fragment.viewLifecycleOwner, Observer { workInfo ->
                    if (workInfo.state == WorkInfo.State.SUCCEEDED) {
                        Toast.makeText(fragment.context, "Data Retrieved, please refresh", Toast.LENGTH_SHORT)
                            .show()
                        fragment.callObserver()
                        workManager.cancelAllWork()
                    }
                })
    }

    fun observeWifiScan(): LiveData<RealmList<AP>> {
        Realm.getInstanceAsync(config, object : Realm.Callback() {
            override fun onSuccess(realm: Realm) {
                realm.executeTransaction { transactionRealm ->
                    val wifiResults = transactionRealm.where(ListAP::class.java).findFirst()
                    wifi = wifiResults?.apList?.asFlowable()
                        ?.onBackpressureBuffer()
                        ?.toLiveData()!!
//                    Log.d(TAG, "onSuccess: ${wifiResults?.apList?.get(0)?.mac}" )
                }
            }
        })

        return wifi
    }

}