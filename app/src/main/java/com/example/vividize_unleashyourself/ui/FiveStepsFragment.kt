package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.VISIBLE
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.vividize_unleashyourself.databinding.FiveStepsDescriptionOverlayBinding
import com.example.vividize_unleashyourself.databinding.FragmentFiveStepsBinding
import com.example.vividize_unleashyourself.databinding.FragmentMentalSectionBinding
import com.example.vividize_unleashyourself.databinding.StepFiveOverlayBinding
import com.example.vividize_unleashyourself.databinding.StepFourOverlayBinding
import com.example.vividize_unleashyourself.databinding.StepOneOverlayBinding
import com.example.vividize_unleashyourself.databinding.StepsTwoAndThreeOverlayBinding
import com.example.vividize_unleashyourself.feature_vms.FiveStepsViewModel
import eightbitlab.com.blurview.RenderScriptBlur

class FiveStepsFragment(val sectionBinding: FragmentMentalSectionBinding) : Fragment() {
    private lateinit var binding: FragmentFiveStepsBinding
    private lateinit var stepOneOverlayBinding: StepOneOverlayBinding
    private lateinit var stepTwoAndThreeOverlayBinding: StepsTwoAndThreeOverlayBinding
    private lateinit var stepFourOverlayBinding: StepFourOverlayBinding
    private lateinit var stepFiveOverlayBinding: StepFiveOverlayBinding
    private lateinit var fiveStepsDescriptionOverlayBinding: FiveStepsDescriptionOverlayBinding
    val viewModel: FiveStepsViewModel by activityViewModels()




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentFiveStepsBinding.inflate(layoutInflater)
        stepOneOverlayBinding = binding.overlayStepOne
        stepTwoAndThreeOverlayBinding = binding.overlayStepTwoAndThree
        stepFourOverlayBinding = binding.overlayStepFour
        stepFiveOverlayBinding = binding.overlayStepFive
        fiveStepsDescriptionOverlayBinding = binding.overlay5StepsDescription
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)




        binding.blurViewOne.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(sectionBinding.ivBg.drawable)
            .setBlurRadius(3f)


        binding.ivAddSession.setOnClickListener {



        }
    }

    private fun addObserver() {
        viewModel.currentSession.observe(viewLifecycleOwner) { currentSession ->
            viewModel.currentStep.observe(viewLifecycleOwner) {currentStep ->

            }
        }

        viewModel.allSessions.observe(viewLifecycleOwner) { sessions ->

        }
    }





}