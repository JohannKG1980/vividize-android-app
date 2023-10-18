package com.example.vividize_unleashyourself.feature_vms

import android.app.Application
import android.media.MediaPlayer
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.vividize_unleashyourself.R
import com.example.vividize_unleashyourself.data.AppRepository
import com.example.vividize_unleashyourself.data.model.FiveSteps
import com.example.vividize_unleashyourself.data.model.FiveStepsSession
import com.example.vividize_unleashyourself.data.model.Question
import com.example.vividize_unleashyourself.data.remote.QuotesApi
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


enum class CurrentStep { NO_CYCLE_NOW, DESCRIPTION, DESCRIPTION_FIRST, STEP_ONE, STEP_TWO, STEP_THREE, STEP_THREE_ADD, STEP_FOUR, STEP_FIVE }

@HiltViewModel
class FiveStepsViewModel @Inject constructor(
    private val repository: AppRepository,
    private val mediaPlayer: MediaPlayer,
    application: Application,
) : AndroidViewModel(application) {

    val allSessions = repository.fiveStepsSessions
    private val _instructionWatched = MutableLiveData<Boolean>(false)
    val instructionWatched: LiveData<Boolean>
        get() = _instructionWatched

    private val _currentCycle = MutableLiveData<FiveSteps>()
    val currentCycle: LiveData<FiveSteps>
        get() = _currentCycle

    private val _currentStep = MutableLiveData<CurrentStep>(CurrentStep.NO_CYCLE_NOW)
    val currentStep: LiveData<CurrentStep>
        get() = _currentStep


    private val _currentSession = MutableLiveData<FiveStepsSession?>()
    val currentSession: LiveData<FiveStepsSession?>
        get() = _currentSession

    val stepOne = Question(R.string.stepOne)
    val stepOneRepeat = Question(R.string.stepOneRepeat)
    val stepTwoInstruct = Question(R.string.stepTwoGeneral)
    private val stepTwo = listOf(
        Question(R.string.stepTwo1),
        Question(R.string.stepTwo2),
        Question(R.string.stepTwo3)
    )
    var stepTwoQuestion = stepTwo.random()

    private val stepThree = listOf(
        Question(R.string.stepThree1),
        Question(R.string.stepThree2)
    )
    var stepThreeQuestion = stepThree.random()

    val stepThreeFollowUp = Question(R.string.stepThreeNo)
    val stepFourQuestion = Question(R.string.stepFour)
    val stepFive = Question(R.string.stepFive)
    val repeatQuestion = Question(R.string.askForRepeat)

    fun getStepTwoQuest() {
        stepTwoQuestion = stepTwo.random()
    }

    fun getStepThreeQuest() {

        stepThreeQuestion = stepThree.random()
    }

    fun descriptionWatched() {
        _instructionWatched.value = true
        _instructionWatched.value = _instructionWatched.value

    }

    fun openInstructions() {
        _currentStep.value = CurrentStep.DESCRIPTION
        _currentStep.value = _currentStep.value
    }

    fun changeToStepOne() {

        _currentStep.value = CurrentStep.STEP_ONE
        _currentStep.value = _currentStep.value
    }

    fun closeSession() {
        _currentStep.value = CurrentStep.NO_CYCLE_NOW
        _currentStep.value = _currentStep.value
        _currentSession.value = null
    }

    fun openSession() {
        if (!_instructionWatched.value!!) {
            _currentStep.value = CurrentStep.DESCRIPTION_FIRST
            _currentStep.value = _currentStep.value
        } else {
            _currentStep.value = CurrentStep.STEP_ONE
            _currentStep.value = _currentStep.value
        }
        if (_currentSession.value == null)
            _currentSession.value = FiveStepsSession()
        if (_currentSession.value != null && _currentSession.value!!.sessionFinished) {
                _currentSession.value = FiveStepsSession()

                _currentSession.value!!.stepCycles.add(FiveSteps())

        } else {

            _currentSession.value!!.stepCycles.add(FiveSteps())


            _currentCycle.postValue(_currentSession.value!!.stepCycles.last())
        }
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

    fun finishStepFive(finishedCycle: FiveSteps) {
        _currentCycle.value = finishedCycle
        _currentCycle.value = _currentCycle.value
        if (finishedCycle.cycleFinished && !finishedCycle.repeatAnswer) {
            _currentStep.postValue(CurrentStep.NO_CYCLE_NOW)
            saveFinishedSession()
        } else if (finishedCycle.repeatAnswer && finishedCycle.cycleFinished) {
            _currentStep.value = CurrentStep.STEP_ONE
            _currentStep.value = _currentStep.value
            _currentSession.value!!.stepCycles.add(_currentCycle.value!!)
            _currentCycle.postValue(FiveSteps(_currentSession.value!!.stepCycles.last().cycleId + 1))
        }

    }


    private fun saveFinishedSession() {
        if (_currentSession.value != null)
            repository.addFiveStepSession(_currentSession.value!!)
        _currentSession.value = null
    }

    fun removeSession(session: FiveStepsSession) {
            repository.removeFiveStepSession(session)
    }

}