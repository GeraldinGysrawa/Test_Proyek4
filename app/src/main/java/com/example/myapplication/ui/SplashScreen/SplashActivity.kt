package com.example.myapplication.ui.SplashScreen

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.myapplication.MainActivity
import com.example.myapplication.R

import com.example.myapplication.ui.Theme.MainColor
import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color

import com.example.myapplication.ui.OnBoarding.OnboardingActivity


class SplashActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SplashScreen {
                startActivity(Intent(this, OnboardingActivity::class.java))
                finish() // Tutup SplashActivity setelah berpindah
            }
        }
    }
}

@Composable
fun SplashScreen(onTimeout: () -> Unit) {
    var startAnimation by remember { mutableStateOf(false) }
    val alphaAnim = animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(durationMillis = 1000),
        label = ""
    )

    var progress by remember { mutableStateOf(0f) }

    // Efek animasi masuk & ProgressBar berjalan
    LaunchedEffect(Unit) {
        startAnimation = true
        for (i in 1..100) {
            progress = i / 100f
            delay(20)  // Update progress setiap 20ms
        }
        delay(500) // Tambahan delay sebelum berpindah
        onTimeout()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainColor),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = R.drawable.logo_moneymind),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(300.dp)
                    .alpha(alphaAnim.value)
            )
            // Tambahkan teks di bawah logo
            Text(
                text = "MONEY MIND",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White
            )

            Spacer(modifier = Modifier.height(20.dp)) // Spasi sebelum progress bar
            Spacer(modifier = Modifier.height(20.dp))
            LinearProgressIndicator(progress = progress, modifier = Modifier.width(200.dp))
        }
    }
}
