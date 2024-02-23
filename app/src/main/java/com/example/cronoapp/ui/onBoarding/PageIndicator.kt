package com.example.cronoapp.ui.onBoarding

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun Pager(size: Int, currentPage: Int) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .padding(top = 60.dp)
    ) {
        repeat(size) {
            PageIndicator(isSelected = it == currentPage)
        }
    }
}

@Composable
fun PageIndicator(isSelected: Boolean) {
    Box(
        modifier = Modifier
            .padding(5.dp)
            .height(10.dp)
            .width(25.dp)
            .clip(CircleShape)
            .background(if (isSelected) Color.Red else Color.Gray)
    )
}