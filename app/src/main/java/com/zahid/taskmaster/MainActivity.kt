package com.zahid.taskmaster

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.rememberNavController
import com.zahid.taskmaster.presentation.utils.CircularIndeterminateProgressBar
import com.zahid.taskmaster.presentation.navigation.AppNavHostGraph
import com.zahid.taskmaster.presentation.theme.TaskMasterAppTheme

class MainActivity : ComponentActivity() {
    private var showProgressDialog by mutableStateOf(false)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            TaskMasterAppTheme {
                ChangeSystemBarsTheme(!isSystemInDarkTheme())
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Surface {
                        AppNavHostGraph(navController = navController)
                        if (showProgressDialog) {
                            Box(modifier = Modifier
                                .fillMaxSize()
                                .clickable(
                                    indication = null, // disable ripple effect
                                    interactionSource = remember { MutableInteractionSource() },
                                    onClick = { }
                                ),
                                contentAlignment = Alignment.Center
                            ) {
                                CircularIndeterminateProgressBar(
                                    isDisplayed = showProgressDialog,
                                    verticalBias = 0.4f
                                )
                            }

                        }
                    }
                }
            }
        }
    }

    @Composable
    private fun ChangeSystemBarsTheme(lightTheme: Boolean) {
        val barColor = MaterialTheme.colorScheme.background.toArgb()
        LaunchedEffect(lightTheme) {
            if (lightTheme) {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.light(
                        barColor, barColor,
                    ),
                    navigationBarStyle = SystemBarStyle.light(
                        barColor, barColor,
                    ),
                )
            } else {
                enableEdgeToEdge(
                    statusBarStyle = SystemBarStyle.dark(
                        barColor,
                    ),
                    navigationBarStyle = SystemBarStyle.dark(
                        barColor,
                    ),
                )
            }
        }
    }
}
