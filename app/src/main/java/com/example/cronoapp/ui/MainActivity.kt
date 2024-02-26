package com.example.cronoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.cronoapp.data.dataStore.PreferencesDataStore
import com.example.cronoapp.ui.navigation.NavManager
import com.example.cronoapp.ui.theme.CronoAppTheme
import com.example.cronoapp.ui.viewModels.AddChronometerViewModel
import com.example.cronoapp.ui.viewModels.ChronometerViewModel
import com.example.cronoapp.ui.viewModels.EditChronometerViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val addChronometerViewModel: AddChronometerViewModel by viewModels()
        val chronometerViewModel: ChronometerViewModel by viewModels()
        val editChronometerViewModel: EditChronometerViewModel by viewModels()
        setContent {
            val dataStore = PreferencesDataStore(this)
            val darkMode = dataStore.getDarkMode.collectAsState(initial = false)
            val isDarkMode = darkMode.value
            val colorBlindMode = dataStore.getColorBlindMode.collectAsState(initial = false)
            val isColorBlindMode = colorBlindMode.value
            CronoAppTheme(
                darkTheme = isDarkMode,
                colorBlindMode = isColorBlindMode
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavManager(
                        addChronometerViewModel = addChronometerViewModel,
                        chronometerViewModel = chronometerViewModel,
                        editChronometerViewModel = editChronometerViewModel,
                        dataStore = dataStore,
                        isDarkMode =  isDarkMode,
                        isColorBlindMode = isColorBlindMode
                    )
                }
            }
        }
    }
}
