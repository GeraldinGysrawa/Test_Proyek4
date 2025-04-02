package com.example.myapplication.ui.OnBoarding

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.MainActivity
import com.example.myapplication.ui.onboarding.components.OnboardingScreen

class OnboardingActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            OnboardingScreen {
                startActivity(Intent(this, MainActivity::class.java))
                finish() // Tutup OnboardingActivity setelah berpindah
            }
        }
    }
}
