package com.example.vividize_unleashyourself.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.transition.TransitionInflater
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.data.model.JournalEntry
import com.example.vividize_unleashyourself.databinding.JournalingItemLayoutBinding
import com.example.vividize_unleashyourself.extensions.setButtonEffect
import com.example.vividize_unleashyourself.feature_vms.JournalingViewModel

class JournalEntriesAdapter(
    private val context: Context,
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
            val alertBuilder = AlertDialog.Builder(context, R.style.CustomAlertDialogTheme)

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
            binding.tvTopicTitle.text =
                HtmlCompat.fromHtml(entry.content, HtmlCompat.FROM_HTML_MODE_LEGACY)
            binding.tvJrnlContent.text = entry.timestamp
            binding.clDel.setOnClickListener {
                binding.ivDelete.setButtonEffect()
                alertBuilder.setTitle(R.string.alert_title)
                alertBuilder.setMessage(R.string.alert_del_journal)

                alertBuilder.setPositiveButton(R.string.yes) { dialog, _ ->
                    viewModel.removeEntry(entry)
                    dialog.dismiss()
                }

                alertBuilder.setNegativeButton(R.string.no) { dialog, _ ->
                    dialog.dismiss()
                }

                val alertDialog = alertBuilder.create()
                alertDialog.show()
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.WHITE)
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(Color.RED)
            }

            binding.clJrnItemMain.setOnClickListener {
                binding.clItemMain.setButtonEffect()
                viewModel.setCurrentEntry(entry)
                holder.itemView.findNavController().navigate(R.id.journalEntryFragment)

            }


        }
    }

}