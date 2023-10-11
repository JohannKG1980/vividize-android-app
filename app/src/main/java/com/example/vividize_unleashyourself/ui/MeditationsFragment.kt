package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.adapter.MeditationTypeAdapter
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.databinding.FragmentMeditationsBinding
import com.example.vividize_unleashyourself.databinding.FragmentMentalSectionBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationFinisherBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationIntentionMoodBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationTimerBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationTypesBinding
import com.example.vividize_unleashyourself.extensions.slideUp
import com.example.vividize_unleashyourself.feature_vms.MeditationsViewModel
import com.example.vividize_unleashyourself.feature_vms.currentState
import com.example.vividize_unleashyourself.feature_vms.timerState
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class MeditationsFragment(private val sectionBinding: FragmentMentalSectionBinding) : Fragment() {
    private lateinit var binding: FragmentMeditationsBinding
    private lateinit var selectorBinding: OverlayMeditationTypesBinding
    private lateinit var initSessionBinding: OverlayMeditationIntentionMoodBinding
    private lateinit var timerBinding: OverlayMeditationTimerBinding
    private lateinit var finisherBinding: OverlayMeditationFinisherBinding
    private val viewModel: MeditationsViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentMeditationsBinding.inflate(layoutInflater)
        selectorBinding = binding.overlayMeditationTypes
        initSessionBinding = binding.overlayMeditationInit
        timerBinding = binding.overlayMeditationTimer
        finisherBinding = binding.overlayMeditationFinisher
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.blurViewOne.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(sectionBinding.ivBg.drawable)
            .setBlurAutoUpdate(true)
            .setBlurRadius(3f)

        addObservers()
        binding.ivAddSession.setOnClickListener {
            viewModel.openSessionSelector()
        }

        binding.ivCancleButton.setOnClickListener {

            viewModel.cancelSession()

        }


    }

    private fun addObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.currentViewState.collectLatest { overlayState ->

                when (overlayState) {
                    currentState.SELECTING_SESSION -> openSessionSelector(overlayState)

                    currentState.GUIDED_INIT -> {
                        selectorBinding.overlayMeditationTypes.visibility = GONE
                        openInitSession(overlayState)
                    }

                    currentState.UNGUIDED_INIT -> {
                        selectorBinding.overlayMeditationTypes.visibility = GONE
                        openInitSession(overlayState)
                    }

                    currentState.SESSION_RUNNING -> {
                        initSessionBinding.overlayMeditationInit.visibility = GONE
                        openTimer(overlayState)
                    }
                    currentState.SESSION_END -> {
                        timerBinding.overlayMeditationTimer.visibility = GONE
                        openSessionFinisher(overlayState)
                    }
                    currentState.NO_SESSION -> {
                        binding.cvOverlay.visibility = GONE
                        selectorBinding.overlayMeditationTypes.visibility  = GONE
                        initSessionBinding.overlayMeditationInit.visibility = GONE
                        timerBinding.overlayMeditationTimer.visibility = GONE
                        finisherBinding.overlayMeditationFinisher.visibility = GONE
                    }

                    else -> binding.cvOverlay.visibility = GONE

                }

            }

        }

    }

    private fun openSessionSelector(viewState: currentState) {
        if (viewState == currentState.SELECTING_SESSION) {
            binding.cvOverlay.visibility = VISIBLE
            selectorBinding.overlayMeditationTypes.visibility = VISIBLE
        } else if (viewState == currentState.NO_SESSION) {
            binding.cvOverlay.visibility = GONE
            selectorBinding.overlayMeditationTypes.visibility = GONE
        } else {
            selectorBinding.overlayMeditationTypes.visibility = GONE
        }
        selectorBinding.rvMeditationSessions.adapter =
            MeditationTypeAdapter(requireContext(), viewModel.meditations, viewModel)


    }

    private fun openInitSession(viewState: currentState) {
        if (viewState == currentState.UNGUIDED_INIT) {
            binding.cvOverlay.visibility = VISIBLE
            initSessionBinding.overlayMeditationInit.visibility = VISIBLE
            initSessionBinding.slTimerSetter.visibility = VISIBLE
            initSessionBinding.cvTimeBg.visibility = VISIBLE
            initSessionBinding.tvTimerSet.visibility = VISIBLE
        } else if (viewState == currentState.GUIDED_INIT) {
            binding.cvOverlay.visibility = VISIBLE
            initSessionBinding.overlayMeditationInit.visibility = VISIBLE
            initSessionBinding.slTimerSetter.visibility = GONE
            initSessionBinding.cvTimeBg.visibility = GONE
            initSessionBinding.tvTimerSet.visibility = GONE
        } else if (viewState == currentState.NO_SESSION) {
            binding.cvOverlay.visibility = GONE
            initSessionBinding.overlayMeditationInit.visibility = GONE
        } else {
            initSessionBinding.overlayMeditationInit.visibility = GONE
        }
        initSessionBinding.slStartMood.setLabelFormatter { value ->
            return@setLabelFormatter when {
                value <= 20.0 -> "☹️"
                value <= 40.0 -> "\uD83D\uDE14"
                value <= 60.0 -> "😐"
                value <= 80.0 -> "☺️"
                else -> "\uD83D\uDE0A"
            }
        }
        initSessionBinding.slTimerSetter.setLabelFormatter { value ->
            return@setLabelFormatter "${value.toInt()} Min."
        }
        initSessionBinding.btnNext.setOnClickListener {
            val initMood = initSessionBinding.slStartMood.value.toDouble()
            val intention = initSessionBinding.teIntention.text.toString()
            if (viewState == currentState.UNGUIDED_INIT) {
                val customDuration = initSessionBinding.slTimerSetter.value.toLong() * 1000 * 60
                viewModel.initSession(customDuration, initMood, intention)
            } else {
                viewModel.initSession(0, initMood, intention)
            }
            initSessionBinding.slTimerSetter.value = 0F
            initSessionBinding.slStartMood.value = 0F
            initSessionBinding.teIntention.setText("")
            initSessionBinding.overlayMeditationInit.visibility = GONE

        }

    }

    private fun openTimer(viewState: currentState) {
        if (viewState == currentState.SESSION_RUNNING) {
            binding.cvOverlay.visibility = VISIBLE
            timerBinding.overlayMeditationTimer.visibility = VISIBLE
        } else if (viewState == currentState.NO_SESSION) {
            binding.cvOverlay.visibility = GONE
            timerBinding.overlayMeditationTimer.visibility = GONE
        } else {
            timerBinding.overlayMeditationTimer.visibility = GONE
        }

        val sessionDuration = viewModel.currentSession.value!!.duration
        viewModel.startTimer(sessionDuration)
        lifecycleScope.launchWhenStarted {
            viewModel.currentTimerState.collectLatest { currentTimerState ->
                if (currentTimerState == timerState.RUNNING) {
                    timerBinding.ivPlayPauseBtnIcon.setImageResource(R.drawable.round_pause_24)
                } else if (currentTimerState == timerState.PAUSED) {
                    timerBinding.ivPlayPauseBtnIcon.setImageResource(R.drawable.round_play_arrow_24)
                }

            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.remainingTime.collectLatest { remainingTime ->
                val minutes = (remainingTime / (1000 * 60)).toInt()
                val seconds = (remainingTime / 1000).toInt() % 60

                updateDigit(timerBinding.tvMinTens, (minutes / 10).toString())
                updateDigit(timerBinding.tvMinOnes, (minutes % 10).toString())
                updateDigit(timerBinding.tvSecTens, (seconds / 10).toString())
                updateDigit(timerBinding.tvSecOnes, (seconds % 10).toString())

                var progressPercentage =
                    (remainingTime.toFloat() / sessionDuration) * 100
                timerBinding.circularProgressBar.progress = progressPercentage
            }

        }
        timerBinding.ivPlayPauseBtnIcon.setOnClickListener {
            viewModel.pauseRestartTimer()
        }
    }
    private fun openSessionFinisher(viewState: currentState) {
        if (viewState == currentState.SESSION_END) {
            binding.cvOverlay.visibility = VISIBLE
            finisherBinding.overlayMeditationFinisher.visibility = VISIBLE
        } else if (viewState == currentState.NO_SESSION) {
            binding.cvOverlay.visibility = GONE
            finisherBinding.overlayMeditationFinisher.visibility = GONE
        } else {
            finisherBinding.overlayMeditationFinisher.visibility = GONE
        }



    }

    private fun updateDigit(textView: TextView, newValue: String) {
        if (textView.text != newValue) {
            textView.text = newValue
            textView.slideUp(1000L, 0L)
        }
    }
}
