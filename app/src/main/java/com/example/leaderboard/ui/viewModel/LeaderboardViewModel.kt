package com.example.leaderboard.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaderboard.model.Player
import com.example.leaderboard.repository.LeaderboardRepository
import kotlinx.coroutines.launch

class LeaderboardViewModel : ViewModel(){

    private val repository = LeaderboardRepository()

    private val _leaderboardList = MutableLiveData<List<Player>>()
    val leaderboardList : LiveData<List<Player>> = _leaderboardList

    private val _selectedMode = MutableLiveData<String>()
    val selectedMode: LiveData<String> = _selectedMode

    private val _modeList = MutableLiveData<List<String>>()
    val modeList: LiveData<List<String>> = _modeList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        loadOptions()
    }

    fun getTop50LeaderBoard(leaderboard : String) {
        viewModelScope.launch {
            repository.getLeaderBoard(leaderboard).fold(
                onSuccess = { reponse ->
                    if(reponse.players.isNotEmpty()) {
                        _leaderboardList.value = reponse.players
                    } else {
                        _errorMessage.value = "top100 error"
                    }
                },
                onFailure = {error ->
                    _errorMessage.value = "검색 오류: ${error.message}"
                }
            )
        }
    }

    // 선택된 옵션 업데이트
    fun selectMode(option: String) {
        _selectedMode.value = option
    }

    // 옵션 리스트 초기화
    private fun loadOptions() {
        _modeList.value = listOf("rm_solo","rm_team","rm_1v1","rm_2v2","rm_3v3","rm_4v4")
    }

}