package com.devkurt.markets

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            // Uygulama kökü (tema + Navigation host), shared-features hazır olunca buraya gelecek.
        }
    }
}
