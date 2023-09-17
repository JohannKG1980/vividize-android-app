package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.vividize_unleashyourself.vms.MainViewModel
import com.example.vividize_unleashyourself.databinding.FragmentMeditationsBinding

class MeditationsFragment : Fragment() {
    private lateinit var binding: FragmentMeditationsBinding
    private val viewModel: MainViewModel by activityViewModels()



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMeditationsBinding.inflate(layoutInflater)
        return binding.root


    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        val tabHostBinding = FragmentMentalSectionBinding.inflate(layoutInflater)
//            binding.blurView1.setupWith(binding.root, RenderScriptBlur(requireContext()))
//                .setBlurAutoUpdate(true)
//                .setBlurRadius(3f)
//                //.setFrameClearDrawable(tabHostBinding.ivBg.drawable) // Optional

    }

}
