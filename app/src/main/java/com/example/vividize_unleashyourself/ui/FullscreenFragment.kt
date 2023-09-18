package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.vividize_unleashyourself.feature_vms.ApiStatus
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.databinding.FragmentFullscreenBinding
import eightbitlab.com.blurview.RenderScriptBlur


class FullscreenFragment : Fragment() {
    private lateinit var binding: FragmentFullscreenBinding
    private val viewModel: MainViewModel by activityViewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val animation1 = TransitionInflater.from(requireContext()).inflateTransition(
            android.R.transition.move
        )
        sharedElementEnterTransition = animation1
        sharedElementReturnTransition = animation1


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentFullscreenBinding.inflate(layoutInflater)

        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        binding.ivHomeBg.setLayerType(View.LAYER_TYPE_HARDWARE, null)
//        binding.ivAuthor.setLayerType(View.LAYER_TYPE_HARDWARE, null)


        binding.blurView1.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.ivHomeBg.drawable) // Optional
            .setBlurRadius(3f)

        addObserver()

        Handler(Looper.getMainLooper()).postDelayed({
            val motionLayout = binding.constraintLayout2
            motionLayout.transitionToEnd()
        }, 2000)

        Handler(Looper.getMainLooper()).postDelayed({
            val motionLayout = binding.constraintLayout4
            motionLayout.transitionToEnd()
        }, 3000)


    }

    private fun addObserver() {
        viewModel.todaysQuote.observe(viewLifecycleOwner) {


            val pageBg = it.bg_img_url.toUri().buildUpon().scheme("https").build()
            binding.ivHomeBg.load(pageBg) {
                error(R.drawable.taosit_temple)
                allowHardware(false)
            }
            view?.post {
                val quote = it.quote_de
                val stringBuilder = StringBuilder()
                Thread {
                    for (letter in quote) {
                        stringBuilder.append(letter)
                        Thread.sleep(70)

                        activity?.runOnUiThread {
                            binding.tvQuote.text = stringBuilder.toString()
                        }
                    }
                }.start()

            }

            val authorImg = it.aut_img_url.toUri().buildUpon().scheme("https").build()
            binding.ivAuthor.load(authorImg) {
                error(R.drawable.taosit_temple)
                allowHardware(false)
            }

            binding.tvAuthor.text = it.Author

            binding.cvQuoteStyle.setOnClickListener {
                val extras = FragmentNavigatorExtras(binding.cvQuoteStyle to "quote_card_home", binding.ivHomeBg to "bg_img_home")
                findNavController().navigate(R.id.action_fullscreenFragment_to_homeFragment, null, null, extras)
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
    }

}