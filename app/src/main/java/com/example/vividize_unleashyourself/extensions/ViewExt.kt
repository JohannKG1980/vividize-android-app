package com.example.vividize_unleashyourself.extensions

import android.animation.Animator
import android.animation.ArgbEvaluator
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.Color
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.transition.AutoTransition
import android.transition.TransitionManager
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.animation.AnticipateOvershootInterpolator
import androidx.core.content.ContextCompat
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.vividize_unleashyourself.R
import com.google.android.material.bottomnavigation.BottomNavigationView

fun View.slideUp(
    animTime: Long,
    startOffset: Long,
    makeVisible: Boolean = true,
) {
    val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                Log.d("AnimationDebug", "slideUp animation started")
                if (makeVisible)
                    visibility = VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                Log.d("AnimationDebug", "slideUp animation ended")
            }

            override fun onAnimationRepeat(animation: Animation?) {
                if (makeVisible)
                    fadeOut(100)
            }
        })
    }
    Log.d("AnimationDebug", "Starting slideUp animation")
    postDelayed({
        startAnimation(slideUp)
    }, 100)
}

fun View.slideOutDown(animTime: Long, startOffset: Long, onAnimationEnd: (() -> Unit)? = null) {
    val slideOutDown = AnimationUtils.loadAnimation(context, R.anim.slide_out_down).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {
                visibility = GONE
            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
    }
    postDelayed({
        startAnimation(slideOutDown)
    }, 100)
}

fun View.slideOutUp(animTime: Long, startOffset: Long) {
    val slideOutUp = AnimationUtils.loadAnimation(context, R.anim.slide_out_up).apply {
        duration = animTime
        interpolator = AnticipateOvershootInterpolator()
        this.startOffset = startOffset
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {

            }

            override fun onAnimationEnd(animation: Animation?) {

            }

            override fun onAnimationRepeat(animation: Animation?) {
            }
        })
    }
    postDelayed({
        startAnimation(slideOutUp)
    }, 100)
}

fun View.fadeIn(duration: Long = 500, makeVisible: Boolean = true) {
    val fadeIn = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
    fadeIn.duration = duration
    fadeIn.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator) {
            if (makeVisible)
                visibility = VISIBLE
        }

        override fun onAnimationEnd(p0: Animator) {
        }

        override fun onAnimationCancel(p0: Animator) {
        }

        override fun onAnimationRepeat(p0: Animator) {
        }


    })
    fadeIn.start()
}

fun View.fadeOut(duration: Long = 500, makeItGone: Boolean = true) {
    val fadeOut = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
    fadeOut.duration = duration
    fadeOut.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator) {}
        override fun onAnimationEnd(p0: Animator) {
            if (makeItGone)
                visibility = GONE
        }

        override fun onAnimationCancel(p0: Animator) {

        }

        override fun onAnimationRepeat(p0: Animator) {

        }


    })
    fadeOut.start()
}

fun BottomNavigationView.setFadeEnabled(enabled: Boolean) {
    val alphaValue = if (enabled) 1f else 0.5f
    this.animate().alpha(alphaValue).setDuration(900).start()

    this.isEnabled = enabled
}


fun View.setFadeEnabled(enabled: Boolean) {
    val alphaValue = if (enabled) 1f else 0.5f
    this.animate().alpha(alphaValue).setDuration(900).start()

    this.isEnabled = enabled
}

fun View.setButtonEffect(scaleFactor: Float = 0.9f, duration: Long = 100) {

      val bouncer =  this.animate()
            .scaleX(scaleFactor)
            .scaleY(scaleFactor)
            .setDuration(duration)
            .withEndAction {
                this.animate()
                    .scaleX(1f)
                    .scaleY(1f)
                    .setDuration(duration)
                    .start()
            }
    postDelayed({
           bouncer.start()
    }, 100)
}



