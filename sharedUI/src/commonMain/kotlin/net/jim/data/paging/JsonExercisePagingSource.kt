package net.jim.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import net.jim.data.models.JsonExerciseType
import net.jim.data.table.JsonExerciseTable

class JsonExercisePagingSource(
    val searchQuery: String
) : PagingSource<Int, JsonExerciseType>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, JsonExerciseType> {
        return try {
            val page = params.key ?: 0
            val offset = page * params.loadSize

            val result = JsonExerciseTable.searchByNamePaged(
                name = searchQuery,
                pageSize = params.loadSize,
                offset = offset
            )

            LoadResult.Page(
                data = result,
                prevKey = if (page == 0) null else page - 1,
                nextKey = if (result.isEmpty()) null else page + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, JsonExerciseType>): Int? {
        return state.anchorPosition?.let { anchor ->
            state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
        }
    }
}