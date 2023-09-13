package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.transition.TransitionInflater
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.example.vividize_unleashyourself.MainViewModel
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


        binding.blurView1.setupWith(binding.root, RenderScriptBlur(requireContext()))
            .setFrameClearDrawable(binding.ivHomeBg.drawable) // Optional
            .setBlurRadius(3f)


        view.post {
            val quote =
                "Du musst es dir in deinem Herzen und deinem Verstand vorstellen, bevor du es empfangen kannst. Wenn du glaubst, dann sind alle Dinge möglich."

            val stringBuilder = StringBuilder()
            Thread {
                for (letter in quote) {
                    stringBuilder.append(letter)
                    Thread.sleep(10)

                    activity?.runOnUiThread {
                        binding.tvQuote.text = stringBuilder.toString()
                    }
                }
            }.start()

            binding.cvQuoteStyle.setOnClickListener {
                val extras = FragmentNavigatorExtras(binding.cvQuoteStyle to "quote_card_home")
                findNavController().navigate(R.id.homeFragment, null,null,extras)
            }
        }




        Handler(Looper.getMainLooper()).postDelayed({
            val motionLayout = binding.constraintLayout2
            motionLayout.transitionToEnd()
        }, 2000)

        Handler(Looper.getMainLooper()).postDelayed({
            val motionLayout = binding.constraintLayout4
            motionLayout.transitionToEnd()
        }, 3000)

    }


}