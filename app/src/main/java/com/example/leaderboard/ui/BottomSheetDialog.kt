package com.example.leaderboard.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.leaderboard.databinding.BottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

class BottomSheetDialog (private val context : Context,
                         private val option: List<String>,
                         private val onOptionSelected: (String) -> Unit){

    fun show() {
        val bottomSheetDialog = BottomSheetDialog(context)
        val binding = BottomSheetBinding.inflate(LayoutInflater.from(context))

        // RecyclerView 설정
        binding.modeRecyclerview.layoutManager = LinearLayoutManager(context)
        binding.modeRecyclerview.adapter = SimpleListAdapter(option) { selectedOption ->
            onOptionSelected(selectedOption) // 선택한 모드 업데이트
            bottomSheetDialog.dismiss() // 바텀 시트 다이얼로그 Close
        }

        bottomSheetDialog.setContentView(binding.root)
        bottomSheetDialog.show()
    }

    class SimpleListAdapter(
        private val options: List<String>,
        private val onItemClick: (String) -> Unit
    ) : RecyclerView.Adapter<SimpleListAdapter.SimpleViewHolder>() {
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimpleViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(android.R.layout.simple_list_item_1, parent, false)
            return SimpleViewHolder(view as TextView)
        }

        override fun onBindViewHolder(holder: SimpleViewHolder, position: Int) {
            holder.textView.text = options[position]
            holder.itemView.setOnClickListener {
                onItemClick(options[position])
            }
        }

        override fun getItemCount(): Int = options.size

        class SimpleViewHolder(val textView: TextView) :
            RecyclerView.ViewHolder(textView)
    }
}