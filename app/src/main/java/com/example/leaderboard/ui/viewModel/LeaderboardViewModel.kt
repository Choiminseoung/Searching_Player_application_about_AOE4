package com.example.leaderboard.ui.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.leaderboard.model.LeaderboardResponse
import com.example.leaderboard.model.Player
import com.example.leaderboard.repository.LeaderboardRepository
import kotlinx.coroutines.launch

class LeaderboardViewModel : ViewModel(){

    private val repository = LeaderboardRepository()

    private val _leaderboardList = MutableLiveData<List<Player>>()
    val leaderboardList : LiveData<List<Player>> = _leaderboardList

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    fun getTop100LeaderBoard(leaderboard : String) {
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

}