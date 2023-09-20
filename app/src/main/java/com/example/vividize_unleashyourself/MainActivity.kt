package com.example.vividize_unleashyourself

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.AccelerateDecelerateInterpolator
import androidx.activity.viewModels
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.databinding.ActivityMainBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import eightbitlab.com.blurview.RenderScriptBlur

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController
    private val viewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.blurView1.setupWith(binding.root, RenderScriptBlur(this))
            .setBlurRadius(3f)
        binding.blurViewAddDialog.setupWith(binding.root, RenderScriptBlur(this))
            .setBlurRadius(3f)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(2).isEnabled = false

        val navView: BottomNavigationView = binding.bottomNavigationView

        navView.setupWithNavController(navController)

        val motionLayout = binding.clBottomDialog

        val rotateOpenAnim = ObjectAnimator.ofFloat(binding.fab, "rotation", 0f, 45f)
        val rotateCloseAnim = ObjectAnimator.ofFloat(binding.fab, "rotation", 45f, 0f)
        rotateOpenAnim.duration = 300 // Dauer der Animation
        rotateCloseAnim.duration = 300
        rotateOpenAnim.interpolator = AccelerateDecelerateInterpolator() // Interpolator für die Animation
        rotateCloseAnim.interpolator = AccelerateDecelerateInterpolator()

        motionLayout.setTransitionListener(object : MotionLayout.TransitionListener {
            override fun onTransitionStarted(
                motionLayout: MotionLayout?,
                startId: Int,
                endId: Int
            ) {
                if (endId == R.id.start) {
                    rotateCloseAnim.start()
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
//                if (currentId == R.id.start) {
//                    rotateCloseAnim.start()
//                    binding.clBottomDialog.visibility = GONE
//                }
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

            if (motionLayout.currentState == R.id.start) {
                binding.clBottomDialog.visibility = VISIBLE
                motionLayout.transitionToEnd()
                rotateOpenAnim.start()
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
                            rotateCloseAnim.start()

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

        navController.addOnDestinationChangedListener { _, destination, _ ->
            when (destination.id) {
                R.id.fullscreenFragment -> {
                    binding.bottomAppBar.visibility = View.GONE
                    binding.fab.visibility = View.GONE
                    binding.blurView1.visibility = View.GONE

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
                    binding.blurView1.visibility = View.GONE
                }

                else -> {
                    binding.bottomAppBar.visibility = View.VISIBLE
                    binding.fab.visibility = View.VISIBLE
                    binding.topBar.visibility = View.VISIBLE

                }
            }
        }
    }

}