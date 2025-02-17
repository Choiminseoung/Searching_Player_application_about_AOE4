package com.example.leaderboard.model

data class PlayerGamesResponse(
    val games: List<PlayerGame>
)

data class PlayerGame(
    val game_id: Long,
    val mode: String,
    val map: String,
    val winner: String,
    val loser: String
)