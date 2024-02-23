package com.example.cronoapp.ui.chronometer

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.cronoapp.R
import com.example.cronoapp.data.dataStore.PreferencesDataStore
import com.example.cronoapp.data.entities.Chronometer
import com.example.cronoapp.ui.chronometer.components.Alert
import com.example.cronoapp.ui.chronometer.components.CircularButton
import com.example.cronoapp.ui.chronometer.components.MainIconButton
import com.example.cronoapp.ui.chronometer.components.MainTextField
import com.example.cronoapp.ui.chronometer.components.MainTitle
import com.example.cronoapp.ui.chronometer.components.formatTime
import com.example.cronoapp.ui.viewModels.AddChronometerViewModel
import com.example.cronoapp.ui.viewModels.ChronometerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddView(
    navController: NavController,
    addChronometerViewModel: AddChronometerViewModel,
    chronometerViewModel: ChronometerViewModel,
    dataStore: PreferencesDataStore,
    isDarkMode: Boolean
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    MainTitle(
                        title = "Add Chronometer",
                        dataStore,
                        isDarkMode,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },

    ) {
        AddViewContent(paddingValues = it, navController, addChronometerViewModel, chronometerViewModel)
    }

}

@Composable
fun AddViewContent(
    paddingValues: PaddingValues,
    navController: NavController,
    addChronometerViewModel: AddChronometerViewModel,
    chronometerViewModel: ChronometerViewModel
) {
    val state = addChronometerViewModel.state

    LaunchedEffect(state.activeChronometer) {
        addChronometerViewModel.chronometer()
    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = formatTime(time = addChronometerViewModel.chronometerTime),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )

        if(!state.showTextField) {
            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                CircularButton(
                    icon = painterResource(id = R.drawable.play),
                    enabled = !state.activeChronometer
                ) {
                    addChronometerViewModel.start()
                }
                CircularButton(
                    icon = painterResource(id = R.drawable.pause),
                    enabled = state.activeChronometer
                ) {
                    addChronometerViewModel.pause()
                }
            }

            Row(
                horizontalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(vertical = 16.dp)
            ) {
                CircularButton(
                    icon = painterResource(id = R.drawable.stop),
                    enabled = !state.activeChronometer &&
                            addChronometerViewModel.chronometerTime != 0L
                ) {
                    addChronometerViewModel.stop()
                }
                CircularButton(
                    icon = painterResource(id = R.drawable.save),
                    enabled = state.showSaveButton
                ) {
                    addChronometerViewModel.showTextField()
                }
            }
        }
        
        if(state.showTextField) {
            MainTextField(
                value = state.title,
                onValueChange = { addChronometerViewModel.onValue(it) },
                label = "Title"
            )
            Row(
               modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        if (addChronometerViewModel.validateTitle(state.title)) {
                            chronometerViewModel.addChronometer(
                                Chronometer(
                                    title = state.title,
                                    chronometer = addChronometerViewModel.chronometerTime
                                )
                            )

                            addChronometerViewModel.stop()
                            navController.popBackStack()
                        } else {
                            addChronometerViewModel.showTitleAlert()
                        }
                    }
                ) {
                    Text(text = "Save")
                }

                Button(
                    modifier = Modifier.padding(horizontal = 10.dp),
                    onClick = {
                        addChronometerViewModel.hideTextField()
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.Red
                    )

                ) {
                    Text(text = "Cancel")
                }

                if(!state.isValidTitle) {
                    Alert(
                        title = "Invalid Title",
                        message = addChronometerViewModel.getAlertMessage(state.title),
                        confirmText = "Ok",
                        onConfirmClick = { addChronometerViewModel.closeTitleAlert() }
                    ) {}
                }

                BackHandler {
                    addChronometerViewModel.stop()
                    navController.popBackStack()
                }
            }
        }
    }
}