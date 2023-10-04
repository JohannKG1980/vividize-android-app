package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.databinding.FragmentMeditationsBinding
import com.example.vividize_unleashyourself.databinding.FragmentMentalSectionBinding
import com.example.vividize_unleashyourself.databinding.OverlayMeditationIntentionMoodBinding
import eightbitlab.com.blurview.RenderScriptBlur

class MeditationsFragment(private val sectionBinding: FragmentMentalSectionBinding) : Fragment() {
    private lateinit var binding: FragmentMeditationsBinding
    private lateinit var initOverlayBinding: OverlayMeditationIntentionMoodBinding
    private val viewModel: MainViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
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

    }

}
