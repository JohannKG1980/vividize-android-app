package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.vividize_unleashyourself.feature_vms.ApiStatus
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.adapter.FiveStepsAdapter
import com.example.vividize_unleashyourself.adapter.MeditationsAdapter
import com.example.vividize_unleashyourself.data.model.FiveStepsSession
import com.example.vividize_unleashyourself.data.model.MeditationSession
import com.example.vividize_unleashyourself.databinding.FragmentHomeBinding
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur
import kotlinx.coroutines.flow.collectLatest

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()
    private lateinit var meditationChart: BarChart
    private lateinit var fiveStepChart: LineChart

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        val animation1 = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.fade
        )
        sharedElementEnterTransition = animation
        sharedElementReturnTransition = animation


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentHomeBinding.inflate(layoutInflater)

        return binding.root
    }


    // @RequiresApi(Build.VERSION_CODES.S)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //  binding.blurView1.setRenderEffect(RenderEffect.createBlurEffect(10F, 10F, Shader.TileMode.DECAL))


        binding.blurView1.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.ivHomeBg.drawable) // Optional
            .setBlurRadius(8f)
        binding.blurView2.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.ivHomeBg.drawable) // Optional
            .setBlurRadius(8f)
        binding.blurView3.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.ivHomeBg.drawable) // Optional
            .setBlurRadius(8f)
        binding.blurView3.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.ivHomeBg.drawable) // Optional
            .setBlurRadius(8f)
        binding.blurView4.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.ivHomeBg.drawable) // Optional
            .setBlurRadius(8f)
        binding.blurView5.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.ivHomeBg.drawable) // Optional
            .setBlurRadius(8f)
        meditationChart = binding.chartMeditations
        fiveStepChart = binding.chartFiveSteps
        addObserver()


        binding.cvQuote.setOnClickListener {
            val extras = FragmentNavigatorExtras(binding.cvQuote to "quote_card_fullscreen", binding.ivHomeBg to "bg_img_quote")
            findNavController().navigate(R.id.fullscreenFragment, null,null,extras)
        }




    }

    private fun addObserver() {
        viewModel.todaysQuote.observe(viewLifecycleOwner) {
            binding.tvAuthor.text = it.Author
            binding.tvQuote.text = it.quote_de
//            val pageBg = it.bg_img_url.toUri().buildUpon().scheme("https").build()
//            binding.ivHomeBg.load(pageBg) {
//                error(R.drawable.taosit_temple)
//                allowHardware(false)
//            }

            val authorImg = it.aut_img_url.toUri().buildUpon().scheme("https").build()
            binding.ivAuthor.load(authorImg) {
                error(R.drawable.taosit_temple)
//                allowHardware(false)
            }

        }
        viewModel.loading.observe(viewLifecycleOwner) { loading ->
            when (loading){
                ApiStatus.LOADING -> binding.progressBar.visibility = View.VISIBLE
                ApiStatus.DONE -> binding.progressBar.visibility = View.GONE
                ApiStatus.ERROR -> {
                    binding.progressBar.visibility = View.GONE
//                    binding.errorImage.visibility = View.VISIBLE
                }
            }
        }
        lifecycleScope.launchWhenStarted {
            viewModel.meditationSessions.collectLatest {
                updateMeditationChart(it)
                meditationChart.isClickable = false
            }

        }
        viewModel.fiveStepSessions.observe(viewLifecycleOwner) { sessions ->

            updateFiveStepChart(sessions)

        }


    }

    private fun updateMeditationChart(sessions: List<MeditationSession>) {
        meditationChart.xAxis.setDrawLabels(false)
        meditationChart.axisLeft.setDrawLabels(false)
        meditationChart.axisRight.setDrawLabels(false)

        val entries = mutableListOf<BarEntry>()
        sessions.forEachIndexed { index, session ->
            val barEntry = BarEntry(index.toFloat(), session.moodEnd.toFloat())
            entries.add(barEntry)
        }
        val barDataSet = BarDataSet(entries, "Sessions")
        val barData = BarData(barDataSet)
        meditationChart.data = barData
        meditationChart.invalidate()
    }

    private fun updateFiveStepChart(sessions: List<FiveStepsSession>) {
        fiveStepChart.xAxis.setDrawLabels(false)
        fiveStepChart.axisLeft.setDrawLabels(false)
        fiveStepChart.axisRight.setDrawLabels(false)

        val entries = mutableListOf<Entry>()
        sessions.forEachIndexed { index, session ->
            val lineEntry = Entry(index.toFloat(), session.stepCycles.size.toFloat())
            entries.add(lineEntry)
        }
        val lineDataSet = LineDataSet(entries, "Sessions")
        val lineData = LineData(lineDataSet)
        fiveStepChart.data = lineData
        fiveStepChart.invalidate()
    }
}