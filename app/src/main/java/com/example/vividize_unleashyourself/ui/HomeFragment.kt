package com.example.vividize_unleashyourself.ui

import android.graphics.RenderEffect
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.databinding.ActivityMainBinding
import com.example.vividize_unleashyourself.databinding.FragmentHomeBinding
import eightbitlab.com.blurview.BlurView
import eightbitlab.com.blurview.RenderEffectBlur
import eightbitlab.com.blurview.RenderScriptBlur

class HomeFragment : Fragment() {

    private lateinit var binding: FragmentHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
    }
}