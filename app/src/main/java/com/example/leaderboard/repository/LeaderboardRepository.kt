package com.example.leaderboard.repository

import android.util.Log
import com.example.leaderboard.model.LeaderboardResponse
import com.example.leaderboard.model.PlayerSearchResponse
import com.example.leaderboard.network.RetrofitClient

class LeaderboardRepository {
    private val TAG : String = "LeaderboardRepository"
    private val apiService = RetrofitClient.apiService

    // 1Page 당 100 개
    suspend fun getLeaderBoard(leaderboard : String) : Result<LeaderboardResponse> {
        return try{
            val response = apiService.getLeaderboard(leaderboard)
            Result.success(response)
        } catch (e: Exception){
            Result.failure(e)
        }
    }
}