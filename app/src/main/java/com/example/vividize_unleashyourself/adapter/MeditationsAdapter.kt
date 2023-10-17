package com.example.vividize_unleashyourself.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.data.model.MeditationSession
import com.example.vividize_unleashyourself.databinding.MeditationsItemLayoutBinding
import com.example.vividize_unleashyourself.extensions.setButtonEffect
import com.example.vividize_unleashyourself.feature_vms.MeditationsViewModel

class MeditationsAdapter (private val context: Context,
                          private val dataset: List<MeditationSession>,
                          private val viewModel: MeditationsViewModel,
) : RecyclerView.Adapter<ViewHolder>() {

    inner class MeditationsViewHolder(val binding: MeditationsItemLayoutBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
            MeditationsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeditationsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is MeditationsViewHolder) {
            val binding = holder.binding
            val meditationsSession = dataset[position]
            if (position == 0) {
                binding.clTopDate.visibility = View.VISIBLE
                binding.tvDate.text = meditationsSession.formattedDateTimestamp


            } else if (position > 0) {
                if (meditationsSession.datestamp != dataset[position - 1].datestamp) {
                    binding.clTopDate.visibility = View.VISIBLE
                    binding.tvDate.text = meditationsSession.formattedDateTimestamp
                } else {
                    binding.clTopDate.visibility = View.GONE
                }
            }
            binding.tvIntentionTitle.text = meditationsSession.intention
            binding.tvMoodStartTitle.text = context.getString(R.string.mood_before, moodToEmoji(meditationsSession.moodStart))
            binding.tvMoodEndTitle.text = context.getString(R.string.mood_after, moodToEmoji(meditationsSession.moodEnd))


        }
    }

    private fun moodToEmoji(input: Double): String {

        return when (input) {
            20.0 -> "☹️"
            40.0 -> "\uD83D\uDE14"
            60.0 -> "😐"
            80.0 -> "☺️"
            else -> "\uD83D\uDE0A"
        }
    }
}