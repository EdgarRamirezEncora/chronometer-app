package com.example.cronoapp.ui.viewModels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cronoapp.data.entities.Chronometer
import com.example.cronoapp.domain.repositories.ChronometerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChronometerViewModel @Inject constructor(
    private val chronometerRepository: ChronometerRepository
): ViewModel() {
    private val _chronometerList = MutableStateFlow<List<Chronometer>>(emptyList())
    val chronometerList = _chronometerList.asStateFlow()

    init {
        viewModelScope.launch {
            chronometerRepository.getAllChronometers().collect {
                _chronometerList.value = if(it.isEmpty()) emptyList() else it
            }
        }
    }

    fun addChronometer(chronometer: Chronometer) = viewModelScope
        .launch {
            chronometerRepository
                .addChronometer(chronometer)
        }

    fun updateChronometer(chronometer: Chronometer) = viewModelScope
        .launch {
            chronometerRepository
                .updateChronometer(chronometer)
        }

    fun deleteChronometer(chronometer: Chronometer) = viewModelScope
        .launch {
            chronometerRepository
                .deleteChronometer(chronometer)
        }
}