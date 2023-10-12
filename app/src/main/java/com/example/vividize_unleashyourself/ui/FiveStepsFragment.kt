package com.example.vividize_unleashyourself.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.GestureDetector
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
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
import com.example.vividize_unleashyourself.extensions.fadeIn
import com.example.vividize_unleashyourself.extensions.fadeOut
import com.example.vividize_unleashyourself.feature_vms.CurrentStep
import com.example.vividize_unleashyourself.feature_vms.FiveStepsViewModel
import com.google.android.material.internal.ViewUtils
import com.google.android.material.internal.ViewUtils.hideKeyboard
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
@AndroidEntryPoint
class FiveStepsFragment(
    private val sectionBinding: FragmentMentalSectionBinding
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

    @SuppressLint("ClickableViewAccessibility", "RestrictedApi")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.blurViewOne.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(sectionBinding.ivBg.drawable)
            .setBlurRadius(3f)


        addObserver()

        binding.ivAddSession.setOnClickListener {
            viewModel.openSession()

        }

        binding.ivCancleButton.setOnClickListener {
            viewModel.closeSession()
            binding.cvOverlay.fadeOut()
            fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.fadeOut()
            stepOneOverlayBinding.overlayStepOne.fadeOut()
            stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.fadeOut()
            stepFourOverlayBinding.overlayStepFour.fadeOut()
            stepFiveOverlayBinding.overlayStepFive.fadeOut()


        }
        binding.ivInfoFsm.setOnClickListener {
           viewModel.openInstructions()
        }

        binding.clFiveMain.setOnTouchListener { _, _ ->
            hideKeyboard(binding.root)

            return@setOnTouchListener true


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
                CurrentStep.DESCRIPTION_FIRST -> openInstructions()
                CurrentStep.DESCRIPTION -> openInstructions(false)
                CurrentStep.STEP_ONE -> openStepOne(currentCycle)
                CurrentStep.STEP_TWO -> openStepTwo(currentCycle)
                CurrentStep.STEP_THREE -> openStepThree(currentCycle)
                CurrentStep.STEP_THREE_ADD -> openStepThreeAdd(currentCycle)
                CurrentStep.STEP_FOUR -> openStepFour(currentCycle)
                CurrentStep.STEP_FIVE -> openStepFive(currentCycle, currentStep)
                CurrentStep.NO_CYCLE_NOW -> binding.cvOverlay.fadeOut()

            }

    }

    private fun openInstructions(firstTime: Boolean = true) {
        binding.cvOverlay.fadeIn(300)
        fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.fadeIn(300)
        if(!firstTime){
            fiveStepsDescriptionOverlayBinding.btnNext.visibility = GONE
        } else {
            fiveStepsDescriptionOverlayBinding.btnNext.visibility = VISIBLE
        }
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
            if(firstTime) {
                viewModel.descriptionWatched()
                fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.fadeOut(200)
                viewModel.changeToStepOne()
            } else {
                viewModel.descriptionWatched()
                fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.visibility = GONE
                binding.cvOverlay.visibility = GONE

            }

        }

    }


    @SuppressLint("ClickableViewAccessibility", "RestrictedApi")
    private fun openStepOne(currentCycle: FiveSteps) {
        binding.cvOverlay.fadeIn(200)
        stepOneOverlayBinding.overlayStepOne.fadeIn(300)


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
            stepOneOverlayBinding.overlayStepOne.fadeOut(200)
            Log.e("TestStepOne", "Topic : $input   Intensity: $intensity ")
            viewModel.finishStepOne(input, intensity)

        }

    }

    private fun openStepTwo(currentCycle: FiveSteps) {


        stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.fadeIn(300)
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
            stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.fadeOut(200)
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
            stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.fadeOut(200)
            viewModel.finishStepThreeAdd(true)


        }
        stepTwoAndThreeOverlayBinding.btnNo.setOnClickListener {
            currentCycle.stepThreeBetterBeFree = false
            stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.fadeOut(200)
            viewModel.finishStepThreeAdd(false)

        }
    }

    private fun openStepFour(currentCycle: FiveSteps) {
        stepFourOverlayBinding.overlayStepFour.fadeIn(300)

        stepFourOverlayBinding.tvStep.text = getString(R.string.stepFourTitle)
        stepFourOverlayBinding.tvStepText.text = getString(currentCycle.stepFourQuestion.content)


        stepFourOverlayBinding.btnNext.setOnClickListener {

            val input = stepFourOverlayBinding.teWhen.text.toString()
            currentCycle.stepFourAnswer = input
            stepFourOverlayBinding.teWhen.setText("")
            stepFourOverlayBinding.overlayStepFour.fadeOut(200)
            viewModel.finishStepFour(input)

        }
    }

    private fun openStepFive(currentCycle: FiveSteps, currentStep: CurrentStep) {

            stepFiveOverlayBinding.overlayStepFive.fadeIn(300)

        stepFiveOverlayBinding.btnRepeat.setOnClickListener {
            val intensity = stepFiveOverlayBinding.slEndIntensity.value.toInt()
            currentCycle.intensityLeft = intensity
            currentCycle.repeatAnswer = true
            currentCycle.cycleFinished = true
            stepFiveOverlayBinding.slEndIntensity.value = 0f
            viewModel.finishStepFive(currentCycle)
            stepFiveOverlayBinding.overlayStepFive.fadeOut(200)

        }

        stepFiveOverlayBinding.btnComplete.setOnClickListener {
            val intensity = stepFiveOverlayBinding.slEndIntensity.value.toInt()
            currentCycle.intensityLeft = intensity
            currentCycle.cycleFinished = true
            stepFiveOverlayBinding.slEndIntensity.value = 0f
            viewModel.finishStepFive(currentCycle)
            stepFiveOverlayBinding.overlayStepFive.fadeOut(200)
            binding.cvOverlay.fadeOut(200)

        }

    }

    override fun onPause() {
        super.onPause()
        viewModel.closeSession()
        binding.cvOverlay.visibility = GONE
        fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.visibility = GONE
        stepOneOverlayBinding.overlayStepOne.visibility = GONE
        stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.visibility = GONE
        stepFourOverlayBinding.overlayStepFour.visibility = GONE
        stepFiveOverlayBinding.overlayStepFive.visibility = GONE

    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.closeSession()
        binding.cvOverlay.visibility = GONE
        fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.visibility = GONE
        stepOneOverlayBinding.overlayStepOne.visibility = GONE
        stepTwoAndThreeOverlayBinding.overlayStepTwoAndThree.visibility = GONE
        stepFourOverlayBinding.overlayStepFour.visibility = GONE
        stepFiveOverlayBinding.overlayStepFive.visibility = GONE

    }

}