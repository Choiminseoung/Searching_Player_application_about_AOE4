package com.example.leaderboard.repository

import com.example.leaderboard.model.PlayerGamesResponse
import com.example.leaderboard.model.PlayerSearchResponse
import com.example.leaderboard.network.RetrofitClient

class SearchRepository {
    private val apiService = RetrofitClient.apiService

    // Player Search
    suspend fun searchPlayer(query: String): Result<PlayerSearchResponse> {
        return try{
            val response = apiService.searchPlayers(query)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // Player Games Search
    suspend fun getPlayerGames(profileId: Int, limit: Int = 5): Result<PlayerGamesResponse> {
        return try {
            val response = apiService.getPlayerGames(profileId, limit)
            Result.success(response)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}