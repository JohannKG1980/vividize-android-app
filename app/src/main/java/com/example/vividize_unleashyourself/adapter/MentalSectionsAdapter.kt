package com.example.vividize_unleashyourself.adapter

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


    override fun createFragment(position: Int): Fragment {
       lateinit var currentTab :  Fragment


            when (position) {
                0 -> currentTab = MeditationsFragment()
                1 -> currentTab = FiveStepsFragment(blurViewRoot, true)
                2 -> currentTab = JournalsFragment()




            }

        return currentTab
    }
}