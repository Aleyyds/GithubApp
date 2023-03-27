package ale.ricardo.githubapp.viewmodel

import ale.ricardo.githubapp.database.HistoryDataBase
import ale.ricardo.githubapp.database.SearchHistoryEntity
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flowOn

class HistoryViewModel(application: Application): AndroidViewModel(application) {

   private val historyDataBase by lazy {
        HistoryDataBase.getHistoryDataBaseInstance(application)
    }

    fun getAllSearchHistory(): Flow<List<SearchHistoryEntity>> {
        return historyDataBase.historyDao().fetchAllSearchHistory().catch {
            it.printStackTrace()
        }.flowOn(Dispatchers.IO)
    }

    suspend fun insertHistory(key: String) {
        historyDataBase.historyDao().insertSearchHistory(SearchHistoryEntity(null, key, System.currentTimeMillis()))
    }

    suspend fun deleteAllSearchHistory(){
        historyDataBase.historyDao().deleteAllSearchHistory()
    }



}