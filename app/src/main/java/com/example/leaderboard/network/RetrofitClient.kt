package com.example.leaderboard.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// TODO: HTTP Client
//  1. Object Single Tone 패턴 ( 앱 전체에서 하나의 인스턴스를 공유 ( 메모리 절약 ) )
//  2. by lazy 를 사용 하여 필요할 때 초기화 되므로 지연 로딩 ( Lazy Initialization ) 지원
object RetrofitClient {

    // 기본 URL 설정
    // const val 사용 -> 컴파일 타임 상수로 저장 ( 런타임 변경 불가능 )
    private const val BASE_URL = "https://aoe4world.com/api/v0/"

    // TODO: HttpLoggingInterceptor 는 네트워크 요청 / 응답 로그를 기록하는 클래스
    //  1. Body 레벨을 설정하면 요청 및 응답 전체 데이터를 출력
    //  2. 기본적으로 개발환경에서는 BODY , 배포 환경에서는 NONE 으로 설정 추천
    //  3.  NONE → 로그 출력 없음
    //      BASIC → 요청/응답 라인만 출력 (URL, 메서드 등)
    //      HEADERS → 요청/응답 헤더 출력
    //      BODY → 요청/응답 전체 데이터 출력 (디버깅 용도)
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)         // HTTP 요청/응답 로그 기록
        .connectTimeout(30, TimeUnit.SECONDS) // 연결 시간 초과 설정
        .readTimeout(30, TimeUnit.SECONDS)    // 읽기 시간 초과 설정
        .writeTimeout(30, TimeUnit.SECONDS)   // 쓰기 시간 초과 설정
        .build()

    // TODO: Retrofit 객체 설정
    //  1. Retrofit.Builder() -> Retrofit 객체 생성 시작
    //  2. .baseUrl(BASE_URL) -> API의 기본 URL 설정
    //  3. .addConverterFactory(GsonConverterFactory.create()) -> JSON 응답을 자동으로 변환하는 변환기 추가
    //  4. .client(client) -> 위에서 설정한 OkHttpClient 적용
    //  5. .build()	-> Retrofit 객체 생성 완료
    //  6. .create(AoeAPIService::class.java) -> AoeAPIService 인터페이스와 연결
    //  7. by lazy -> 최초 사용될 때 한 번만 초기화 됨 ( 필요할 때만 생성되므로 불필요한 리소스 낭비 방지 )
    val apiService: AoeAPIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create()) // JSON 변환기 추가
            .client(client)
            .build()
            .create(AoeAPIService::class.java)
    }
}