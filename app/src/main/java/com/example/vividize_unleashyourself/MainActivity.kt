package com.example.vividize_unleashyourself

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.graphics.Rect
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.BounceInterpolator
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.databinding.ActivityMainBinding
import com.example.vividize_unleashyourself.feature_vms.FiveStepsViewModel
import com.google.android.material.bottomnavigation.BottomNavigationView
import eightbitlab.com.blurview.RenderScriptBlur

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private lateinit var motionLayout: MotionLayout

    private lateinit var rotateOpenFab: ObjectAnimator
    private lateinit var rotateCloseFab: ObjectAnimator
    private val mainViewModel: MainViewModel by viewModels()
    private val fiveStepsViewModel: FiveStepsViewModel by viewModels()

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.blurViewTopBar.setupWith(binding.root, RenderScriptBlur(this))
            .setBlurRadius(3f)
        binding.blurViewAddDialog.setupWith(binding.root, RenderScriptBlur(this))
            .setBlurRadius(3f)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.background = null
//        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        val navView: BottomNavigationView = binding.bottomNavigationView

        navView.setupWithNavController(navController)
        motionLayout = binding.clBottomDialog
        rotateOpenFab = ObjectAnimator.ofFloat(binding.fab, "rotation", 0f, 45f)
        rotateCloseFab = ObjectAnimator.ofFloat(binding.fab, "rotation", 45f, 0f)
        rotateOpenFab.duration = 700
        rotateCloseFab.duration = 700
        rotateOpenFab.interpolator = BounceInterpolator()
        rotateCloseFab.interpolator = BounceInterpolator()

        val gestureDetector =
            GestureDetector(this, object : GestureDetector.SimpleOnGestureListener() {
                override fun onSingleTapUp(e: MotionEvent): Boolean {
                    return true
                }

                override fun onDown(e: MotionEvent): Boolean {
                    return false
                }
            })

        binding.clBottomDialog.setOnTouchListener { _, event ->
            if (gestureDetector.onTouchEvent(event)) {
                val rect = Rect()
                binding.cvFabDialog.getGlobalVisibleRect(rect)
                if (!rect.contains(event.rawX.toInt(), event.rawY.toInt())) {
                    if (binding.clBottomDialog.currentState == R.id.end) {
                        binding.clBottomDialog.transitionToStart()
                    }
                }
                true
            } else {
                false
            }
        }


        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                if (endId == R.id.start) {
                    rotateCloseFab.start()
                    binding.clBottomDialog.visibility = GONE

                }
            }

            override fun onTransitionChange(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int,
                progress: Float
            ) {

            }

            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                if (currentId == R.id.start) {
                    rotateCloseFab.start()
                    binding.clBottomDialog.visibility = GONE

                }
            }

            override fun onTransitionTrigger(
                motionLayout: MotionLayout?,
                triggerId: Int,
                positive: Boolean,
                progress: Float
            ) {

            }
        })
        binding.fab.setOnClickListener {
            motionControl()

        }



        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fullscreenFragment -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                    binding.topBar.visibility = View.GONE

                }

                R.id.logInFragment -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                }

                R.id.signUpFragment -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                }

                R.id.forgotPassFragment -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                }

                R.id.splashFragment -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                    binding.topBar.visibility = View.GONE
                }

                R.id.mentalSectionFragment -> {
                    binding.tvPageTitle.text = destination.label
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                    binding.topBar.visibility = View.VISIBLE
                    binding.ibAddFiveStep.setOnClickListener {
                        motionControl()

                        mainViewModel.controlQuickstart(1)
                        fiveStepsViewModel.openSession()


                    }


                }

                else -> {
                    binding.tvPageTitle.text = destination.label
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                    binding.topBar.visibility = View.VISIBLE
                    binding.ibAddFiveStep.setOnClickListener {
                        motionControl()


                        navController.navigate(R.id.mentalSectionFragment)
                        mainViewModel.controlQuickstart(1)
                        fiveStepsViewModel.openSession()


                    }

                }
            }
        }
    }

    private fun motionControl() {

        if (motionLayout.currentState == R.id.start) {
            binding.clBottomDialog.visibility = VISIBLE
            motionLayout.transitionToEnd()
            rotateOpenFab.start()
        } else {
            motionLayout.transitionToStart()

            binding.clBottomDialog.setTransitionListener(object :
                MotionLayout.TransitionListener {
                override fun onTransitionStarted(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int
                ) {

                }

                override fun onTransitionChange(
                    motionLayout: MotionLayout?,
                    startId: Int,
                    endId: Int,
                    progress: Float
                ) {

                }


                override fun onTransitionCompleted(
                    motionLayout: MotionLayout?,
                    currentId: Int
                ) {

                    if (currentId == R.id.start) {
                        rotateCloseFab.start()
                        binding.clBottomDialog.visibility = GONE
                    }
                }

                override fun onTransitionTrigger(
                    motionLayout: MotionLayout?,
                    triggerId: Int,
                    positive: Boolean,
                    progress: Float
                ) {

                }

            })
        }
    }
}