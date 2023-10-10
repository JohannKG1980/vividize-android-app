package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.databinding.FragmentMeditationsBinding
import com.example.vividize_unleashyourself.databinding.FragmentMentalSectionBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationFinisherBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationIntentionMoodBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationTimerBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationTypesBinding
import com.example.vividize_unleashyourself.feature_vms.MeditationsViewModel
import com.example.vividize_unleashyourself.feature_vms.currentState
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
        savedInstanceState: Bundle?
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
//        val tabHostBinding = FragmentMentalSectionBinding.inflate(layoutInflater)
//            binding.blurView1.setupWith(binding.root, RenderScriptBlur(requireContext()))
//                .setBlurAutoUpdate(true)
//                .setBlurRadius(3f)
//                //.setFrameClearDrawable(tabHostBinding.ivBg.drawable) // Optional

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
            binding.cvOverlay.visibility = GONE
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
    }

    private fun addObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.currentViewState.collectLatest { overlayState ->

                when (overlayState) {
                    currentState.SELECTING_SESSION -> openSessionSelector(overlayState)

                    else -> binding.cvOverlay.visibility = GONE

                }

            }
        }

    }

    private fun openSessionSelector(viewState: currentState) {
        if(viewState == currentState.SELECTING_SESSION) {
            binding.cvOverlay.visibility = VISIBLE
            selectorBinding.overlayMeditationTypes.visibility = VISIBLE
        } else if(viewState == currentState.NO_SESSION) {
            binding.cvOverlay.visibility = GONE
            selectorBinding.overlayMeditationTypes.visibility = GONE
        }



    }
}
