package com.example.vividize_unleashyourself.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.vividize_unleashyourself.ui.JournalsFragment
import com.example.vividize_unleashyourself.ui.MeditationsFragment

class MentalSectionsAdapter(fragmentManager: FragmentManager, lifecycle: Lifecycle) : FragmentStateAdapter(fragmentManager, lifecycle) {
    override fun getItemCount(): Int {
        return  2
    }

    override fun createFragment(position: Int): Fragment {
      return  if(position == 0)
           MeditationsFragment()
        else
           JournalsFragment()
     }
}