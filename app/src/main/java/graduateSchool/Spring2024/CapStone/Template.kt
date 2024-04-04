package graduateSchool.Spring2024.CapStone

import android.os.Bundle
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
import androidx.room.PrimaryKey
import graduateSchool.Spring2024.CapStone.databinding.FragmentGameListBinding
import graduateSchool.Spring2024.CapStone.ui.gamelist.GameListViewModel
import kotlinx.coroutines.launch
import java.util.UUID

data class Template(
    @PrimaryKey val templateId: UUID = UUID.randomUUID(),
    val gameName: String,
    val maxPlayers: Int,
    val scoreType: String,
    val rows: Int,
    val rowTitles: List<String>?
)


object TemplatesList {
    private val templates: MutableList<Template> = mutableListOf()

    init{
        templates.add(Template(
            templateId = UUID.randomUUID(),
            gameName = "Chess",
            maxPlayers = 2,
            scoreType = "Rounds-Wins",
            rows = 0,
            rowTitles = null
        ))

        templates.add(Template(
            templateId = UUID.randomUUID(),
            gameName = "Uno",
            maxPlayers = 4,
            scoreType = "Rounds-Score",
            rows = 1,
            rowTitles = listOf("Score:", "Total")
        ))

        templates.add(Template(
            templateId = UUID.randomUUID(),
            gameName = "Spades",
            maxPlayers = 4,
            scoreType = "Rounds-Wins",
            rows = 1,
            rowTitles = listOf("Score:")
        ))
    }


    fun getTemplates(): List<Template>{
        return templates.toList()
    }

    fun getTemplateNames(): List<String>{
        return templates.map{template -> template.gameName}
    }


    fun addTemplates(template: Template){
        templates.add(template)
    }



}
