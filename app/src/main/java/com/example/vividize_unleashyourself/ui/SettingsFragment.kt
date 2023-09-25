package com.example.vividize_unleashyourself.ui

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.vividize_unleashyourself.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}