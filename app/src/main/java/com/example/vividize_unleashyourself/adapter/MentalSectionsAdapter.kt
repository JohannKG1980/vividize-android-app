package com.example.vividize_unleashyourself.adapter

import android.annotation.SuppressLint
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vividize_unleashyourself.databinding.FragmentMentalSectionBinding
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.ui.FiveStepsFragment
import com.example.vividize_unleashyourself.ui.JournalsFragment
import com.example.vividize_unleashyourself.ui.MeditationsFragment

class MentalSectionsAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle,
    val blurViewRoot: FragmentMentalSectionBinding,
    val viewModel: MainViewModel
) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return 3
    }


    @SuppressLint("SuspiciousIndentation")
    override fun createFragment(position: Int): Fragment {
        lateinit var currentTab: Fragment
        var quickStart = false
        viewModel.quickStart.observeForever {
            quickStart = it
        }


            when (position) {
                0 -> currentTab = MeditationsFragment()
                1 -> currentTab = FiveStepsFragment(blurViewRoot, quickStart)
                2 -> currentTab = JournalsFragment()


            }


        return currentTab
    }
}