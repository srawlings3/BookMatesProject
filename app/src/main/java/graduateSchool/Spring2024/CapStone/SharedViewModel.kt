package graduateSchool.Spring2024.CapStone

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.plus
import java.util.UUID


class SharedViewModel: ViewModel() {



    var playerList = PlayerList
    var gameList = Games
    var templateList = TemplatesList


    private val _games = MutableLiveData<List<Game>>()
    val games: LiveData<List<Game>> get() = _games

    private val _templates = MutableLiveData<List<Template>>()
    val templates: LiveData<List<Template>> get() = _templates


    init {
        viewModelScope.launch {
            gameList.createGames(
                templateList.getTemplates().get(0),
                "Chess Game1",
                listOf(playerList.getPlayers().get(0), playerList.getPlayers().get(1)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(1),
                "Uno Game1",
                listOf(playerList.getPlayers().get(2), playerList.getPlayers().get(3)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(2),
                "Spades Game1",
                listOf(playerList.getPlayers().get(1), playerList.getPlayers().get(3)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(1),
                "Uno Game2",
                listOf(playerList.getPlayers().get(0), playerList.getPlayers().get(2)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(0),
                "Chess Game2",
                listOf(playerList.getPlayers().get(0), playerList.getPlayers().get(3)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(2),
                "Spades Game2",
                listOf(playerList.getPlayers().get(1), playerList.getPlayers().get(2)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(2),
                "Spades Game3",
                listOf(
                    playerList.getPlayers().get(2),
                    playerList.getPlayers().get(3),
                    playerList.getPlayers().get(0)
                ),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(1),
                "Uno Game3",
                listOf(
                    playerList.getPlayers().get(2),
                    playerList.getPlayers().get(3),
                    playerList.getPlayers().get(0),
                    playerList.getPlayers().get(1)
                ),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(1),
                "Uno Game4",
                listOf(playerList.getPlayers().get(2), playerList.getPlayers().get(3)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(1),
                "Uno Game5",
                listOf(playerList.getPlayers().get(2), playerList.getPlayers().get(3)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(0),
                "Chess Game3",
                listOf(playerList.getPlayers().get(2), playerList.getPlayers().get(3)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(0),
                "Chess Game4",
                listOf(playerList.getPlayers().get(2), playerList.getPlayers().get(3)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(2),
                "Spades Game4",
                listOf(playerList.getPlayers().get(2), playerList.getPlayers().get(3)),
                mutableMapOf()
            )
            gameList.createGames(
                templateList.getTemplates().get(2),
                "Spades Game5",
                listOf(playerList.getPlayers().get(2), playerList.getPlayers().get(3)),
                mutableMapOf()
            )



            gameList.getGames()[0].updateFinished()
            gameList.getGames()[1].updateFinished()
            gameList.getGames()[6].updateFinished()
            gameList.getGames()[4].updateFinished()


            for(game in gameList.getGames()){
                val numRows = templateList.getTemplates().find { it.templateId == game.templateId }?.rowTitles?.size
                for(player in game.players){
                    val playerScores = MutableList(numRows ?: 0) { 0 }
                    game.scores[player] = playerScores
                }
            }


            _games.value = gameList.getGames()
            _templates.value = templateList.getTemplates()
        }
    }



    fun deleteGame(game: Game){
        viewModelScope.launch {
            val currentGame = _games.value.orEmpty().toMutableList()
            currentGame.remove(game)
            _games.value = currentGame.toList()
           // gameList.deleteGame(game)
        }
    }

    fun addNewGame(game: Game): UUID {
            viewModelScope.launch {
                val currentGame = _games.value.orEmpty().toMutableList()
                currentGame.add(game)
                _games.value = currentGame.toList()


                // viewModelScope.launch {
                //     Games.addGame(game)
                //    gameList = Games

                //  _games.value = gameList.getGames()
                //}
            }
         return game.gameId

    }
    fun addNewTemplate(template: Template) {
        // Get the current list of templates
        val currentTemplates = _templates.value.orEmpty().toMutableList()
        // Add the new template to the list
        currentTemplates.add(template)
        // Update the live data with the new list of templates
        _templates.value = currentTemplates.toList()
    }

    fun getNumberOfTemplates(): Int {
        return _templates.value?.size ?: 0
    }



    fun updateGameScores(updatedGame: Game, player: Player, updatedScores: MutableList<Int>) {
        val currentGames = games.value ?: return

        val updatedGameList = currentGames.map { game ->
            if (game == updatedGame) {
                // Update the scores of the specific game
                val updatedScoresMap = game.scores.toMutableMap()
                val currentScores = updatedScoresMap[player] ?: mutableListOf()
               // if (currentScores.isNotEmpty()) {
                    // Append the updated scores to the existing list
                 //   currentScores.addAll(updatedScores)
               // } else {
                 //   // If no existing scores, create a new list with the updated scores
                   // updatedScoresMap[player] = updatedScores.toMutableList()
               // }
                game.copy(scores = updatedScoresMap)
            } else {
                game
            }
        }
        _games.value = updatedGameList
    }


    /*
        fun updateGameScores(updatedGame: Game, player: Player, updatedScores: MutableList<Int>) {
            val currentGames = games.value ?: return

            val updatedGameList = currentGames.map { game ->
                if (game == updatedGame) {
                    // Update the scores of the specific player in the game
                    val updatedScoresMap = game.scores.toMutableMap()
                    updatedScoresMap[player] = updatedScores
                    val updatedGame = game.copy(scores = updatedScoresMap)
                    updatedGame
                } else {
                    game
                }
            }

            _games.value = updatedGameList
        }


     */
    /*
        fun updateGameScores(updatedGame: Game, player: Player, updatedScores: MutableList<Int>) {
            val currentGames = games.value ?: return

            val scoreMap = MutableListOf<Player, MutableList<Int>>
            val updatedGameList = currentGames.map { game ->
                if (game == updatedGame) {
                    // Update the scores of the specific game
                    val upGame = game.copy(scores = updatedScores.toMutableMap())
                    upGame
                } else {
                    game
                }
            }
            _games.value = updatedGameList
        }

     */

}


