package com.example.cronoapp.ui.viewModels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cronoapp.data.entities.Chronometer
import com.example.cronoapp.domain.repositories.ChronometerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChronometerViewModel @Inject constructor(
    private val chronometerRepository: ChronometerRepository
): ViewModel() {
    private val _chronometerList = MutableStateFlow<List<Chronometer>>(emptyList())
    val chronometerList = _chronometerList.asStateFlow()

    private val _searchBar = mutableStateOf("")
    val searchBar = _searchBar

    init {
        viewModelScope.launch {
            chronometerRepository.getAllChronometers().collect {
                _chronometerList.value = it.ifEmpty { emptyList() }
            }
        }
    }

    fun getChronometersByTitle(title: String) = viewModelScope
        .launch {
            chronometerRepository
                .getChronometersByTitle(title)
                .collect {
                    _chronometerList.value = it.ifEmpty { emptyList() }
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

    fun updateSearchBar(title: String) {
        searchBar.value = title
    }
}