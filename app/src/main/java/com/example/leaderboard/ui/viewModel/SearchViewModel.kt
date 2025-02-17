package com.example.leaderboard.ui.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.leaderboard.repository.SearchRepository

class SearchViewModel : ViewModel() {
    private val repository = SearchRepository()
    // 자료 구조 set
    private val _searchResults = MutableLiveData<>()


}