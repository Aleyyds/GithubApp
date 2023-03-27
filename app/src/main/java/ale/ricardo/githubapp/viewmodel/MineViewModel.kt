package ale.ricardo.githubapp.viewmodel

import ale.ricardo.githubapp.common.TAG
import ale.ricardo.githubapp.model.Repository
import ale.ricardo.githubapp.model.UserModel
import ale.ricardo.githubapp.network.RetrofitClient
import ale.ricardo.githubapp.network.UserService
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*

class MineViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "This is dashboard Fragment"
    }
    val text: LiveData<String> = _text

    private val userService: UserService by lazy {
        RetrofitClient.createApi(UserService::class.java)
    }


    private val _userInfo by lazy {
        flow {
            var userInfo =
                userService.fetchUserInfo()
            emit(userInfo)
        }.flowOn(Dispatchers.IO).catch {
            Log.d(TAG, "errors: $it")
            it.printStackTrace()
        }
    }


    private val repositories by lazy {
        Pager(
            config = PagingConfig(pageSize = 8, initialLoadSize = 8),
            pagingSourceFactory = { RepositoryDataSource() }
        ).flow.cachedIn(viewModelScope).catch {
            Log.d(TAG, "repositories error: $it")
        }
    }

    fun loadUserRepository():Flow<PagingData<Repository>> = repositories

    fun fetchUserMetaData(): Flow<UserModel> = _userInfo
}