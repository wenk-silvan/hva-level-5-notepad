package nl.hva.madlevel5task2.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import nl.hva.madlevel5task2.databinding.FragmentAddGameBinding
import nl.hva.madlevel5task2.model.Game
import nl.hva.madlevel5task2.viewmodel.GamesViewModel
import java.time.LocalDate
import java.util.*

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class AddGameFragment : Fragment() {

    private var _binding: FragmentAddGameBinding? = null
    private val binding get() = _binding!!
    private val viewModel: GamesViewModel by viewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAddGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeMessages()
        binding.fabSaveGame.setOnClickListener {
            saveGame()
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun observeMessages() {
        viewModel.error.observe(viewLifecycleOwner, { message ->
            Snackbar.make(binding.root, message, Snackbar.LENGTH_SHORT).show()
        })

        viewModel.success.observe(viewLifecycleOwner, {
            findNavController().popBackStack()
        })
    }

    private fun saveGame() {
        viewModel.insertGame(
            binding.tilGame.editText?.text.toString(),
            binding.tilPlatform.editText?.text.toString(),
            binding.tilDay.editText?.text.toString(),
            binding.tilDay.editText?.text.toString(),
            binding.tilDay.editText?.text.toString()
        )
    }
}