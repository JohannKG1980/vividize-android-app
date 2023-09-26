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
import android.widget.ScrollView
import androidx.core.text.HtmlCompat
import androidx.fragment.app.activityViewModels
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.adapter.FiveStepsAdapter
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


        addObserver()
    }

    private fun addObserver() {

        viewModel.instructionWatched.observe(viewLifecycleOwner) { instructed ->
            if (!instructed) {
                openInstructions()
            }
            viewModel.currentStep.observe(viewLifecycleOwner) { currentStep ->
                binding.ivAddSession.setOnClickListener {
                    if (instructed) {
                        when (currentStep) {
                            CurrentStep.STEP_ONE -> openStepOne()
                            else -> binding.cvOverlay.visibility = GONE


                        }

                    }
                }
            }
        }




        viewModel.allSessions.observe(viewLifecycleOwner) { sessions ->
            binding.rvFsmSessions.adapter = FiveStepsAdapter(sessions, viewModel)

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

        fiveStepsDescriptionOverlayBinding.btnNext1.setOnClickListener {
            fiveStepsDescriptionOverlayBinding.overlay5StepsDescription.visibility = GONE
            viewModel.openSession()
            openStepOne()
        }

    }

    private fun openStepOne() {
        binding.cvOverlay.visibility = VISIBLE
        stepOneOverlayBinding.overlayStepOne.visibility = VISIBLE


    }


}