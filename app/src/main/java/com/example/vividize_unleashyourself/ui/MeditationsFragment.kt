package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.databinding.FragmentMeditationsBinding
import com.example.vividize_unleashyourself.databinding.FragmentMentalSectionBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationIntentionMoodBinding
import eightbitlab.com.blurview.RenderScriptBlur

class MeditationsFragment(private val sectionBinding: FragmentMentalSectionBinding) : Fragment() {
    private lateinit var binding: FragmentMeditationsBinding
    private lateinit var initOverlayBinding: OverlayMeditationIntentionMoodBinding
    private val viewModel: MainViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment

        binding = FragmentMeditationsBinding.inflate(layoutInflater)
        initOverlayBinding = binding.overlayMeditationInit
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


        initOverlayBinding.slStartMood.setLabelFormatter { value ->
            return@setLabelFormatter when {
                value <= 20.0 -> "☹️"
                value <= 40.0 -> "\uD83D\uDE14"
                value <= 60.0 -> "😐"
                value <= 80.0 -> "☺️"
                else -> "\uD83D\uDE0A"
            }
        }
        initOverlayBinding.slTimerSetter.setLabelFormatter { value ->
            return@setLabelFormatter "${value.toInt()} Min."
        }
    }

}
