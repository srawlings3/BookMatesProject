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
import androidx.recyclerview.widget.LinearLayoutManager
import graduateSchool.Spring2024.CapStone.GameListAdapter
import graduateSchool.Spring2024.CapStone.SharedViewModel
import graduateSchool.Spring2024.CapStone.databinding.FragmentGameslistBinding
import graduateSchool.Spring2024.CapStone.ui.leaderboard.LeaderboardFragment
import kotlinx.coroutines.launch

private const val TAG = "GameListFragment"
class GameListFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var _binding: FragmentGameslistBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val GameListViewModel =
            ViewModelProvider(this).get(GameListViewModel::class.java)

        _binding = FragmentGameslistBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textDashboard
        GameListViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }
        return root
    }




    override fun onViewCreated(view: View, savedInstanceState: Bundle?){
        super.onViewCreated(view, savedInstanceState)

        // getItemTouchHelper().attachToRecyclerView(binding.dreamRecyclerView)


//        binding.dreamRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.gameRecyclerView.layoutManager = LinearLayoutManager(context)

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED){


                    binding.gameRecyclerView.adapter = GameListAdapter(sharedViewModel.gameList.getGames()){ GameId ->
                       // findNavController().navigate(
                            //LeaderboardFragmentDirections.showGameDetails(GameId)

                           //GameListFragmentDirections.showGameDetail(GameId)
                         //   GameListFragment.showGameDetails(GameId)
                       // )


                    }


            }
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}