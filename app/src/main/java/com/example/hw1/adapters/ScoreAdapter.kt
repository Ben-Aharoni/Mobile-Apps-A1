package com.example.hw1.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hw1.databinding.ScoreItemBinding
import com.example.hw1.interfaces.Callback_HighScoreItemClicked
import com.example.hw1.model.Score

class ScoreAdapter(private var scoresRV: MutableList<Score>) :
    RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    var callback: Callback_HighScoreItemClicked? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        val binding = ScoreItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScoreViewHolder(binding)
    }

    override fun getItemCount(): Int = scoresRV.size

    private fun getItem(position: Int): Score = scoresRV[position]

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        with(holder) {
            with(getItem(position)) {
                binding.scoreLBLScore.text = buildString {
                    append("Score: ")
                    append(score)
                }
            }
        }
    }

    fun updateScores(newScores: List<Score>) {
        val oldSize = scoresRV.size
        scoresRV.clear()
        scoresRV.addAll(newScores)
        notifyItemRangeChanged(0, oldSize.coerceAtLeast(newScores.size))
    }


    inner class ScoreViewHolder(val binding: ScoreItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        init {
            binding.scoreCVScore.setOnClickListener {
                callback?.highScoreItemClicked(
                    getItem(adapterPosition).lat,
                    getItem(adapterPosition).lon
                )
            }
        }
    }
}