package ale.ricardo.githubapp.viewmodel

import ale.ricardo.githubapp.common.TAG
import ale.ricardo.githubapp.model.CommitModel
import ale.ricardo.githubapp.views.paging.NotificationDataSource
import ale.ricardo.githubapp.views.paging.RepositoryDataSource
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch

class NotificationsViewModel : ViewModel() {


    private val notifications by lazy {
        Pager(
            config = PagingConfig(pageSize = 8, initialLoadSize = 8),
            pagingSourceFactory = { NotificationDataSource() }).flow.cachedIn(viewModelScope).catch {
            Log.d(TAG, "git notification error: $it")
        }
    }

    fun fetchNotification(): Flow<PagingData<CommitModel>> = notifications

}