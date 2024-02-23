package com.example.cronoapp.ui.onBoarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cronoapp.data.dataStore.PreferencesDataStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun FinishButton(
    navigationController: NavController,
    dataStore: PreferencesDataStore
    ) {
    Row(
        modifier = Modifier
            .padding(bottom = 20.dp)
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.Center,
    ) {
        OutlinedButton(
            onClick = {
                CoroutineScope(Dispatchers.IO).launch {
                    dataStore.updateFirstTime(false)
                }
                navigationController.navigate("home_view")
            }
        ) {
            Text(
                text = "Start",
                modifier = Modifier
                    .padding(vertical = 8.dp, horizontal = 40.dp),
                color = Color.Gray
            )
        }
    }
}