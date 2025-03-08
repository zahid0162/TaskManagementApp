package com.zahid.taskmaster.presentation.screens


import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.zahid.taskmaster.R
import com.zahid.taskmaster.presentation.compose_ui_utils.navigateToNextScreen
import com.zahid.taskmaster.presentation.navigation.RootNavRoutes
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun SplashScreen(
    navHostController: NavHostController,
    ) {
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = true) {
        scope.launch {
            delay(3000L)
            navHostController.navigateToNextScreen<Unit>(RootNavRoutes.USER_HOME.route)
        }
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .statusBarsPadding()
            .navigationBarsPadding(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painterResource(
                R.drawable.splash_logo
            ),
            contentDescription = stringResource(R.string.splash_logo),

            )
        Spacer(modifier = Modifier.height(16.dp))
        Image(
            painterResource(R.drawable.taskmaster),
            contentDescription = stringResource(R.string.splash_logo),
        )


    }
}