package nl.hva.madlevel5task2.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import nl.hva.madlevel5task2.model.Game
import nl.hva.madlevel5task2.repository.GamesRepository

class GamesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GamesRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val games: LiveData<List<Game>> = repository.getGames()
    val error = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    fun insertGame(game: Game) {
        if (isGameValid(game)) {
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    repository.insertGame(game)
                }
                success.value = true
            }
        }
    }

    fun deleteGame(game: Game) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteGame(game)
            }
            success.value = true
        }
    }

    fun deleteAllGames() {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                repository.deleteAllGames()
            }
            success.value = true
        }
    }

    private fun isGameValid(game: Game): Boolean {
        return when {
            game.title.isBlank() -> {
                error.value = "Title must not be empty"
                false
            }
            game.platform.isBlank() -> {
                error.value = "Platform must not be empty"
                false
            }
            //TODO: Assert condition
            game.release == null -> {
                error.value = "Date must not be empty"
                false
            }
            else -> true
        }
    }
}