package com.jacqulin.gainly.feature.onboarding

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

@Composable
fun OnboardingScreen(
    onFinish: () -> Unit,
) {

    val pages = listOf(
        OnboardingModel.FirstPage,
        OnboardingModel.SecondPage,
        OnboardingModel.ThirdPage,
        OnboardingModel.FourthPage
    )

    val pagerState = rememberPagerState { pages.size }

    Box(modifier = Modifier.fillMaxSize()) {
        HorizontalPager(
            state = pagerState,
            modifier = Modifier.fillMaxSize()
        ) { index ->
            OnboardingItem(onboardingModel = pages[index])
        }

        val scope = rememberCoroutineScope()
        val currentPage = pagerState.currentPage
        val isLastPage = currentPage == pages.lastIndex

        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(24.dp)
        ) {
            OnboardingNextButton(
                currentPage = currentPage,
                totalPages = pages.size,
                isLastPage = isLastPage,
                onNext = {
                    scope.launch {
                        if (!isLastPage) {
                            pagerState.animateScrollToPage(currentPage + 1)
                        } else {
                            onFinish()
                        }
                    }
                }
            )
        }
    }
}
