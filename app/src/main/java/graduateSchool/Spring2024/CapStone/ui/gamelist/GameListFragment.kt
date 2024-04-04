package graduateSchool.Spring2024.CapStone.ui.gamelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import graduateSchool.Spring2024.CapStone.GameHolder
import graduateSchool.Spring2024.CapStone.GameListAdapter
import graduateSchool.Spring2024.CapStone.SharedViewModel
import graduateSchool.Spring2024.CapStone.databinding.FragmentGameListBinding
import kotlinx.coroutines.launch


private const val TAG = "GameListFragment"

class GameListFragment : Fragment() {


    private var _binding: FragmentGameListBinding? = null

    private val binding
        get() = checkNotNull(_binding){
            "cannot access"
        }
    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

    private val sharedViewModel: SharedViewModel by activityViewModels()

    private lateinit var gameListAdapter: GameListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val gameListViewModel =
            ViewModelProvider(this).get(GameListViewModel::class.java)

        _binding = FragmentGameListBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        gameListViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }




        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        getItemTouchHelper().attachToRecyclerView(binding.gameRecyclerView)

        binding.gameRecyclerView.layoutManager = LinearLayoutManager(context)

/*
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {

                sharedViewModel.games.collect { games ->
                    println(games.size)

                   // binding.gameRecyclerView.adapter =
                    val adapter = GameListAdapter(games) { gameId ->
                        print(games.size)
                        findNavController().navigate(
                            GameListFragmentDirections.showGameDetail(gameId)
                        )
                    }

                    binding.gameRecyclerView.adapter = adapter
                    adapter.notifyDataSetChanged()

                }
            }
        }

 */

        gameListAdapter = GameListAdapter(emptyList()) { gameId ->
            findNavController().navigate(
                GameListFragmentDirections.showGameDetail(gameId)
            )
        }
        binding.gameRecyclerView.adapter = gameListAdapter
        sharedViewModel.games.observe(viewLifecycleOwner){games ->

           gameListAdapter.updateGames(games)
        }

/*
                gameListAdapter = GameListAdapter(emptyList()) { gameId ->
                    findNavController().navigate(GameListFragmentDirections.showGameDetail(gameId))
                }
                binding.gameRecyclerView.apply{
                    layoutManager = LinearLayoutManager(context)
                    adapter = gameListAdapter
                }
                viewLifecycleOwner.lifecycleScope.launch{
                    viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){
                        sharedViewModel.games.collect{games ->
                            gameListAdapter.updateGames(games)
                        }
                    }
                }

                getItemTouchHelper().attachToRecyclerView(binding.gameRecyclerView)



 */


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun getItemTouchHelper(): ItemTouchHelper {
        return ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT){
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                Log.w("-----DLF-----" , "Swipe LEFT detected!!")
                val gameHolder = viewHolder as GameHolder
                sharedViewModel.deleteGame(gameHolder.boundGame)
            }

        })
    }


}

