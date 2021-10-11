package nl.hva.madlevel5task2.ui

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import nl.hva.madlevel5task2.R
import nl.hva.madlevel5task2.adapter.GamesAdapter
import nl.hva.madlevel5task2.databinding.FragmentGamesBinding
import nl.hva.madlevel5task2.model.Game
import nl.hva.madlevel5task2.viewmodel.GamesViewModel

class GamesFragment : Fragment() {

    private var _binding: FragmentGamesBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GamesViewModel by viewModels()
    private val games: ArrayList<Game> = arrayListOf()
    private val gamesAdapter = GamesAdapter(games)


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentGamesBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        initRecyclerView()
        observeAddNoteResult()

        binding.fabAddGame.setOnClickListener {
            findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun createItemTouchHelper(): ItemTouchHelper {
        val callback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) =
                deleteOrUndo(viewHolder.adapterPosition)
        }
        return ItemTouchHelper(callback)
    }

    private fun deleteOrUndo(position: Int) {
        val gameToDelete = games[position]
        viewModel.deleteGame(gameToDelete)
        gamesAdapter.notifyItemRemoved(position)
        Snackbar.make(
            binding.rvGames, getString(R.string.snackbarOnDeleteGame), Snackbar.LENGTH_LONG
        ).setAction("Undo") {
            viewModel.insertGame(gameToDelete)
            gamesAdapter.notifyItemInserted(position)
        }.show()
    }

    private fun initRecyclerView() {
        binding.rvGames.layoutManager =
            LinearLayoutManager(context, RecyclerView.VERTICAL, false)
        binding.rvGames.adapter = gamesAdapter
        createItemTouchHelper().attachToRecyclerView(binding.rvGames)
    }

    private fun observeAddNoteResult() {
        viewModel.games.observe(viewLifecycleOwner, { list ->
            list?.let {
                games.clear()
                games.addAll(it)
                gamesAdapter.notifyDataSetChanged()
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_actions, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_action_delete -> {
                val games = viewModel.games.value!!
                viewModel.deleteAllGames()
                gamesAdapter.notifyDataSetChanged()
                Snackbar.make(
                    binding.rvGames, getString(R.string.snackbarOnDeleteGames), Snackbar.LENGTH_LONG
                ).setAction("Undo") {
                    games.forEach {
                        viewModel.insertGame(it)
                    }
                    gamesAdapter.notifyDataSetChanged()
                }.show()
                true
            }
            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}
