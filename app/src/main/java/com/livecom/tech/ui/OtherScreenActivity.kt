package com.livecom.tech.ui

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.livecom.tech.ui.navigation.AppNavigation
import com.livecom.tech.ui.navigation.Screen
import com.livecom.tech.ui.theme.SdkDemoAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OtherScreenActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val startRoute = intent.extras?.getString(START_ROUTE_ARG)
        setContent {
            SdkDemoAppTheme {
                AppNavigation(startRoute = startRoute ?: Screen.MAIN.name)
            }
        }
    }

    companion object {
        private const val START_ROUTE_ARG = "START_ROUTE_ARG"

        fun startActivity(context: Context, startDestination: String) {
            context.startActivity(
                Intent(context, OtherScreenActivity::class.java).apply {
                    putExtra(START_ROUTE_ARG, startDestination)
                }
            )
        }
    }
}