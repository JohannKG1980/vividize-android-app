package com.example.vividize_unleashyourself.feature_vms

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.model.FiveSteps
import com.example.vividize_unleashyourself.data.model.FiveStepsSession
import com.example.vividize_unleashyourself.data.remote.QuotesApi


enum class CurrentStep { NO_CYCLE_NOW, DESCRIPTION, STEP_ONE, STEP_TWO, STEP_THREE, STEP_THREE_ADD, STEP_FOUR, STEP_FIVE }

class FiveStepsViewModel(application: Application) : AndroidViewModel(application) {

    private val _instructionWatched = MutableLiveData<Boolean>(false)
    val instructionWatched: LiveData<Boolean>
        get() = _instructionWatched

    private val _currentCycle = MutableLiveData<FiveSteps>()
    val currentCycle: LiveData<FiveSteps>
        get() = _currentCycle

    private val _currentStep = MutableLiveData<CurrentStep>(CurrentStep.NO_CYCLE_NOW)
    val currentStep: LiveData<CurrentStep>
        get() = _currentStep

    private val repository = AppRepository(QuotesApi)
    val allSessions = repository.fiveStepsSessions

    private val _currentSession = MutableLiveData<FiveStepsSession>()
    val currentSession: LiveData<FiveStepsSession>
        get() = _currentSession

    fun despriptionWatched() {
        _instructionWatched.value = true
        _instructionWatched.value = _instructionWatched.value
    }

    fun openSession() {
        if (!_instructionWatched.value!!) {
            _currentStep.value = CurrentStep.DESCRIPTION
            _currentStep.value = _currentStep.value
        } else {
            _currentStep.value = CurrentStep.STEP_ONE
            _currentStep.value = _currentStep.value
        }
        _currentSession.value = FiveStepsSession()
        _currentSession.value!!.stepCycles.add(FiveSteps())
        _currentCycle.postValue(_currentSession.value!!.stepCycles.last())
    }

    fun finishStepOne(topic: String, intensity: Int) {
        _currentCycle.value!!.stepOneInput = topic
        _currentCycle.value!!.intensity = intensity
        _currentStep.postValue(CurrentStep.STEP_TWO)

    }

    fun finishStepTwo(answer: Boolean) {
        _currentCycle.value!!.stepTwoAnswer = answer
        _currentStep.postValue(CurrentStep.STEP_THREE)

    }

    fun finishStepThree(answer: Boolean) {
        _currentCycle.value!!.stepThreeAnswer = answer
        if (!answer) {
            _currentStep.postValue(CurrentStep.STEP_THREE_ADD)
        } else {
            _currentStep.postValue(CurrentStep.STEP_FOUR)
        }

    }

    fun finishStepThreeAdd(answer: Boolean) {
        _currentCycle.value!!.stepThreeBetterBeFree = answer

        _currentStep.postValue(CurrentStep.STEP_FOUR)

    }

    fun finishStepFour(answer: String) {
        _currentCycle.value!!.stepFourAnswer = answer

        _currentStep.postValue(CurrentStep.STEP_FIVE)

    }

    fun finishStepFive(answer: Boolean) {
        _currentCycle.value!!.repeatAnswer = answer
        if (!answer) {
            _currentStep.postValue(CurrentStep.NO_CYCLE_NOW)
        } else {
            _currentSession.value!!.stepCycles.add(FiveSteps())
            _currentCycle.postValue(_currentSession.value!!.stepCycles.last())
            _currentStep.postValue(CurrentStep.STEP_ONE)
        }

    }


    fun saveFinishedSession() {
        _currentStep.postValue(CurrentStep.STEP_ONE)
        if (currentSession.value != null)
            repository.addFiveStepSession(currentSession.value!!)
    }

    fun removeSession() {
        if (currentSession.value != null)
            repository.removeFiveStepSession(currentSession.value!!)
    }

}