package nl.hva.madlevel5task2.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import nl.hva.madlevel5task2.model.Game

@Dao
interface GameDao {
    @Insert
    suspend fun insertGame(game: Game)

    @Query("SELECT * FROM GameTable")
    fun getGames(): LiveData<Game?>

    @Delete
    suspend fun deleteGame(game: Game)

    @Query("DELETE FROM GameTable")
    fun deleteAllGames()
}