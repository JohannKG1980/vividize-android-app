package com.example.vividize_unleashyourself.adapter

import android.content.Context
import android.view.LayoutInflater
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
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val binding =
            MeditationsItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MeditationsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is MeditationsViewHolder) {
            val binding = holder.binding
            val meditationsSession = dataset[position]



        }
    }
}