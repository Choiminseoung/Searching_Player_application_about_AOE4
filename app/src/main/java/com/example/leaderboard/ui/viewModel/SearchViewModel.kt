package com.example.leaderboard.ui.viewModel

import androidx.annotation.RestrictTo
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.viewModelScope
import com.example.leaderboard.model.PlayerGame
import com.example.leaderboard.model.PlayerSearchResult
import com.example.leaderboard.repository.SearchRepository
import kotlinx.coroutines.launch

class SearchViewModel : ViewModel() {

    private val repository = SearchRepository()

    // TODO: MutableLiveData , LiveData 사용
    //  1. MutableLiveData 사용 이유 -> 데이터 변경 가능성
    //  2. LiveData 사용 이유 -> UI 에서만 관찰 가능 하게
    private val _selectedPlayer = MutableLiveData<PlayerSearchResult?>()
    val selectedPlayer: LiveData<PlayerSearchResult?> = _selectedPlayer

    private val _gamesList = MutableLiveData<List<PlayerGame>>()
    val gamesList: LiveData<List<PlayerGame>> = _gamesList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    // TODO: 기본 Process
    //  1. viewModelScope.launch -> 코루틴을 이용한 비동기 처리
    //  2. repository.searchPlayer(query) -> 리포지터리 에서 검색 요청 실행
    //  3. fold { onSuccess, onFailure } -> fold (코틀린 에서는 지원 해주는게 많다..) 라는 인터페이스 가져와 성공할 떄와 실패 할때를 선언
    fun searchPlayer(query: String) {
        viewModelScope.launch {
            repository.searchPlayer(query).fold(
                onSuccess = { response ->
                    if (response.players.isNotEmpty()) {
                        _selectedPlayer.value = response.players.first() // 첫 번째 결과 선택
                    } else {
                        _errorMessage.value = "검색된 플레이어가 없습니다."
                    }
                },
                onFailure = { error ->
                    _errorMessage.value = "검색 오류: ${error.message}"
                }
            )
        }
    }

    fun getPlayerGames(profileId: Int) {
        viewModelScope.launch {
            repository.getPlayerGames(profileId).fold(
                onSuccess = { response ->
                    _gamesList.value = response.games
                },
                onFailure = { error ->
                    _errorMessage.value = "게임 데이터 로딩 실패: ${error.message}"
                }
            )
        }
    }

}

