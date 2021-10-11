package nl.hva.madlevel5task2.repository

import android.content.Context
import androidx.lifecycle.LiveData
import nl.hva.madlevel5task2.dao.GameDao
import nl.hva.madlevel5task2.db.GamesRoomDatabase
import nl.hva.madlevel5task2.model.Game

class GamesRepository(context: Context) {

    private val gameDao: GameDao

    init {
        val database = GamesRoomDatabase.getDatabase(context)
        gameDao = database!!.gameDao()
    }

    fun getGames(): LiveData<List<Game>> {
        return gameDao.getGames()
    }

    suspend fun insertGame(game: Game) {
        gameDao.insertGame(game)
    }

    suspend fun deleteGame(game: Game) {
        gameDao.deleteGame(game)
    }

    suspend fun deleteAllGames() {
        gameDao.deleteAllGames()
    }
}