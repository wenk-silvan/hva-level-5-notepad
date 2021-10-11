package nl.hva.madlevel5task2.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import nl.hva.madlevel5task2.R
import nl.hva.madlevel5task2.databinding.ItemGamesBinding
import nl.hva.madlevel5task2.model.Game
import java.text.SimpleDateFormat
import java.time.LocalDateTime

class GamesAdapter(private val games: List<Game>) :
    RecyclerView.Adapter<GamesAdapter.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val binding = ItemGamesBinding.bind(itemView)

        fun databind(game: Game) {
            binding.tvTitle.text = game.title
            binding.tvPlatform.text = game.platform
            val formattedDate = SimpleDateFormat("dd MM yyyy").format(game.release)
            binding.tvRelease.text = "Release: $formattedDate"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_games, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.databind(games[position])
    }

    override fun getItemCount(): Int {
        return games.size
    }
}
