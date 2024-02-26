package com.example.cronoapp.ui.chronometer.components

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.cronoapp.R
import com.example.cronoapp.data.dataStore.PreferencesDataStore
import com.example.cronoapp.ui.viewModels.ChronometerViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Composable
fun MainActions(
    isColorBlindMode: Boolean,
    isDarkMode: Boolean,
    dataStore: PreferencesDataStore
) {
    val scope = rememberCoroutineScope()
    val isMenuOpen = remember { mutableStateOf(false) }

    IconButton(
        onClick = { isMenuOpen.value = true }
    ) {
        Icon(
            painter = painterResource(id = R.drawable.menu),
            contentDescription = "Menu",
            tint = if(isColorBlindMode) Color.Black else Color.White,
        )

        DropdownMenu(
            expanded = isMenuOpen.value,
            onDismissRequest = { isMenuOpen.value = false }
        ) {
            DropdownMenuItem(
                text = {
                    Text(
                        text = "Switch to ${if(isDarkMode) "Light" else "Dark"} mode",
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(
                            id = if(isDarkMode) R.drawable.light_mode_icon
                            else R.drawable.dark_mode_icon
                        ),
                        contentDescription = "Theme icon"
                    )
                },
                onClick = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.updateDarkMode(!isDarkMode)
                    }
                },
            )

            DropdownMenuItem(
                text = {
                    Text(
                        text = "Turn ${if(isColorBlindMode) "off" else "on"} blind color mode",
                    )
                },
                leadingIcon = {
                    Icon(
                        painter = painterResource(id = R.drawable.color_blind),
                        contentDescription = "Theme icon"
                    )
                },
                onClick = {
                    scope.launch(Dispatchers.IO) {
                        dataStore.updateColorBlindMode(!isColorBlindMode)
                    }
                },
            )
            Divider(
                thickness = 3.dp
            )
        }
    }
}

@Composable
fun MainTitle(
    title: String,
    isColorBlindMode: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 25.sp,
            color = if(isColorBlindMode) Color.Black else Color.White,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
fun MainTextField(value: String, onValueChange: (String) -> Unit, label: String) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(text = label) },
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 30.dp)
            .padding(bottom = 15.dp)

    )
}

@Composable
fun formatTime(time: Long): String {
    val seconds = (time / 1000) % 60
    val minutes = (time / 1000 / 60) % 60
    val hours = time / 1000 / 3600

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Composable
fun CronCard(
    title: String,
    time: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 15.dp, vertical = 10.dp)
            .clickable { onClick() }
            .border(
                2.dp,
                MaterialTheme.colorScheme.primary,
                RoundedCornerShape(10.dp)
            )
    ) {
        Column(
            modifier = Modifier
                .padding(10.dp)
        ) {
            Text(
                text = title,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp),
            )
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 10.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.timer),
                    contentDescription = "",
                    tint = Color.Gray
                )
                Text(
                    text = time,
                    fontSize = 25.sp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 5.dp),
                )
            }
        }
    }
}

@Composable
fun DarkModeSwitchButton (
    dataStore: PreferencesDataStore,
    isDarkMode: Boolean
) {
    val scope = rememberCoroutineScope()
    var themeMode by remember { mutableStateOf("") }

    LaunchedEffect(themeMode) {
        themeMode = if(isDarkMode) "Switch to light mode" else "Switch to dark mode"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth(),
        horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = themeMode,
            fontWeight = FontWeight.Bold
        )
        Switch(
            checked = isDarkMode,
            onCheckedChange = {
                scope.launch {
                    dataStore.updateDarkMode(it)
                    themeMode = if(it) "Switch to light mode" else "Switch to dark mode"
                }
            },
            thumbContent = if (isDarkMode) {
                {
                    Icon(
                        painter = painterResource(id = R.drawable.light_mode_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(SwitchDefaults.IconSize)
                    )
                }
            } else {
                {
                    Icon(
                        painter = painterResource(id = R.drawable.dark_mode_icon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(SwitchDefaults.IconSize)
                    )
                }
            }
        )
    }
}

@Composable
fun Alert(
    title: String,
    message: String,
    confirmText: String,
    onConfirmClick: () -> Unit,
    onDismissClick: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismissClick,
        title = { Text(text = title) },
        text = { Text(text = message) },
        confirmButton = {
            Button(onClick = onConfirmClick) {
                Text(text = confirmText)
            }
        }
    )
}

@Composable
fun SearchBar(
    chronometerViewModel: ChronometerViewModel
) {
    val searchBar = chronometerViewModel.searchBar
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .padding(5.dp)
    ) {
        MainTextField(
            value = searchBar.value,
            onValueChange = {
                chronometerViewModel.updateSearchBar(it)
                chronometerViewModel.getChronometersByTitle(it)
            },
            label = "Search"
        )
    }
}

