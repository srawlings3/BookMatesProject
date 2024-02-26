package graduateSchool.Spring2024.CapStone


import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Ignore
import androidx.room.PrimaryKey
import java.util.Date

import java.util.UUID
data class Game(
    @PrimaryKey val gameId: UUID = UUID.randomUUID(),
    val title: String,
    val players: List<Player>,
    val scores: MutableMap<Player, Int> = mutableMapOf()
){
    fun updateScore(player: Player, score: Int){
        scores[player] = score
    }
}




object Games{

    private val gamesList: MutableList<Game> = mutableListOf()


    fun createGames(template: Template, title: String,  players: List<Player>){
        val game = Game(template.templateId, title, players)
        players.forEach{
                player ->
            game.updateScore(player, 0)
        }
        gamesList.add(game)
    }


    fun getGames(): List<Game>{
        return gamesList
    }

}
