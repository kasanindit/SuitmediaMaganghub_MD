package com.example.suitmediamaganghub_md

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.suitmediamaganghub_md.ui.navigation.AppNavigation
import com.example.suitmediamaganghub_md.ui.theme.SuitmediaMaganghub_MDTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SuitmediaMaganghub_MDTheme {
                AppNavigation()
            }
        }
    }
}



