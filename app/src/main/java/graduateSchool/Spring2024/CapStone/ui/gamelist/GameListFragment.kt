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



        gameListAdapter = GameListAdapter(emptyList()) { gameId ->
            findNavController().navigate(
                GameListFragmentDirections.showGameDetail(gameId)
            )
        }
        binding.gameRecyclerView.adapter = gameListAdapter
        print("Shared View model list size: ")

        sharedViewModel.games.observe(viewLifecycleOwner){games ->
            println(games.size)
            for (game in games) {
                print(sharedViewModel.templateList.getTemplates().find{it.templateId == game.templateId})
                println(game.title)
            }

            if(games.size == 0 ){
                binding.textDashboard.visibility = View.VISIBLE
            }else{
                binding.textDashboard.visibility = View.GONE
            }
           gameListAdapter.updateGames(games)
            binding.gameRecyclerView.invalidate()
        }



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

