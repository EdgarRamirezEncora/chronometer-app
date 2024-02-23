package com.example.cronoapp.ui.onBoarding

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.cronoapp.data.dataStore.PreferencesDataStore

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnBoardingPager (
    items: List<PageData>,
    pagerState: PagerState,
    modifier: Modifier = Modifier,
    navigationController: NavController,
    dataStore: PreferencesDataStore
) {
    Box(modifier = modifier) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            HorizontalPager(state = pagerState) {
                Column(
                    modifier = Modifier
                        .padding(60.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    DataLoader(modifier = Modifier
                        .size(200.dp)
                        .fillMaxWidth()
                        .align(alignment = Alignment.CenterHorizontally),
                        image = items[it].image
                    )
                    Text(
                        text = items[it].title,
                        modifier = Modifier
                            .padding(15.dp),
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Center
                    )
                }
            }

            Pager(size = items.size, currentPage = pagerState.currentPage)
        }

        if (pagerState.currentPage == pagerState.pageCount - 1) {
            Box(
                modifier = Modifier.align(Alignment.BottomCenter)
            ) {
                FinishButton(
                    navigationController = navigationController,
                    dataStore = dataStore
                )
            }
        }
    }
}