package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import com.example.vividize_unleashyourself.vms.MainViewModel
import com.example.vividize_unleashyourself.databinding.FragmentJournalsBinding


class JournalsFragment : Fragment() {
    private lateinit var binding: FragmentJournalsBinding
    private val viewModel: MainViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentJournalsBinding.inflate(layoutInflater)
        return binding.root
    }

}