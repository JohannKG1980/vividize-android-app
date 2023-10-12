package com.example.vividize_unleashyourself.extensions

import android.animation.Animator
import android.animation.ObjectAnimator
import android.util.Log
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.example.vividize_unleashyourself.R

fun View.slideUp(
    animTime: Long,
    startOffset: Long
) {
    val slideUp = AnimationUtils.loadAnimation(context, R.anim.slide_up).apply {
        duration = animTime
        interpolator = FastOutSlowInInterpolator()
        this.startOffset = startOffset
        setAnimationListener(object : Animation.AnimationListener {
            override fun onAnimationStart(animation: Animation?) {
                Log.d("AnimationDebug", "slideUp animation started")
                visibility = VISIBLE
            }

            override fun onAnimationEnd(animation: Animation?) {
                Log.d("AnimationDebug", "slideUp animation ended")

            }

            override fun onAnimationRepeat(animation: Animation?) {
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

fun View.fadeIn(duration: Long = 500) {
    val fadeIn = ObjectAnimator.ofFloat(this, "alpha", 0f, 1f)
    fadeIn.duration = duration
    fadeIn.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator) {

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

fun View.fadeOut(duration: Long = 500) {
    val fadeOut = ObjectAnimator.ofFloat(this, "alpha", 1f, 0f)
    fadeOut.duration = duration
    fadeOut.addListener(object : Animator.AnimatorListener {
        override fun onAnimationStart(p0: Animator) {}
        override fun onAnimationEnd(p0: Animator) {
            visibility = GONE
        }

        override fun onAnimationCancel(p0: Animator) {

        }

        override fun onAnimationRepeat(p0: Animator) {

        }


    })
    fadeOut.start()
}