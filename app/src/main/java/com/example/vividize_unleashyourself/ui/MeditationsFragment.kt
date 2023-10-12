package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.os.HandlerCompat.postDelayed
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
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
import com.example.vividize_unleashyourself.extensions.fadeIn
import com.example.vividize_unleashyourself.extensions.fadeOut
import com.example.vividize_unleashyourself.extensions.slideOutDown
import com.example.vividize_unleashyourself.extensions.slideOutUp
import com.example.vividize_unleashyourself.extensions.slideUp
import com.example.vividize_unleashyourself.feature_vms.MeditationsViewModel
import com.example.vividize_unleashyourself.feature_vms.currentState
import com.example.vividize_unleashyourself.feature_vms.timerState
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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        lifecycle.addObserver(this)
        binding.blurViewOne.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(sectionBinding.ivBg.drawable)
            .setBlurAutoUpdate(true)
            .setBlurRadius(3f)
        addObservers()

        binding.ivAddSession.setOnClickListener {
//            if (isFragVisible) {
                binding.ivCancleButton.isEnabled = false
                viewModel.openSessionSelector()


                binding.cvOverlay.fadeIn()


                binding.ivCancleButton.isEnabled = true
//            }
        }

        binding.ivCancleButton.setOnClickListener {
            binding.ivAddSession.isEnabled = false
            viewModel.cancelSession()
            lifecycleScope.launch(Dispatchers.Main) {
                binding.cvOverlay.fadeOut()
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
                        currentState.SELECTING_SESSION -> {

                            openSessionSelector(overlayState)

                        }

                        currentState.GUIDED_INIT -> {
                            selectorBinding.overlayMeditationTypes.fadeOut(200)
                            openInitSession(overlayState)
                        }

                        currentState.UNGUIDED_INIT -> {
                            selectorBinding.overlayMeditationTypes.fadeOut(200)
                            openInitSession(overlayState)
                        }

                        currentState.SESSION_RUNNING -> {
                            initSessionBinding.overlayMeditationInit.fadeOut(200)
                            openTimer(overlayState)
                        }

                        currentState.SESSION_END -> {
                            timerBinding.overlayMeditationTimer.fadeOut(200)
                            openSessionFinisher(overlayState)
                        }

                        currentState.NO_SESSION -> {
                            selectorBinding.overlayMeditationTypes.fadeOut(200)
                            initSessionBinding.overlayMeditationInit.fadeOut(200)
                            timerBinding.overlayMeditationTimer.fadeOut(200)
                            finisherBinding.overlayMeditationFinisher.fadeOut(200)
                        }


                    }

                }

        }

    }

    private fun openSessionSelector(viewState: currentState) {
        when (viewState) {
            currentState.SELECTING_SESSION -> {
                selectorBinding.overlayMeditationTypes.fadeIn()
            }

            currentState.NO_SESSION -> {
                selectorBinding.overlayMeditationTypes.fadeOut()
            }

            else -> {
                selectorBinding.overlayMeditationTypes.fadeOut()
            }
        }
        selectorBinding.rvMeditationSessions.adapter =
            MeditationTypeAdapter(requireContext(), viewModel.meditations, viewModel)


    }

    private fun openInitSession(viewState: currentState) {
        when (viewState) {
            currentState.UNGUIDED_INIT -> {
                initSessionBinding.overlayMeditationInit.fadeIn()
                initSessionBinding.slTimerSetter.visibility = VISIBLE
                initSessionBinding.cvTimeBg.visibility = VISIBLE
                initSessionBinding.tvTimerSet.visibility = VISIBLE
            }
            currentState.GUIDED_INIT -> {
                initSessionBinding.overlayMeditationInit.fadeIn()
                initSessionBinding.slTimerSetter.visibility = GONE
                initSessionBinding.cvTimeBg.visibility = GONE
                initSessionBinding.tvTimerSet.visibility = GONE
            }
            currentState.NO_SESSION -> {
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
            if (viewState == currentState.UNGUIDED_INIT) {
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

    private fun openTimer(viewState: currentState) {
        when (viewState) {
            currentState.SESSION_RUNNING -> {
                timerBinding.overlayMeditationTimer.fadeIn(200)
            }
            currentState.NO_SESSION -> {
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

    private fun openSessionFinisher(viewState: currentState) =
        when (viewState) {
            currentState.SESSION_END -> {
                finisherBinding.overlayMeditationFinisher.fadeIn()
            }
            currentState.NO_SESSION -> {
                binding.cvOverlay.visibility  = GONE
                finisherBinding.overlayMeditationFinisher.fadeOut(200)
            }
            else -> {
                finisherBinding.overlayMeditationFinisher.fadeOut(200)
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



//    override fun onResume() {
//        super.onResume()
//        onFragmentResumed()
//    }
//



}