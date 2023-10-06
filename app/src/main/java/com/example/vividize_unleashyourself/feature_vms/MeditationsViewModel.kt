package com.example.vividize_unleashyourself.feature_vms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.vividize_unleashyourself.data.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MeditationsViewModel  @Inject constructor(private val repository: AppRepository, application: Application) : AndroidViewModel(application) {





}