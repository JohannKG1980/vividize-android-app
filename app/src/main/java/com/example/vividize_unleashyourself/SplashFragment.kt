package com.example.vividize_unleashyourself

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.databinding.FragmentSplashBinding


class SplashFragment : Fragment() {
    private lateinit var binding: FragmentSplashBinding
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.fade
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentSplashBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.clSplashBg.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                viewModel.todaysQuote.observe(viewLifecycleOwner) {}
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {
                // Hier können Sie Code hinzufügen, der während der Transition ausgeführt werden soll
            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                // Überprüfen Sie, ob die aktuelle ID der Endzustand des äußeren MotionLayouts ist
                if (currentId == R.id.totalEnd) {
                    // Starten Sie die innere Animation
                    binding.clAppName.transitionToEnd()
                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {
                // Hier können Sie Code hinzufügen, der ausgeführt werden soll, wenn ein Trigger während der Transition ausgelöst wird
            }
        })


        Handler(Looper.getMainLooper()).postDelayed({
            val extras = FragmentNavigatorExtras(binding.ivLogo to "quote_card_fullscreen")
            findNavController().navigate(R.id.action_splashScreenFragment_to_fullscreenFragment, null, null, extras)

        }, 6000)
    }

}