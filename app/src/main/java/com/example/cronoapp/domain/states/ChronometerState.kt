package com.example.cronoapp.domain.states

data class ChronometerState(
    val activeChronometer: Boolean = false,
    val showSaveButton: Boolean = false,
    val showTextField: Boolean = false,
    val title: String = "",
    val isValidTitle: Boolean = true
)
