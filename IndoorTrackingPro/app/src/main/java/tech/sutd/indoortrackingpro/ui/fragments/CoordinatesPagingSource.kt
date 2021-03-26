//package tech.sutd.indoortrackingpro.ui.fragments
//
//import androidx.paging.PagingSource
//import androidx.paging.PagingState
//import java.io.IOException
//import java.net.HttpRetryException
//
//class CoordinatesPagingSource (
//    private val query: String
//    // backend api??
//) : PagingSource<String, Item> () {
//
//    override suspend fun load(params: LoadParams<Int>): LoadResult<String, Item> {
//        return try {
//            //val coordinates = <api>.searchItems(params.key)
//            LoadResult.Page (
//                data = coordinates.items,
//                prevKey = coordinates.prev,
//                nextKey = coordinates.next
//                )
//
//        } catch (e: IOException) {
//            LoadResult.Error(e)
//        } catch (e: HttpRetryException) {
//            LoadResult.Error(e)
//        }
//    }
//
//    override fun getRefreshKey(state: PagingState<String, Item>): String? {
//        return state.anchorPosition?.let {
//            state.closestItemToPosition(it)?.id
//        }
//    }
//}