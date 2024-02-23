package com.example.cronoapp.ui.chronometer

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.datastore.dataStore
import androidx.navigation.NavController
import com.example.cronoapp.data.dataStore.PreferencesDataStore
import com.example.cronoapp.ui.chronometer.components.CronCard
import com.example.cronoapp.ui.chronometer.components.DarkModeSwitchButton
import com.example.cronoapp.ui.chronometer.components.FloatButton
import com.example.cronoapp.ui.chronometer.components.MainTitle
import com.example.cronoapp.ui.chronometer.components.formatTime
import com.example.cronoapp.ui.viewModels.ChronometerViewModel
import me.saket.swipe.SwipeAction
import me.saket.swipe.SwipeableActionsBox

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeView(
    navController: NavController,
    chronometerViewModel: ChronometerViewModel,
    dataStore: PreferencesDataStore,
    isDarkMode: Boolean
) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    MainTitle(
                        title = "Chronometer App",
                        dataStore,
                        isDarkMode,
                    )
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                )
            )
        },
        floatingActionButton = {
            FloatButton {
                navController.navigate("add_view")
            }
        }
    ) {
        HomeViewContent(
            paddingValues = it,
            navController = navController,
            chronometerViewModel = chronometerViewModel,
            dataStore = dataStore,
            isDarkMode = isDarkMode
        )
    }

}

@Composable
fun HomeViewContent(
    paddingValues: PaddingValues,
    navController: NavController,
    chronometerViewModel: ChronometerViewModel,
    dataStore: PreferencesDataStore,
    isDarkMode: Boolean
    ) {
    Column(
        modifier = Modifier
            .padding(paddingValues)
    ) {
        val chronometerList by chronometerViewModel.chronometerList.collectAsState()
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            DarkModeSwitchButton(
                dataStore = dataStore,
                isDarkMode = isDarkMode)
        }
        
        LazyColumn() {
            items(chronometerList) {
                val delete = SwipeAction(
                    icon = rememberVectorPainter(image = Icons.Default.Delete),
                    background = Color.Red,
                    onSwipe = { chronometerViewModel.deleteChronometer(it) }
                )

                val edit = SwipeAction(
                    icon = rememberVectorPainter(image = Icons.Default.Edit),
                    background = Color.Yellow,
                    onSwipe = { navController.navigate("edit_view/${it.id}") }
                )
                SwipeableActionsBox(startActions = listOf(edit), endActions = listOf(delete), swipeThreshold = 270.dp) {
                    CronCard(title = it.title, time = formatTime(time = it.chronometer)) {
                        navController.navigate("edit_view/${it.id}")
                    }
                }
            }
        }
    }
}