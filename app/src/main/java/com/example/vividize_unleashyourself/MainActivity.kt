package com.example.vividize_unleashyourself

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.vividize_unleashyourself.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.bottomNavigationView.background = null
    }
}