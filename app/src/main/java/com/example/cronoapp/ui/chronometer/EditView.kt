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
import com.example.cronoapp.ui.chronometer.components.MainActions
import com.example.cronoapp.ui.chronometer.components.MainIconButton
import com.example.cronoapp.ui.chronometer.components.MainTextField
import com.example.cronoapp.ui.chronometer.components.MainTitle
import com.example.cronoapp.ui.chronometer.components.formatTime
import com.example.cronoapp.ui.viewModels.ChronometerViewModel
import com.example.cronoapp.ui.viewModels.EditChronometerViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditView(
    navController: NavController,
    editChronometerViewModel: EditChronometerViewModel,
    chronometerViewModel: ChronometerViewModel,
    id: Long,
    dataStore: PreferencesDataStore,
    isDarkMode: Boolean,
    isColorBlindMode: Boolean
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    MainTitle(
                        title = "Edit Chronometer",
                        isColorBlindMode = isColorBlindMode
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    MainIconButton(icon = Icons.Default.ArrowBack) {
                        navController.popBackStack()
                    }
                },
                actions = {
                    MainActions(
                        isColorBlindMode = isColorBlindMode,
                        isDarkMode = isDarkMode,
                        dataStore = dataStore
                    )
                }
            )
        },
    ) {
        EditViewContent(paddingValues = it, navController, editChronometerViewModel, chronometerViewModel, id)
    }
}

@Composable
fun EditViewContent(
    paddingValues: PaddingValues,
    navController: NavController,
    editChronometerViewModel: EditChronometerViewModel,
    chronometerViewModel: ChronometerViewModel,
    id: Long
) {
    val state = editChronometerViewModel.state

    LaunchedEffect(state.activeChronometer) {
        editChronometerViewModel.chronometer()
    }

    LaunchedEffect(Unit) {
        editChronometerViewModel.getChronometer(id)

    }

    Column(
        modifier = Modifier
            .padding(paddingValues)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = formatTime(time = editChronometerViewModel.chronometerTime),
            fontSize = 50.sp,
            fontWeight = FontWeight.Bold
        )

        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier
                .padding(vertical = 16.dp)
        ) {
            CircularButton(
                icon = painterResource(id = R.drawable.play),
                enabled = !state.activeChronometer
            ) {
                editChronometerViewModel.start()
            }
            CircularButton(
                icon = painterResource(id = R.drawable.pause),
                enabled = state.activeChronometer
            ) {
                editChronometerViewModel.pause()
            }
        }

        MainTextField(
            value = state.title,
            onValueChange = { editChronometerViewModel.onValue(it) },
            label = "Title"
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Button(
                modifier = Modifier.padding(horizontal = 10.dp),
                enabled = !state.activeChronometer,
                onClick = {
                    if (editChronometerViewModel.validateTitle(state.title)) {
                        chronometerViewModel.updateChronometer(
                            Chronometer(
                                id = id,
                                title = state.title,
                                chronometer = editChronometerViewModel.chronometerTime
                            )
                        )
                        navController.popBackStack()}
                    else {
                        editChronometerViewModel.showTitleAlert()
                    }
                }
            ) {
                Text(text = "Update")
            }

            Button(
                modifier = Modifier.padding(horizontal = 10.dp),
                onClick = {
                    editChronometerViewModel.stop()
                    navController.popBackStack()
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
                    message = editChronometerViewModel.getAlertMessage(state.title),
                    confirmText = "Ok",
                    onConfirmClick = { editChronometerViewModel.closeTitleAlert() }
                ) {}
            }

            BackHandler {
                editChronometerViewModel.stop()
                navController.popBackStack()
            }
        }
    }
}