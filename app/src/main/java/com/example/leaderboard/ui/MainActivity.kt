package com.example.leaderboard.ui

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leaderboard.databinding.ActivityMainBinding
import com.example.leaderboard.ui.adapter.LeaderBoardAdapter
import com.example.leaderboard.ui.viewModel.LeaderboardViewModel

class MainActivity : AppCompatActivity() {
    // TODO: init , lazy , lateinit 관련
    //  1.객체 생성 후 바로 초기화해야 하는 경우 → init
    //  2.초기화 비용이 크고, 필요할 때만 값 설정 → lazy
    //  3.값을 나중에 설정해야 하고, var가 필요 → lateinit
    //  4.상속 시 부모 → 자식 순서로 init 실행됨.
    private val TAG: String = "MainActivity"
    private lateinit var binding : ActivityMainBinding
    private lateinit var adapter : LeaderBoardAdapter
    private val typeLeaderBoard : String = "rm_team"

    private val viewModel: LeaderboardViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewInit()
        observeLiveData()
        viewListener()

        viewModel.getTop100LeaderBoard(typeLeaderBoard)

    }

    private fun viewInit() {
        adapter = LeaderBoardAdapter { player ->
            // intent
            Log.i(TAG, "Select Player Item : $player")
        }
        // Recycler view setting
        binding.leaderboardRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.leaderboardRecyclerView.adapter = adapter
    }

    private fun viewListener() {
        binding.leaderboardRecyclerView.addOnScrollListener(object: RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                // 위로 스크롤할 때 새로고침
                if (!recyclerView.canScrollVertically(-1)) { // 최상단 체크
                    binding.swipeRefreshLayout.isRefreshing = true
                    viewModel.getTop100LeaderBoard(typeLeaderBoard)
                }
            }
        })
    }

    private fun observeLiveData() {
        viewModel.leaderboardList.observe(this) { items ->
            // DIffUtil 활용
            adapter.submitList(items)
            binding.swipeRefreshLayout.isRefreshing = false
        }

        viewModel.errorMessage.observe(this) { error ->
            Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
        }
    }
}