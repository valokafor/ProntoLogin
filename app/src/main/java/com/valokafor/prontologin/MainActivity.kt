package com.valokafor.prontologin

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.valokafor.prontologin.presentation.navigation.AuthNavHost
import com.valokafor.prontologin.ui.theme.ProntoLoginTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ProntoLoginTheme {
                AuthNavHost()
            }
        }
    }
}