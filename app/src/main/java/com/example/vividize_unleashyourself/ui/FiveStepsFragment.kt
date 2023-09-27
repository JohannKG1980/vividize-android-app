package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.adapter.FiveStepsAdapter
import com.example.vividize_unleashyourself.data.model.FiveSteps
import com.example.vividize_unleashyourself.databinding.FiveStepsDescriptionOverlayBinding
import com.example.vividize_unleashyourself.databinding.FragmentFiveStepsBinding
import com.example.vividize_unleashyourself.databinding.FragmentMentalSectionBinding
import com.example.vividize_unleashyourself.databinding.StepFiveOverlayBinding
import com.example.vividize_unleashyourself.databinding.StepFourOverlayBinding
import com.example.vividize_unleashyourself.databinding.StepOneOverlayBinding
import com.example.vividize_unleashyourself.databinding.StepsTwoAndThreeOverlayBinding
import com.example.vividize_unleashyourself.feature_vms.CurrentStep
import com.example.vividize_unleashyourself.feature_vms.FiveStepsViewModel
import eightbitlab.com.blurview.RenderScriptBlur

class FiveStepsFragment(val sectionBinding: FragmentMentalSectionBinding, var quickStart: Boolean = false) : Fragment() {
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


        addObserver()
        if(quickStart) {
            viewModel.openSession()
        }
        binding.ivAddSession.setOnClickListener {
            viewModel.openSession()

        }

        binding.ivCancleButton.setOnClickListener {
            viewModel.closeSession()
            binding.cvOverlay.visibility = GONE
            fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.visibility = GONE
            stepOneOverlayBinding.overlayStepOne.visibility = GONE
            stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.visibility = GONE
            stepFourOverlayBinding.overlayStepFour.visibility = GONE
            stepFiveOverlayBinding.overlayStepFive.visibility = GONE

        }
    }

    private fun addObserver() {

        viewModel.currentSession.observe(viewLifecycleOwner) {currentSession ->
                viewModel.instructionWatched.observe(viewLifecycleOwner) { instructed ->
                    viewModel.currentStep.observe(viewLifecycleOwner) { currentStep ->
                        if (currentStep != CurrentStep.NO_CYCLE_NOW) {
                        showOverlays(currentStep, instructed)
                    }
                }
            }
        }




        viewModel.allSessions.observe(viewLifecycleOwner) { sessions ->
            binding.rvFsmSessions.adapter = FiveStepsAdapter(sessions, viewModel)

        }
    }

    private fun showOverlays(currentStep: CurrentStep, instructionsDone: Boolean) {

        if (!instructionsDone && currentStep == CurrentStep.DESCRIPTION) {
            openInstructions()
        } else if (instructionsDone && currentStep != CurrentStep.DESCRIPTION) {
            when (currentStep) {
                CurrentStep.STEP_ONE -> openStepOne()
                CurrentStep.STEP_TWO -> openStepTwo()
                else -> binding.cvOverlay.visibility = GONE


            }
        }
    }

    private fun openInstructions() {
        binding.cvOverlay.visibility = VISIBLE
        fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.visibility = VISIBLE
        val descriptionView = fiveStepsDescriptionOverlayBinding.tvDescriptionText
        val text = getString(R.string.fsm_description)
        descriptionView.text = HtmlCompat.fromHtml(text, HtmlCompat.FROM_HTML_MODE_LEGACY)
        val scrollView: ScrollView = fiveStepsDescriptionOverlayBinding.svDescription
        val speed = 4  // Geschwindigkeit des Scrollens, je kleiner desto langsamer
        val handler = Handler(Looper.getMainLooper())
        handler.post(object : Runnable {
            override fun run() {
                scrollView.smoothScrollBy(0, speed)
                handler.postDelayed(this, 100)
            }
        })


        fiveStepsDescriptionOverlayBinding.btnNext.setOnClickListener {
            fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.visibility = GONE
            viewModel.despriptionWatched()
           openStepOne()
        }

    }

    private fun openStepOne() {
        binding.cvOverlay.visibility = VISIBLE
        stepOneOverlayBinding.overlayStepOne.visibility = VISIBLE

        val stepCycles = viewModel.currentSession.value!!.stepCycles


        val stepText = if(stepCycles.isNotEmpty()) {
            if (stepCycles.size <= 1) {
                getString(stepCycles.last().stepOne.content)
            } else {
                stepCycles.add(FiveSteps(stepCycles.last().cycleId + 1))
                getString(stepCycles.last().stepOneRepeat.content)
            }
        }else {
            stepCycles.add(FiveSteps(1))
            getString(stepCycles.last().stepOne.content)
        }

        stepOneOverlayBinding.tvStepText.text = stepText

        stepOneOverlayBinding.btnNext.setOnClickListener {

            val input = stepOneOverlayBinding.teTopic.text.toString()
            val intensity = stepOneOverlayBinding.slStartIntensity.value.toInt()
            viewModel.finishStepOne(input, intensity)
            stepOneOverlayBinding.teTopic.setText("")
            stepOneOverlayBinding.slStartIntensity.value = 0f
            stepOneOverlayBinding.overlayStepOne.visibility = GONE
            Log.e("TestStepOne", "Topic : $input   Intensity: $intensity ")
            openStepTwo()
        }

    }

    private fun openStepTwo() {
        stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.visibility = VISIBLE


        stepTwoAndThreeOverlayBinding.tvStep

    }

    override fun onResume() {
        super.onResume()

    }
}