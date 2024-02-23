package com.example.cronoapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
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
            CronoAppTheme(
                darkTheme = isDarkMode
            ) {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    NavManager(
                        addChronometerViewModel,
                        chronometerViewModel,
                        editChronometerViewModel,
                        dataStore,
                        isDarkMode
                    )
                }
            }
        }
    }
}
