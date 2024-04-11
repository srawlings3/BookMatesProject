package graduateSchool.Spring2024.CapStone

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import graduateSchool.Spring2024.CapStone.databinding.FragmentGameBinding
import graduateSchool.Spring2024.CapStone.databinding.FragmentNewGameBinding
import kotlinx.coroutines.launch
import java.util.UUID

class NewGameFragment: Fragment() {

    private lateinit var sharedViewModel: SharedViewModel
    private var selectedTemplateId: UUID? = null
    lateinit var availablePlayers: List<String>

    private var _binding: FragmentNewGameBinding? = null

    private val binding
        get() = checkNotNull(_binding){
            "Cannot access"
        }




    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreate(savedInstanceState)


        _binding=FragmentNewGameBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentNewGameBinding.bind(view)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        availablePlayers = sharedViewModel.playerList.getPlayerNames()

        var templateTitles = sharedViewModel.templateList.getTemplateNames()
        var adapter = ArrayAdapter(requireContext(), android.R.layout.simple_dropdown_item_1line, templateTitles)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.templateList.setAdapter(adapter)
        binding.templateList.threshold = 0

        sharedViewModel.templates.observe(viewLifecycleOwner) { templates ->
            templateTitles = templates.map { it.gameName }
            adapter = ArrayAdapter(
                requireContext(),
                android.R.layout.simple_dropdown_item_1line,
                templateTitles
            )
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            binding.templateList.setAdapter(adapter)
        }


        binding.templateList.onItemClickListener = AdapterView.OnItemClickListener { parent, _, position, _ ->
            val selectedTemplate = parent.getItemAtPosition(position) as String
            //val template = sharedViewModel.templateList.getTemplates().find { it.gameName == selectedTemplate }
            val template = sharedViewModel.templates.value?.find { it.gameName == selectedTemplate }
            selectedTemplateId = template?.templateId

            println(selectedTemplateId)
            if (template != null) {
                println(template.gameName)
            }else{
                println("Template is null")
            }
        }

        binding.addButton.setOnClickListener{
            addPlayerSlot()
        }
        binding.removeButton.setOnClickListener{
            removePlayerSlot()
        }

        binding.createNewGame.setOnClickListener{
            createGame()
        }
    }


    override fun onDestroyView(){
        super.onDestroyView()


    }

    private val selectedPlayersList = mutableListOf<Player>()

    fun addPlayerSlot(){
        val playerSlot = layoutInflater.inflate(R.layout.new_player_entry, null)

        binding.addPlayersLayout.addView(playerSlot)
        binding.removeButton.visibility = View.VISIBLE

        val playerSpinner = playerSlot.findViewById<Spinner>(R.id.new_player_spinner)
        val adapter = ArrayAdapter<String>(requireContext(), android.R.layout.simple_spinner_item, availablePlayers)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        playerSpinner.adapter = adapter

        playerSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedPlayer = availablePlayers[position]

                sharedViewModel.playerList.getPlayer(selectedPlayer)
                    ?.let { selectedPlayersList.add(it) }
                //updateOtherSpinners(playerSpinner, selectedPlayer)
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

    }

    fun removePlayerSlot(){
        if(binding.addPlayersLayout.childCount > 0){

            binding.addPlayersLayout.removeViewAt(binding.addPlayersLayout.childCount-1)

        }else{
           // binding.removeButton.visibility = View.GONE
           // binding.addPlayersLayout.visibility = View.VISIBLE
        }
    }

    private fun createGame(){
        val gameName = binding.newGameTitle.text.toString()
        val players = selectedPlayersList
        var scores: MutableMap<Player, MutableList<Int>> = mutableMapOf()
        for(i in players){
            scores[i] = mutableListOf(0)
        }



        if (selectedTemplateId != null) {
            // Create your Game object with the selected template ID
            val newGame = Game(selectedTemplateId!!, gameName,players, scores)
            val gameId = sharedViewModel.addNewGame(newGame)

            selectedPlayersList.clear()

            val action = GameFragmentDirections.actionGameFragment(gameId)
            val message = "New Game added: $gameName"
            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
            findNavController().navigate(action)
        } else {


            println("Can't find id")
            // Handle the case where no template has been selected
            // Show an error message or take appropriate action
        }

        /*
        val newGame = Game(gameName, players,scores)

            val gameId = sharedViewModel.addNewGame(newGame)

            selectedPlayersList.clear()
            ///findNavController().navigate(R.id.action_GameFragment)


            //val action = GameFragmentDirections.actionGameList()
            val action = GameFragmentDirections.actionGameFragment(gameId)

            findNavController().navigate(action)
            //findNavController().navigateUp()



         */
    }

/*


    private fun updateOtherSpinners(currentSpinner: Spinner, selectedPlayer: String) {
        for (i in 0 until binding.addPlayersLayout.childCount) {
            val playerSlot = binding.addPlayersLayout.getChildAt(i)
            val otherSpinner = playerSlot.findViewById<Spinner>(R.id.new_player_spinner)

            if (otherSpinner != currentSpinner && otherSpinner != null) { // Exclude the current spinner
                val adapter = otherSpinner.adapter as ArrayAdapter<String>

                if (selectedPlayer.isNotEmpty()) {
                    // If a player is selected, remove it from other spinners
                    adapter.remove(selectedPlayer)
                } else {
                    // If no player is selected, add back the deselected player to other spinners
                    val deselectedPlayer = selectedPlayersMap[otherSpinner]
                    if (deselectedPlayer != null) {
                        adapter.add(deselectedPlayer)
                        adapter.sort { o1, o2 -> o1.compareTo(o2) }
                    }
                }
                adapter.notifyDataSetChanged()
            }
        }
    }


 */
}