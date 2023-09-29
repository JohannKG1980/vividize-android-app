package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.viewpager2.widget.ViewPager2
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.adapter.MentalSectionsAdapter
import com.example.vividize_unleashyourself.databinding.FragmentMentalSectionBinding
import com.google.android.material.tabs.TabLayout
import eightbitlab.com.blurview.RenderScriptBlur

class MentalSectionFragment : Fragment() {
    private lateinit var binding: FragmentMentalSectionBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var tabLayout: TabLayout
    private lateinit var viewPager2: ViewPager2
    private lateinit var adapter: MentalSectionsAdapter
    private var isUserInteracted = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMentalSectionBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.blurView1.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.ivBg.drawable) // Optional
            .setBlurRadius(3f)

        binding.blurView2.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.ivBg.drawable) // Optional
            .setBlurRadius(3f)
        tabInitiator()
        tabController()
        quickstartHelper()

    }

    private fun tabInitiator() {
        tabLayout = binding.tlSpiritLab
        viewPager2 = binding.vp2Library
        adapter = MentalSectionsAdapter(childFragmentManager, lifecycle, binding, viewModel)
        tabLayout.addTab(tabLayout.newTab().setText(R.string.meditations))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.fiveSteps))
        tabLayout.addTab(tabLayout.newTab().setText(R.string.journaling))
        viewPager2.adapter = adapter
    }

    private fun tabController() {

        tabLayout.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener {
            override fun onTabSelected(tab: TabLayout.Tab?) {
                if (tab != null) {

                    viewPager2.currentItem = tab.position

                }
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabReselected(tab: TabLayout.Tab?) {
            }
        })

        viewPager2.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {

            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                if (isUserInteracted)
                    tabLayout.selectTab(tabLayout.getTabAt(position))
            }
        })

    }

    private fun quickstartHelper() {
        viewModel.mentalStartTab.observe(viewLifecycleOwner) { startTab ->
            viewModel.quickStart.observe(viewLifecycleOwner) { isQuickStart ->

                if (startTab != null && isQuickStart) {
                    isUserInteracted = false // Setzen Sie dies auf false, bevor Sie programmatisch wechseln
                    viewPager2.post {
                        viewPager2.currentItem = startTab
                    }
                    isUserInteracted =
                         true // Setzen Sie dies zurück auf true, nachdem Sie gewechselt haben

                }

            }
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.controlQuickstart(null, false)
    }
}
