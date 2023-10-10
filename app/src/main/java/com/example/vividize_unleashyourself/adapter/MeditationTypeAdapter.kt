package com.example.vividize_unleashyourself.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vividize_unleashyourself.data.model.Meditation
import com.example.vividize_unleashyourself.databinding.MeditationTypeItemBinding
import com.example.vividize_unleashyourself.feature_vms.MeditationsViewModel

class MeditationTypeAdapter(
    private val dataset: List<Meditation>,
    private val viewModel: MeditationsViewModel,
) : RecyclerView.Adapter<ViewHolder>() {

    inner class MeditationTypeViewHolder(val binding: MeditationTypeItemBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            MeditationTypeItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeditationTypeViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MeditationTypeViewHolder) {
            val binding = holder.binding
            val meditationType = dataset[position]
            binding.tvMediTitle.text = meditationType.name
            if(meditationType.guided) {
                binding.tvMediType.text = "Guided"
            } else {
                binding.tvMediType.text = "Unguided"

            }
            binding.tvMediDuration.text = meditationType.sessionLenght

            binding.cvMediType.setOnClickListener {
                viewModel.openSession(meditationType)
            }

        }
    }
}