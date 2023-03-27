package ale.ricardo.githubapp.viewmodel

import ale.ricardo.githubapp.views.paging.HomeSearchDataSource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn

class HomeViewModel : ViewModel() {

    fun queryRepositoryByKey(key: String = "Android") =
            Pager(config = PagingConfig(pageSize = 8, initialLoadSize = 8), pagingSourceFactory = { HomeSearchDataSource(key) }).flow.cachedIn(
                viewModelScope
            )
}


