package com.example.vividize_unleashyourself.adapter

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.data.model.FiveStepsSession
import com.example.vividize_unleashyourself.databinding.FiveStepItemLayoutBinding
import com.example.vividize_unleashyourself.extensions.setButtonEffect
import com.example.vividize_unleashyourself.feature_vms.FiveStepsViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FiveStepsAdapter(
    private val context: Context,
    private val dataset: MutableList<FiveStepsSession>,
    private val viewModel: FiveStepsViewModel,
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


    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (holder is FiveStepsViewHolder) {
            val alertBuilder = AlertDialog.Builder(context, R.style.CustomAlertDialogTheme)
            val session = dataset[position]
            val binding = holder.binding
            val cycles = viewModel.getSessionCycles(session.sessionId)
            if (cycles.isNotEmpty()) {
                val startIntensity = cycles.first().intensity
                val endIntensity = cycles.last().intensityLeft
                val relief = startIntensity - endIntensity
                binding.tvTopic.text = session.topic
                binding.tvCycles.text = cycles.size.toString()

                binding.tvIntensityStart.text = cycles.first().intensity.toString() + " %"
                binding.tvIntensityEnd.text = relief.toString() + " %"
            }
            if (position == 0) {
                binding.clTopDate.visibility = VISIBLE
                binding.tvDate.text = session.formattedDateTimestamp


            } else {
                if (session.datestamp != dataset[position - 1].datestamp) {
                    binding.clTopDate.visibility = VISIBLE
                    binding.tvDate.text = session.formattedDateTimestamp
                } else {
                    binding.clTopDate.visibility = GONE
                }
            }

            binding.clDel.setOnClickListener {
                binding.ivDelete.setButtonEffect()
                alertBuilder.setTitle(R.string.alert_title)
                alertBuilder.setMessage(R.string.alert_del_session)

                alertBuilder.setPositiveButton(R.string.yes) { dialog, _ ->
                    viewModel.removeSession(session)
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
        }
    }
}