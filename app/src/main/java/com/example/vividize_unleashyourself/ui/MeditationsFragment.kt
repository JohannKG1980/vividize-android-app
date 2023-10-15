package com.example.vividize_unleashyourself.ui

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.lifecycleScope
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.adapter.MeditationTypeAdapter
import com.example.vividize_unleashyourself.adapter.MeditationsAdapter
import com.example.vividize_unleashyourself.databinding.FragmentMeditationsBinding
import com.example.vividize_unleashyourself.databinding.FragmentMentalSectionBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationFinisherBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationIntentionMoodBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationTimerBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationTypesBinding
import com.example.vividize_unleashyourself.extensions.fadeIn
import com.example.vividize_unleashyourself.extensions.fadeOut
import com.example.vividize_unleashyourself.extensions.setButtonEffect
import com.example.vividize_unleashyourself.feature_vms.MeditationsViewModel
import com.example.vividize_unleashyourself.feature_vms.CurrentState
import com.example.vividize_unleashyourself.feature_vms.TimerState
import com.google.android.material.internal.ViewUtils
import com.google.android.material.internal.ViewUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MeditationsFragment(private val sectionBinding: FragmentMentalSectionBinding) : Fragment(),
    LifecycleObserver {
    private lateinit var binding: FragmentMeditationsBinding
    private lateinit var selectorBinding: OverlayMeditationTypesBinding
    private lateinit var initSessionBinding: OverlayMeditationIntentionMoodBinding
    private lateinit var timerBinding: OverlayMeditationTimerBinding
    private lateinit var finisherBinding: OverlayMeditationFinisherBinding
    private val viewModel: MeditationsViewModel by activityViewModels()
//    private var isFragVisible = false


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

    @SuppressLint("RestrictedApi", "ClickableViewAccessibility")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        lifecycle.addObserver(this)
        binding.blurViewOne.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(sectionBinding.ivBg.drawable)
            .setBlurAutoUpdate(true)
            .setBlurRadius(3f)
        addObservers()
        binding.clMeditaionsMain.setOnTouchListener { _, _ ->
            hideKeyboard(binding.root)

            return@setOnTouchListener true

        }

        binding.ivAddSession.setOnClickListener {
            it.setButtonEffect()
            binding.ivCancleButton.isEnabled = false
            viewModel.openSessionSelector()
            binding.ivCancleButton.isEnabled = true

        }

        binding.ivCancleButton.setOnClickListener {
            it.setButtonEffect()
            binding.ivAddSession.isEnabled = false
            viewModel.cancelSession()
            lifecycleScope.launch(Dispatchers.Main) {

                binding.ivAddSession.isEnabled = true


            }

        }


    }

    private fun addObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.currentViewState
                .debounce(300)
                .distinctUntilChanged()
                .collectLatest { overlayState ->

                    when (overlayState) {
                        CurrentState.SELECTING_SESSION -> {
                            binding.cvOverlay.fadeIn()
                            openSessionSelector(overlayState)

                        }

                        CurrentState.GUIDED_INIT -> {
                            selectorBinding.overlayMeditationTypes.fadeOut(200)
                            openInitSession(overlayState)
                        }

                        CurrentState.UNGUIDED_INIT -> {
                            selectorBinding.overlayMeditationTypes.fadeOut(200)
                            openInitSession(overlayState)
                        }

                        CurrentState.SESSION_RUNNING -> {
                            initSessionBinding.overlayMeditationInit.fadeOut(200)
                            openTimer(overlayState)
                        }

                        CurrentState.SESSION_END -> {
                            timerBinding.overlayMeditationTimer.fadeOut(200)
                            openSessionFinisher(overlayState)
                        }

                        CurrentState.NO_SESSION -> {
                            binding.cvOverlay.fadeOut()
                            selectorBinding.overlayMeditationTypes.fadeOut(200)
                            initSessionBinding.overlayMeditationInit.fadeOut(200)
                            timerBinding.overlayMeditationTimer.fadeOut(200)
                            finisherBinding.overlayMeditationFinisher.fadeOut(200)
                        }


                    }

                }
        }
            lifecycleScope.launchWhenStarted {
                viewModel.allSessions.collectLatest {
                    binding.rvMeditationSessions.adapter = MeditationsAdapter(requireContext(), it, viewModel)
                }

            }


    }

    private fun openSessionSelector(viewState: CurrentState) {
        when (viewState) {
            CurrentState.SELECTING_SESSION -> {
                selectorBinding.overlayMeditationTypes.fadeIn()
            }

            CurrentState.NO_SESSION -> {
                selectorBinding.overlayMeditationTypes.fadeOut()
            }

            else -> {
                selectorBinding.overlayMeditationTypes.fadeOut()
            }
        }
        selectorBinding.rvMeditationSessions.adapter =
            MeditationTypeAdapter(requireContext(), viewModel.meditations, viewModel)


    }

    private fun openInitSession(viewState: CurrentState) {
        when (viewState) {
            CurrentState.UNGUIDED_INIT -> {
                initSessionBinding.overlayMeditationInit.fadeIn()
                initSessionBinding.slTimerSetter.visibility = VISIBLE
                initSessionBinding.cvTimeBg.visibility = VISIBLE
                initSessionBinding.tvTimerSet.visibility = VISIBLE
            }

            CurrentState.GUIDED_INIT -> {
                initSessionBinding.overlayMeditationInit.fadeIn()
                initSessionBinding.slTimerSetter.visibility = GONE
                initSessionBinding.cvTimeBg.visibility = GONE
                initSessionBinding.tvTimerSet.visibility = GONE
            }

            CurrentState.NO_SESSION -> {
                initSessionBinding.overlayMeditationInit.fadeOut(200)
            }

            else -> {
                initSessionBinding.overlayMeditationInit.fadeOut(200)
            }
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
            if (viewState == CurrentState.UNGUIDED_INIT) {
                val customDuration = initSessionBinding.slTimerSetter.value.toLong() * 1000 * 60
                viewModel.initSession(customDuration, initMood, intention)
            } else {
                viewModel.initSession(0, initMood, intention)
            }
            initSessionBinding.slTimerSetter.value = 0F
            initSessionBinding.slStartMood.value = 0F
            initSessionBinding.teIntention.setText("")
            initSessionBinding.overlayMeditationInit.fadeOut(200)

        }

    }

    private fun openTimer(viewState: CurrentState) {
        when (viewState) {
            CurrentState.SESSION_RUNNING -> {
                timerBinding.overlayMeditationTimer.fadeIn(200)
            }

            CurrentState.NO_SESSION -> {
                timerBinding.overlayMeditationTimer.fadeOut(200)
            }

            else -> {
                timerBinding.overlayMeditationTimer.fadeOut(200)
            }
        }

        val sessionDuration = viewModel.currentSession.value!!.duration
        viewModel.startTimer(sessionDuration)
        lifecycleScope.launchWhenStarted {
            viewModel.currentTimerState.collectLatest { currentTimerState ->
                if (currentTimerState == TimerState.RUNNING) {
                    timerBinding.ivPlayPauseBtnIcon.setImageResource(R.drawable.round_pause_24)
                } else if (currentTimerState == TimerState.PAUSED) {
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
        timerBinding.cvPlayPauseBtn.setOnClickListener {
            it.setButtonEffect()
            viewModel.pauseRestartTimer()
        }
    }

    private fun openSessionFinisher(viewState: CurrentState) {
        when (viewState) {
            CurrentState.SESSION_END -> {
                finisherBinding.overlayMeditationFinisher.fadeIn()
            }

            CurrentState.NO_SESSION -> {
                binding.cvOverlay.visibility = GONE
                finisherBinding.overlayMeditationFinisher.fadeOut(200)
            }

            else -> {
                finisherBinding.overlayMeditationFinisher.fadeOut(200)
            }
        }

        finisherBinding.slEndMood.setLabelFormatter { value ->
            return@setLabelFormatter when {
                value <= 20.0 -> "☹️"
                value <= 40.0 -> "\uD83D\uDE14"
                value <= 60.0 -> "😐"
                value <= 80.0 -> "☺️"
                else -> "\uD83D\uDE0A"
            }

        }
        finisherBinding.btnEnd.setOnClickListener {
            val endmood = finisherBinding.slEndMood.value.toDouble()
            val endNote = finisherBinding.teEndNote.text.toString()
            viewModel.finishSession(endmood, endNote)
            finisherBinding.slEndMood.value = 0F
            finisherBinding.teEndNote.setText("")
        }
    }

    private fun updateDigit(textView: TextView, newValue: String) {

        if (textView.text != newValue) {

            textView.fadeOut(300, false)


            textView.postDelayed({
                textView.text = newValue

                textView.fadeIn(300)
            }, 300)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.cancelSession()
    }

}