package ale.ricardo.githubapp.views.paging

import ale.ricardo.githubapp.model.Repository
import ale.ricardo.githubapp.network.RetrofitClient
import ale.ricardo.githubapp.network.UserService
import androidx.paging.PagingSource
import androidx.paging.PagingState

class RepositoryDataSource : PagingSource<Int, Repository>() {
    override fun getRefreshKey(state: PagingState<Int, Repository>): Int? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Repository> {
        val pageIndex = params.key ?: 1
        val pageSize = params.loadSize
        val repositoryList = RetrofitClient.createApi(UserService::class.java)
            .fetchUserRepository(pageIndex, pageSize)
        var preKey: Int? = null
        var nextKey: Int? = null

        val realPageSize = 8
        val initialLoadSize = 16
        if (pageIndex == 1) {
            preKey = null
            nextKey = initialLoadSize / realPageSize + 1
        } else {
            preKey = pageIndex - 1
            nextKey = if (repositoryList.isEmpty()) {
                null
            } else {
                pageIndex + 1
            }
        }

        return try {
            LoadResult.Page(
                data = repositoryList,
                prevKey = preKey,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            e.printStackTrace()
            return LoadResult.Error(e)
        }
    }
}