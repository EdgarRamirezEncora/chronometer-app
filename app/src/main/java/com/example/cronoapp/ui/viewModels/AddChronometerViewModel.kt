package com.example.cronoapp.ui.viewModels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cronoapp.domain.states.ChronometerState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddChronometerViewModel @Inject constructor(): ViewModel() {
    var state by mutableStateOf(ChronometerState())
        private set

    private var chronometerJob by mutableStateOf<Job?>(null)

    var chronometerTime by mutableLongStateOf(0L)
        private set

    fun onValue(value: String) {
        state = state.copy(title = value)
    }

    fun start() {
        state = state.copy(
            activeChronometer = true,
            showSaveButton = false
        )
    }

    fun pause() {
        state = state.copy(
            activeChronometer = false,
            showSaveButton = true
        )
    }

    fun stop() {
        chronometerJob?.cancel()
        chronometerTime = 0
        state = state.copy(
            title = "",
            activeChronometer = false,
            showSaveButton = false,
            showTextField = false
        )
    }

    fun showTextField() {
        state = state.copy(
            showTextField = true
        )
    }

    fun hideTextField() {
        state = state.copy(
            showTextField = false
        )
    }

    fun chronometer() {
        if (state.activeChronometer) {
            chronometerJob?.cancel()
            chronometerJob = viewModelScope.launch {
                while(true) {
                    delay(1000)
                    chronometerTime += 1000
                }
            }
        } else {
            chronometerJob?.cancel()
        }
    }

    fun validateTitle(title: String): Boolean {
        val titleRegex = Regex("^.{1,30}$")
        return titleRegex.matches(title)
    }

    fun getAlertMessage(titleValue: String) : String = if(titleValue.isNotEmpty()) {"The title must have 30 characters max."}
                                                        else {"The title is required."}

    fun showTitleAlert() {
        state = state.copy(isValidTitle = false)
    }

    fun closeTitleAlert() {
        state = state.copy(isValidTitle = true)
    }
}