package com.example.myapplication.ui.gamelist

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
<<<<<<< Updated upstream:app/src/main/java/graduateSchool/Spring2024/CapStone/ui/gamelist/GameListFragment.kt
=======
import com.example.myapplication.Game
>>>>>>> Stashed changes:app/src/main/java/com/example/myapplication/ui/gamelist/GameListFragment.kt

import com.example.myapplication.GameHolder
import com.example.myapplication.GameListAdapter
import com.example.myapplication.SharedViewModel
import com.example.myapplication.databinding.FragmentGameListBinding


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
<<<<<<< Updated upstream:app/src/main/java/graduateSchool/Spring2024/CapStone/ui/gamelist/GameListFragment.kt

           gameListAdapter.updateGames(games)
=======
            // println(games.size)
            //for (game in games) {
            //  print(sharedViewModel.templateList.getTemplates().find{it.templateId == game.templateId})
            //println(game.title)
            // }

            if(games.size == 0 ){
                binding.textDashboard.visibility = View.VISIBLE
            }else{
                binding.textDashboard.visibility = View.GONE
            }



            // gameListAdapter.updateGames(games)
            val filteredGames = filterGames(
                binding.FilterText.text.toString(),
                binding.FinishGameFilter.isChecked,

                games
            )
            gameListAdapter.updateGames(filteredGames)

            binding.gameRecyclerView.invalidate()
>>>>>>> Stashed changes:app/src/main/java/com/example/myapplication/ui/gamelist/GameListFragment.kt
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


<<<<<<< Updated upstream:app/src/main/java/graduateSchool/Spring2024/CapStone/ui/gamelist/GameListFragment.kt
=======
    private fun filterGames(
        searchText: String,
        finishedOnly: Boolean,
        games: List<Game>
    ): List<Game> {
        return games.filter { game ->
            val titleMatches = game.title.contains(searchText, ignoreCase = true)
            val statusMatches = !finishedOnly || game.finished
            titleMatches && statusMatches
        }

    }


>>>>>>> Stashed changes:app/src/main/java/com/example/myapplication/ui/gamelist/GameListFragment.kt
}
