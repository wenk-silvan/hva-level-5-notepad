package nl.hva.madlevel5task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
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

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                viewModel.deleteGame(games[position])
            }
        }
        return ItemTouchHelper(callback)
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

}