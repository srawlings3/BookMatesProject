package graduateSchool.Spring2024.CapStone

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow


class SharedViewModel: ViewModel() {



    val playerList = PlayerList
    val gameList = Games
    val templateList = TemplatesList


    init {
        gameList.createGames(templateList.getTemplates().get(0), "Chess template", listOf(playerList.getPlayers().get(0), playerList.getPlayers().get(1)))
        gameList.createGames(templateList.getTemplates().get(1),"Uno template",  listOf(playerList.getPlayers().get(2), playerList.getPlayers().get(3)))


    }

}