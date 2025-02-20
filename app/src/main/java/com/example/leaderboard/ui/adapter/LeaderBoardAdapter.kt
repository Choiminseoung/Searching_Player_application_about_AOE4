package com.example.leaderboard.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.ViewParent
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.leaderboard.R
import com.example.leaderboard.databinding.ItemLeaderboardBinding
import com.example.leaderboard.model.Player
import java.util.Locale

// TODO:
class LeaderBoardAdapter (private val onItemClick: (Player) -> Unit ) :
    ListAdapter<Player,LeaderBoardAdapter.LeaderBoardViewHolder>(LeaderBoardDiffCallback()){

    class LeaderBoardViewHolder(private val binding: ItemLeaderboardBinding) :
        RecyclerView.ViewHolder(binding.root) {
            fun bind(player: Player, onItemClick: (Player) -> Unit) {
                binding.txtRank.text = itemView.context.getString(R.string.player_rank, player.rank)
                binding.txtName.text = player.name
                binding.txtRating.text = String.format(Locale.getDefault(), "%d", player.rating)

                binding.root.setOnClickListener{onItemClick(player)}

            }
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LeaderBoardViewHolder {
        val binding = ItemLeaderboardBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return LeaderBoardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: LeaderBoardViewHolder, position: Int){
        // ListAdapter 내부 메서드 getItem
        val player = getItem(position)
        holder.bind(player,onItemClick)
    }

}

class LeaderBoardDiffCallback : DiffUtil.ItemCallback<Player>() {
    override fun areItemsTheSame(oldItem: Player, newItem: Player): Boolean {
        // 이름 기준 으로 변경 ( 리더 보드 이기 때문에 이름이 바뀌게 되면 갱신 하는 걸로 )
        return oldItem.name == newItem.name
    }

    override fun areContentsTheSame(oldItem: Player, newItem: Player): Boolean {
        // 내용이 같으면 UI 업데이트 안함
        return oldItem == newItem
    }
}