package tech.sutd.indoortrackingpro.ui.coordinates//package tech.sutd.indoortrackingpro.ui.fragments
//
//import androidx.paging.Pager
//import androidx.paging.PagingConfig
//import androidx.paging.PagingData
//import java.util.concurrent.Flow
//
//class CoordinatesViewHolder {
//    val coordinates: Flow<PagingData<>> = Pager(PagingConfig(pageSize = 20)) {
//        CoordinatesPagingSource() //api
//    }.flow.cachedIn(viewModelScope)
//}