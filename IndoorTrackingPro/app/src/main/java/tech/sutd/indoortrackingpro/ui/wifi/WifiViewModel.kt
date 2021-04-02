package tech.sutd.indoortrackingpro.ui.wifi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import dagger.hilt.android.lifecycle.HiltViewModel
import tech.sutd.indoortrackingpro.data.WifiWorker
import javax.inject.Inject

@HiltViewModel
class WifiViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val workManager: WorkManager,
    private val wifiWorker: WifiWorker
) : ViewModel() {

    fun initWifiScan() {
        workManager.enqueue(
            OneTimeWorkRequest.from(
                WifiWorker::class.java
            )
        )
    }

}