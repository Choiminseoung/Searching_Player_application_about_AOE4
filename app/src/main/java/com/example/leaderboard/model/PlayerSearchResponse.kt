package com.example.leaderboard.model

data class PlayerSearchResponse(
    val players: List<PlayerSearchResult>
)

data class PlayerSearchResult(
    val profile_id: Int,
    val name: String,
    val rank: Int
)
