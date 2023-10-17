package com.example.vividize_unleashyourself.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vividize_unleashyourself.data.model.JournalEntry
import com.example.vividize_unleashyourself.databinding.JournalingItemLayoutBinding
import com.example.vividize_unleashyourself.feature_vms.JournalingViewModel

class JournalEntriesAdapter (private val context: Context,
                             private val dataset: MutableList<JournalEntry>,
                             private val viewModel: JournalingViewModel,
) : RecyclerView.Adapter<ViewHolder>() {

    inner class JournalEntriesViewHolder(val binding: JournalingItemLayoutBinding) :
       ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding =
         JournalingItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return JournalEntriesViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is JournalEntriesViewHolder) {
            val binding = holder.binding
            val entry = dataset[position]
            if (position == 0) {
                binding.clTopDate.visibility = View.VISIBLE
                binding.tvDate.text = entry.formattedDateTimestamp


            } else {
                if (entry.datestamp != dataset[position - 1].datestamp) {
                    binding.clTopDate.visibility = View.VISIBLE
                    binding.tvDate.text = entry.formattedDateTimestamp
                } else {
                    binding.clTopDate.visibility = View.GONE
                }
            }
            binding.tvTopicTitle.text = entry.content



        }
    }

}