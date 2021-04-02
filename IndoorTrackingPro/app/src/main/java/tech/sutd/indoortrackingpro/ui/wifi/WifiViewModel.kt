package tech.sutd.indoortrackingpro.ui.wifi

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class WifiViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
) : ViewModel() {

}