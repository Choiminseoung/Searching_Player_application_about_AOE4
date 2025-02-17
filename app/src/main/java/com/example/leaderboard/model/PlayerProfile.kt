package com.example.leaderboard.model

data class PlayerProfile(
    val profile_id: Int,
    val name: String,
    val rating: Int,
    val rank: Int,
    val win_rate: Double
)
