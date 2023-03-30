package ale.ricardo.githubapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters

@Database(entities = [SearchHistoryEntity::class], version = 1, exportSchema = false)
abstract class HistoryDataBase : RoomDatabase() {

     abstract fun historyDao(): SearchHistoryDao

    companion object {

        private var historyDataBase: HistoryDataBase? = null

        fun getHistoryDataBaseInstance(context: Context): HistoryDataBase {
            return historyDataBase ?: synchronized(this) {
                Room.databaseBuilder(
                    context = context,
                    HistoryDataBase::class.java,
                    "search_history"
                ).build().also {
                    historyDataBase = it
                }
            }
        }


    }

}