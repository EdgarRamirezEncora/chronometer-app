package com.example.cronoapp.ui.onBoarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import com.example.cronoapp.R
import com.example.cronoapp.data.dataStore.PreferencesDataStore

data class PageData(
    val image: Int,
    val title: String,
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainOnBoarding(navigationController: NavController, dataStore: PreferencesDataStore) {
    val items = ArrayList<PageData>()

    items.add(
        PageData(
            R.raw.welcome,
            "Welcome to Chronometer App",
        )
    )

    items.add(
        PageData(
            R.raw.chronometer,
            "Keep track of your times!",
        )
    )

    items.add(
        PageData(
            R.raw.edit,
            "Swipe to the left to edit!",
        )
    )

    items.add(
        PageData(
            R.raw.delete,
            "Swipe to the right to delete!",
        )
    )

    items.add(
        PageData(
            R.raw.start,
            "Let's start!",
        )
    )

    val pagerState = rememberPagerState(initialPage = 0) { items.size }

    OnBoardingPager(
        items = items,
        pagerState = pagerState,
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        navigationController = navigationController,
        dataStore = dataStore
    )
}
