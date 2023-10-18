package com.example.vividize_unleashyourself.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vividize_unleashyourself.R
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

    @SuppressLint("ClickableViewAccessibility")
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
            binding.tvTopicTitle.text = HtmlCompat.fromHtml(entry.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.clItemDelete.setOnTouchListener { v, event ->
                v.onTouchEvent(event)
                true
            }
            binding.clItemDelete.setTransitionListener(object :
                androidx.constraintlayout.motion.widget.MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: androidx.constraintlayout.motion.widget.MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {
                    Log.d("MotionLayout", "Transition started from $startId to $endId")
                }

                override fun onTransitionChange(
                    motionLayout: androidx.constraintlayout.motion.widget.MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {
                }

                override fun onTransitionCompleted(
                    motionLayout: androidx.constraintlayout.motion.widget.MotionLayout?,
                    currentId: Int
                ) {
                    if (currentId == R.id.end) {  // Überprüfen Sie die ID des Endzustands
//                        onDelete(position)  // Löschen-Callback aufrufen
                    }
                }

                override fun onTransitionTrigger(
                    motionLayout: androidx.constraintlayout.motion.widget.MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {
                }
            })



        }
    }

}