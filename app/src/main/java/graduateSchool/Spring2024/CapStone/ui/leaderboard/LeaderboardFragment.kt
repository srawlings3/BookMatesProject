package graduateSchool.Spring2024.CapStone.ui.leaderboard

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
import graduateSchool.Spring2024.CapStone.SharedViewModel
import graduateSchool.Spring2024.CapStone.databinding.FragmentLeaderboardBinding
import kotlinx.coroutines.launch
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class LeaderboardFragment : Fragment() {

    private var _binding: FragmentLeaderboardBinding? = null

    private val sharedViewModel: SharedViewModel by activityViewModels()

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val leaderboardViewModel =
            ViewModelProvider(this).get(LeaderboardViewModel::class.java)

        _binding = FragmentLeaderboardBinding.inflate(inflater, container, false)
        val root: View = binding.root

       // val textView: TextView = binding.textLeaderboard
       // leaderboardViewModel.text.observe(viewLifecycleOwner) {
        //    textView.text = it
       // }

        val sortedPlayers = sharedViewModel.playerList.getPlayers().sortedByDescending { it.wins }
        binding.firstPlace.text = "1: ${sortedPlayers[0].playerName}"
        binding.secondPlace.text = "2:${sortedPlayers[1].playerName}"
        binding.thirdPlace.text = "3:${sortedPlayers[2].playerName}"

        return root



    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




}