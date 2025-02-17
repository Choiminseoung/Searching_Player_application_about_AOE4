package com.example.leaderboard.network

import com.example.leaderboard.model.PlayerGamesResponse
import com.example.leaderboard.model.PlayerProfile
import com.example.leaderboard.model.PlayerSearchResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

// TODO: API Service
//  1. suspend 키워드 추가 시 Coroutine 내부에서 실행 가능
//  2. 기존 데이터를 Call 방식이 아닌 자체로 반환
interface AoeAPIService {

    // TODO: 프로필 GET
    //  1. @Path -> URL 경로에 플레이어 ID SET
    //  2. suspend fun -> Coroutine 을 사용하여 비동기 처리 요청 수행
    //  3. : PlayerProfile -> PlayerProfile 데이터 형식 으로 반환
    @GET("players/{profile_id}")
    suspend fun getPlayerProfile(@Path("profile_id")profileID: Int):PlayerProfile

    @GET("players/{profile_id}/games")
    suspend fun getPlayerGames(
        @Path("profile_id") profileID: Int,
        @Query("limit") limit: Int = 5
    ) : PlayerGamesResponse

    @GET("players/search")
    suspend fun searchPlayers(@Query("query") query: String): PlayerSearchResponse


}