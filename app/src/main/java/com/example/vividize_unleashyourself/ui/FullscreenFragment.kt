package com.example.vividize_unleashyourself.ui

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.core.view.ViewCompat.LAYER_TYPE_SOFTWARE
import androidx.core.view.ViewCompat.setLayerType
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.vividize_unleashyourself.feature_vms.ApiStatus
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.databinding.FragmentFullscreenBinding
import com.example.vividize_unleashyourself.extensions.slideUp
import dagger.hilt.android.AndroidEntryPoint
import eightbitlab.com.blurview.RenderScriptBlur

@AndroidEntryPoint
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
        }, 1000)

        Handler(Looper.getMainLooper()).postDelayed({
            val motionLayout = binding.constraintLayout4
            motionLayout.transitionToEnd()
        }, 2000)


    }

    private fun addObserver() {
        viewModel.todaysQuote.observe(viewLifecycleOwner) {


            val pageBg = it.bg_img_url.toUri().buildUpon().scheme("https").build()
            binding.ivHomeBg.load(pageBg) {
                error(R.drawable.taosit_temple)
                allowHardware(false)
            }


                binding.tvQuote.text = it.quote_de
            binding.tvQuote.visibility = View.INVISIBLE

            Handler(Looper.getMainLooper()).postDelayed({
                binding.tvQuote.visibility = View.VISIBLE
                binding.tvQuote.slideUp(1600L, 0L)
            },600)

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