package ale.ricardo.githubapp.views.paging

import ale.ricardo.githubapp.model.CommitModel
import ale.ricardo.githubapp.network.NotificationService
import ale.ricardo.githubapp.network.RetrofitClient
import androidx.paging.PagingSource
import androidx.paging.PagingState

class NotificationDataSource: PagingSource<Int, CommitModel>() {
    override fun getRefreshKey(state: PagingState<Int, CommitModel>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, CommitModel> {
        val pageIndex = params.key ?: 1
        val pageSize = params.loadSize
        val commitList = RetrofitClient.createApi(NotificationService::class.java)
            .fetchAllCommit(pageIndex, pageSize)
        var preKey: Int? = null
        var nextKey: Int? = null

        val realPageSize = 8
        val initialLoadSize = 16
        if (pageIndex == 1) {
            preKey = null
            nextKey = initialLoadSize / realPageSize + 1
        } else {
            preKey = pageIndex - 1
            nextKey = if (commitList.isEmpty()) {
                null
            } else {
                pageIndex + 1
            }
        }

        return try {
            LoadResult.Page(
                data = commitList,
                prevKey = preKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}