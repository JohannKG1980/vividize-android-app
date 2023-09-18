package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import android.transition.TransitionInflater
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import coil.load
import com.example.vividize_unleashyourself.feature_vms.ApiStatus
import com.example.vividize_unleashyourself.feature_vms.MainViewModel
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.databinding.FragmentHomeBinding
import eightbitlab.com.blurview.RenderScriptBlur


class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding
    private val viewModel: MainViewModel by activityViewModels()

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
            .setBlurRadius(3f)

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
                allowHardware(false)
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