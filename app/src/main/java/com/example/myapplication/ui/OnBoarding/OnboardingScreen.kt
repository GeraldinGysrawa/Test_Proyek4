package com.example.myapplication.ui.onboarding.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.myapplication.R
import kotlinx.coroutines.launch
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

data class OnboardingPage(
    val imageRes: Int,
    val title: String,
    val description: String
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pages = listOf(
        OnboardingPage(R.drawable.logo_moneymind, "Selamat Datang!", "Aplikasi terbaik untuk mengatur keuanganmu."),
        OnboardingPage(R.drawable.logo_moneymind, "Catat Keuangan", "Rekam semua transaksi harian dengan mudah."),
        OnboardingPage(R.drawable.logo_moneymind, "Analisis & Laporan", "Dapatkan wawasan tentang pengeluaran dan pemasukanmu.")
    )

    val pagerState = rememberPagerState(pageCount = { pages.size })
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HorizontalPager(state = pagerState) { page ->
            OnboardingPageUI(
                page = pages[page],
                isLastPage = page == pages.lastIndex,
                onNextClick = {
                    coroutineScope.launch {
                        if (page < pages.lastIndex) {
                            pagerState.animateScrollToPage(page + 1)
                        } else {
                            onFinish()
                        }
                    }
                }
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            pages.indices.forEach { index ->
                Indicator(isSelected = index == pagerState.currentPage)
            }
        }
    }
}

@Composable
fun OnboardingPageUI(
    page: OnboardingPage,
    isLastPage: Boolean,
    onNextClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Spacer(modifier = Modifier.height(40.dp))

        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Image(
                painter = painterResource(id = page.imageRes),
                contentDescription = null,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
            )
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = page.title, style = MaterialTheme.typography.headlineSmall)
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = page.description,
                style = MaterialTheme.typography.bodyMedium,
                textAlign = TextAlign.Center
            )
        }

        Button(
            onClick = onNextClick,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Text(if (isLastPage) "Mulai" else "Lanjut")
        }
    }
}

@Composable
fun Indicator(isSelected: Boolean) {
    val color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
    Box(
        modifier = Modifier
            .padding(4.dp)
            .size(10.dp)
            .background(color = color, shape = MaterialTheme.shapes.small)
    )
}
