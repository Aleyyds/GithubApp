package ale.ricardo.githubapp.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Date

@Entity(tableName = "search_history")
data class SearchHistoryEntity(
    @PrimaryKey(autoGenerate = true) val id: Long? = null,

    @ColumnInfo(name = "key") val key: String,
    @ColumnInfo(name = "create_time") val createTime: Long

)