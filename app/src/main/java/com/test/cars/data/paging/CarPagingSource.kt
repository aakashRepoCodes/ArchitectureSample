package com.test.cars.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.test.cars.data.common.Constants
import com.test.cars.data.local.db.CarDatabase
import com.test.cars.data.model.ManufacturerDTO.Companion.toList
import com.test.cars.data.remote.CarsAPI
import retrofit2.HttpException
import java.io.IOException

class CarPagingSource(
    private val carsAPI: CarsAPI,
    private val database: CarDatabase
) : PagingSource<Int, Pair<String, String>>() {

    override fun getRefreshKey(state: PagingState<Int, Pair<String, String>>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Pair<String, String>> {
        return try {
            val position: Int = params.key ?: FIRST_PAGE_INDEX
            val response = carsAPI.getManufacturersList(Constants.API_KEY, position, params.loadSize)

            val nextPageNumber: Int? = if (response.list.isNullOrEmpty()) null else position + 1
            val prevPageNumber: Int? = if (position == FIRST_PAGE_INDEX) null else position - 1
            database.getArticleDao().upsertManufacturer(response.toList())
            LoadResult.Page(
                data = response.list.toList(),
                prevKey = prevPageNumber,
                nextKey = nextPageNumber
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        }
        catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }

    companion object {
        private const val FIRST_PAGE_INDEX = 0
        private const val PAGE_SIZE = 15
    }

}