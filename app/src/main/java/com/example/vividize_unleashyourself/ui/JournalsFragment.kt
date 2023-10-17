package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.postDelayed
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.adapter.JournalEntriesAdapter
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.databinding.FragmentJournalsBinding
import com.example.vividize_unleashyourself.extensions.setButtonEffect
import com.example.vividize_unleashyourself.feature_vms.JournalingViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class JournalsFragment : Fragment() {
    private lateinit var binding: FragmentJournalsBinding
    private val viewModel: JournalingViewModel by activityViewModels()
    private val MainViewModel: MainViewModel by activityViewModels()


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

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.ivAddEntry.setOnClickListener {
            it.setButtonEffect()
            viewModel.newEntry()
            Handler(Looper.getMainLooper()).postDelayed({
            findNavController().navigate(R.id.journalEntryFragment)
            }, 300)
        }
        addObserver()
    }

    private fun addObserver(){
        lifecycleScope.launchWhenStarted {
            viewModel.allEntries.collectLatest { allEntries ->

            binding.rvJournalEntries.adapter = JournalEntriesAdapter(requireContext(),allEntries,viewModel)

            }
        }

    }

}