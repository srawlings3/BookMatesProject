package graduateSchool.Spring2024.CapStone

data class Player(
    val playerId: Int,
    val playerName: String,
    val wins: Int,
    val gamesPlayed: Int,
    val stats: MutableMap<String, winLoss> = mutableMapOf()
)

data class winLoss(
    val GamesPlayed: Int,
    val wins: Int
)



object PlayerList {
    private val players: MutableList<Player> = mutableListOf()

    init{
        players.add(Player(
            1,
            "Stephen",
            0,
            0,

            ))

        players.add(Player(
            2,
            "Trent",
            0,
            0,

            ))

        players.add(Player(
            3,
            "Ellie",
            0,
            0,

            ))

        players.add(Player(
            4,
            "Ronin",
            0,
            0,

            ))
    }


    fun getPlayers(): List<Player>{
        return players.toList()
    }


    fun addPlayer(player: Player){
        players.add(player)
    }



}