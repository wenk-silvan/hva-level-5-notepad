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
import java.time.LocalDate
import java.util.*

class GamesViewModel(application: Application) : AndroidViewModel(application) {
    private val repository = GamesRepository(application.applicationContext)
    private val mainScope = CoroutineScope(Dispatchers.Main)

    val games: LiveData<List<Game>> = repository.getGames()
    val error = MutableLiveData<String>()
    val success = MutableLiveData<Boolean>()

    fun insertGame(title: String, platform: String, day: String, month: String, year: String) {
        if (isInputValid(title, platform, day, month, year)) {

            val c = Calendar.getInstance()
            c.set(
                Integer.parseInt(year),
                Integer.parseInt(month) - 1,
                Integer.parseInt(day), 0, 0
            )
            val game = Game(title, platform, c.time)
            mainScope.launch {
                withContext(Dispatchers.IO) {
                    repository.insertGame(game)
                }
                success.value = true
            }
        }
    }

    fun insertGame(game: Game) {
        mainScope.launch {
            withContext(Dispatchers.IO) {
                repository.insertGame(game)
            }
            success.value = true
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

    private fun isInputValid(
        title: String,
        platform: String,
        day: String,
        month: String,
        year: String
    ): Boolean {
        return when {
            title.isBlank() -> {
                error.value = "Title must not be empty"
                false
            }
            platform.isBlank() -> {
                error.value = "Platform must not be empty"
                false
            }
            day.length != 2 || month.length != 2 || year.length != 4 -> {
                error.value = "Invalid date input"
                false
            }
            else -> true
        }
    }
}