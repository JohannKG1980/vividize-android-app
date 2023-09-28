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
import com.example.vividize_unleashyourself.data.model.FiveStepsSession
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

class FiveStepsFragment(
    private val sectionBinding: FragmentMentalSectionBinding,
    private var quickStart: Boolean = false
) : Fragment() {
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
        if (quickStart) {
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


        viewModel.currentCycle.observe(viewLifecycleOwner) { currentCycle ->
            viewModel.instructionWatched.observe(viewLifecycleOwner) { instructed ->
                viewModel.currentStep.observe(viewLifecycleOwner) { currentStep ->
                    if (currentStep != CurrentStep.NO_CYCLE_NOW) {
                        showOverlays(currentStep, instructed, currentCycle)
                    }
                }
            }
        }




        viewModel.allSessions.observe(viewLifecycleOwner) { sessions ->
            binding.rvFsmSessions.adapter = FiveStepsAdapter(sessions, viewModel)

        }
    }

    private fun showOverlays(
        currentStep: CurrentStep,
        instructionsDone: Boolean,
        currentCycle: FiveSteps
    ) {

            when (currentStep) {
                CurrentStep.DESCRIPTION -> openInstructions()
                CurrentStep.STEP_ONE -> openStepOne(currentCycle)
                CurrentStep.STEP_TWO -> openStepTwo(currentCycle)
                CurrentStep.STEP_THREE -> openStepThree(currentCycle)
                CurrentStep.STEP_THREE_ADD -> openStepThreeAdd(currentCycle)
                CurrentStep.STEP_FOUR -> openStepFour(currentCycle)
                CurrentStep.STEP_FIVE -> openStepFive(currentCycle, currentStep)

                else -> binding.cvOverlay.visibility = GONE


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
            viewModel.descriptionWatched()
            fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.visibility = GONE

        }

    }


    private fun openStepOne(currentCycle: FiveSteps) {
//        stepFiveOverlayBinding.overlayStepFive.visibility =
//            GONE  //hilfszeile für einen unerklärlichen bug beim cycle repeat
        binding.cvOverlay.visibility = VISIBLE
        stepOneOverlayBinding.overlayStepOne.visibility = VISIBLE


        val stepText = if (currentCycle.repeatAnswer) {
            getString(currentCycle.stepOneRepeat.content)
        } else {
            getString(currentCycle.stepOne.content)
        }

        stepOneOverlayBinding.tvStepText.text = stepText

        stepOneOverlayBinding.btnNext.setOnClickListener {

            val input = stepOneOverlayBinding.teTopic.text.toString()
            val intensity = stepOneOverlayBinding.slStartIntensity.value.toInt()
            stepOneOverlayBinding.teTopic.setText("")
            stepOneOverlayBinding.slStartIntensity.value = 0f
            stepOneOverlayBinding.overlayStepOne.visibility = GONE
            Log.e("TestStepOne", "Topic : $input   Intensity: $intensity ")
            viewModel.finishStepOne(input, intensity)

        }

    }

    private fun openStepTwo(currentCycle: FiveSteps) {


        stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.visibility = VISIBLE
        stepTwoAndThreeOverlayBinding.btnYes.text = "Ja"
        stepTwoAndThreeOverlayBinding.btnNo.text = "Nein"

        stepTwoAndThreeOverlayBinding.tvStep.text = getString(R.string.stepTwoTitle)
        stepTwoAndThreeOverlayBinding.tvStepText.text =
            getString(currentCycle.stepTwoQuestion.content)

        stepTwoAndThreeOverlayBinding.btnYes.setOnClickListener {
            currentCycle.stepTwoAnswer = true
            viewModel.finishStepTwo(true)

        }
        stepTwoAndThreeOverlayBinding.btnNo.setOnClickListener {
            currentCycle.stepTwoAnswer = false
            viewModel.finishStepTwo(false)

        }
    }

    private fun openStepThree(currentCycle: FiveSteps) {

        stepTwoAndThreeOverlayBinding.tvStep.text = getString(R.string.stepThreeTitle)
        stepTwoAndThreeOverlayBinding.tvStepText.text =
            getString(currentCycle.stepThreeQuestion.content)
        stepTwoAndThreeOverlayBinding.btnYes.text = "Ja"
        stepTwoAndThreeOverlayBinding.btnNo.text = "Nein"

        stepTwoAndThreeOverlayBinding.btnYes.setOnClickListener {
            currentCycle.stepThreeAnswer = true
            stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.visibility = GONE
            viewModel.finishStepThree(true)

        }
        stepTwoAndThreeOverlayBinding.btnNo.setOnClickListener {
            currentCycle.stepThreeAnswer = false
            viewModel.finishStepThree(false)

        }
    }

    private fun openStepThreeAdd(currentCycle: FiveSteps) {

        stepTwoAndThreeOverlayBinding.tvStep.text = getString(R.string.stepThreeTitle)
        stepTwoAndThreeOverlayBinding.tvStepText.text =
            getString(currentCycle.stepThreeFollowUp.content)
        stepTwoAndThreeOverlayBinding.btnYes.text = "Frei"
        stepTwoAndThreeOverlayBinding.btnNo.text = "Gefühl"


        stepTwoAndThreeOverlayBinding.btnYes.setOnClickListener {
            currentCycle.stepThreeBetterBeFree = true
            stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.visibility = GONE
            viewModel.finishStepThreeAdd(true)


        }
        stepTwoAndThreeOverlayBinding.btnNo.setOnClickListener {
            currentCycle.stepThreeBetterBeFree = false
            stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.visibility = GONE
            viewModel.finishStepThreeAdd(false)

        }
    }

    private fun openStepFour(currentCycle: FiveSteps) {
        stepFourOverlayBinding.overlayStepFour.visibility = VISIBLE

        stepFourOverlayBinding.tvStep.text = getString(R.string.stepFourTitle)
        stepFourOverlayBinding.tvStepText.text = getString(currentCycle.stepFourQuestion.content)


        stepFourOverlayBinding.btnNext.setOnClickListener {

            val input = stepFourOverlayBinding.teWhen.text.toString()
            currentCycle.stepFourAnswer = input
            stepFourOverlayBinding.teWhen.setText("")
            stepFourOverlayBinding.overlayStepFour.visibility = GONE
            viewModel.finishStepFour(input)

        }
    }

    private fun openStepFive(currentCycle: FiveSteps, currentStep: CurrentStep) {

            stepFiveOverlayBinding.overlayStepFive.visibility = VISIBLE

        stepFiveOverlayBinding.btnRepeat.setOnClickListener {
            val intensity = stepFiveOverlayBinding.slEndIntensity.value.toInt()
            currentCycle.intensityLeft = intensity
            currentCycle.repeatAnswer = true
            currentCycle.cycleFinished = true
            stepFiveOverlayBinding.slEndIntensity.value = 0f
            viewModel.finishStepFive(currentCycle)
            stepFiveOverlayBinding.overlayStepFive.visibility = GONE

        }

        stepFiveOverlayBinding.btnComplete.setOnClickListener {
            val intensity = stepFiveOverlayBinding.slEndIntensity.value.toInt()
            currentCycle.intensityLeft = intensity
            currentCycle.cycleFinished = true
            stepFiveOverlayBinding.slEndIntensity.value = 0f
            viewModel.finishStepFive(currentCycle)
            stepFiveOverlayBinding.overlayStepFive.visibility = GONE
            binding.cvOverlay.visibility = GONE

        }

    }


}