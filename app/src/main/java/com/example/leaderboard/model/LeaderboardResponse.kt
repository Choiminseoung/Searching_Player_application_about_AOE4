package com.example.leaderboard.model

data class LeaderboardResponse(
    val leaderboard: String,
    val total_players: Int,
    val players: List<Player>
)

data class Player(
    val rank: Int,
    val profile_id: Int,
    val name: String,
    val clan: String?,
    val rating: Int,
    val country: String,
    val win_rate: Float,
    val games_played: Int,
    val wins: Int,
    val losses: Int,
    val streak: Int,
    val last_game_at: String

)
