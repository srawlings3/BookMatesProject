<<<<<<< Updated upstream:app/src/main/java/graduateSchool/Spring2024/CapStone/GameListAdapter.kt
package graduateSchool.Spring2024.CapStone

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import graduateSchool.Spring2024.CapStone.databinding.ListItemGamesBinding
import java.util.UUID




class GameHolder(
    private val binding: ListItemGamesBinding
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var boundGame: Game
        private set


    fun bind(game: Game, onGameClicked: (gameId: UUID) -> Unit) {

        boundGame = game

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


        val playerNames = game.players.map {it.playerName }
        binding.listOfPlayers.text = "Players: ${playerNames.joinToString(", ") }"

        with(binding.listItemImage){
            when{
                game.finished -> {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.finished_game)
                }
                !game.finished ->{
                    visibility = View.GONE
                }
            }
        }
        //var tempString: String = "Players: "
        //binding.listOfPlayers.text = game.players.forEach {
        //    tempString += " ${it.playerName}"
        //}.toString()

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
    private var games: List<Game>,
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
        holder.bind(game, onGameClicked)
    }

    override fun getItemCount() :Int {
        return games.size
    }

    fun updateGames(gameList: List<Game>){
        games = gameList
        notifyDataSetChanged()

    }
=======
package com.example.myapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ListItemGamesBinding
import java.util.UUID




class GameHolder(
    private val binding: ListItemGamesBinding
) : RecyclerView.ViewHolder(binding.root) {

    lateinit var boundGame: Game
        private set


    fun bind(game: Game, onGameClicked: (gameId: UUID) -> Unit) {

        boundGame = game

        binding.root.setOnClickListener {
            onGameClicked(game.gameId)
        }



        binding.gameItemTitle.text = game.title



        val playerNames = game.players.map {it.playerName }
        binding.listOfPlayers.text = "Players: ${playerNames.joinToString(", ") }"

        with(binding.listItemImage){
            when{
                game.finished -> {
                    visibility = View.VISIBLE
                    setImageResource(R.drawable.finished_game)
                }
                !game.finished ->{
                    visibility = View.GONE
                }
            }
        }

    }


}




class GameListAdapter(
    private var games: List<Game>,
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

        holder.bind(game, onGameClicked)
    }

    override fun getItemCount() :Int {
        return games.size
    }

    fun updateGames(gameList: List<Game>){
        print("This is the updated Game list:")
        println(gameList.size)
        games = gameList
        notifyDataSetChanged()


    }
>>>>>>> Stashed changes:app/src/main/java/com/example/myapplication/GameListAdapter.kt
}