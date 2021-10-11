package nl.hva.madlevel5task2.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity(tableName = "gameTable")
data class Game(
    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "platform")
    val platform: String,

    @ColumnInfo(name = "release")
    val release: Date,

    @ColumnInfo(name = "id")
    @PrimaryKey(autoGenerate = true)
    var id: Long? = null
)