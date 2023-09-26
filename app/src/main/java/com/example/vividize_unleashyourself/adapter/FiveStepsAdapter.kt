package com.example.vividize_unleashyourself.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vividize_unleashyourself.data.model.FiveStepsSession
import com.example.vividize_unleashyourself.databinding.FiveStepItemLayoutBinding
import com.example.vividize_unleashyourself.feature_vms.FiveStepsViewModel

class FiveStepsAdapter(
    private val dataset: MutableList<FiveStepsSession>,
    private val viewModel: FiveStepsViewModel
) : RecyclerView.Adapter<ViewHolder>() {

    inner class FiveStepsViewHolder(val binding: FiveStepItemLayoutBinding) :
        ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {

        val binding =
            FiveStepItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FiveStepsViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is FiveStepsViewHolder) {
            val session = dataset[position]
            val binding = holder.binding
            binding.tvTopic.text = session.stepCycles.first().stepOneInput
            binding.tvCycles.text = session.stepCycles.size.toString()
            binding.tvIntensityStart.text = session.stepCycles.first().intensity.toString()
            binding.tvIntensityEnd.text = session.stepCycles.last().intensityLeft.toString()
            if (position > 0) {
                if (session.datestamp != dataset[position - 1].datestamp) {
                    binding.tvDate.text = session.formattedDateTimestamp

                }
            }
        }
    }
}