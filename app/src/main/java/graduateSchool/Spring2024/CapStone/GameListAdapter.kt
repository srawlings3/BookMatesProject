package graduateSchool.Spring2024.CapStone

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import graduateSchool.Spring2024.CapStone.databinding.ListItemGamesBinding
import java.util.UUID




class GameHolder(
    private val binding: ListItemGamesBinding
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var boundDream: Game
        private set


    fun bind(game: Game, onGameClicked: (gameId: UUID) -> Unit) {

        boundDream = game

        binding.root.setOnClickListener {
            onGameClicked(game.gameId)
        }


        // binding.listItemTitle.text = dream.title
        // binding.listItemReflectionCount.text = dream.reflections.toString()
        binding.gameItemTitle.text = game.title
        // binding.listItem.text = game.title
        //binding.listOfPlayers.text =
        //   binding.root.context.getString(
        //     R.string.reflection_count,
        //    dream.entries.count { it.kind ==  DreamEntryKind.REFLECTION}
        // )

        var tempString: String = "Players: "
        binding.listOfPlayers.text = game.players.forEach {
            tempString += " ${it.playerName}"
        }.toString()

        /*

        with(binding.listItemImage){
            when{
                dream.isFulfilled -> {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.ic_dream_fulfilled)
                }
                dream.isDeferred -> {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.ic_dream_deferred)
                }
                else -> {
                    visibility = View.GONE
                }

            }
        }
    }

 */
    }


}




class GameListAdapter(
    private val games: List<Game>,
    private val onGameClicked: (UUID) -> Unit
) : RecyclerView.Adapter<GameHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GameHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ListItemGamesBinding.inflate(inflater, parent, false)
        return GameHolder(binding)
    }

    override fun onBindViewHolder(holder: GameHolder, position: Int) {
        val game = games[position]
        //holder.apply {
        //  binding.listItemTitle.text = dream.title
        // binding.listItemReflectionCount.text = "Reflections: 0"
        //}
        holder.bind(games[position], onGameClicked)
    }

    override fun getItemCount() :Int {
        return games.size
    }
}