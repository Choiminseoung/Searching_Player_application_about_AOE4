package com.example.leaderboard.network

import retrofit2.http.GET

// TODO: API Service
//  1. suspend 키워드 추가 시 Coroutine 내부에서 실행 가능
//  2. 기존 데이터를 Call 방식이 아닌 자체로 반환
interface AoeAPIService {

    // TODO:
    @GET("/api/v0/players/:profile_id")
    suspend fun searchPlayer(

    )

}