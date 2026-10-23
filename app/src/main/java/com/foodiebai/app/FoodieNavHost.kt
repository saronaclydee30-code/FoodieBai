package com.foodiebai.app

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.foodiebai.app.ui.screens.HomeScreen
import com.foodiebai.app.ui.screens.LoginScreen
import com.foodiebai.app.ui.screens.RegisterScreen
import com.foodiebai.app.ui.screens.WelcomeScreen

object Routes {
    const val WELCOME = "welcome"
    const val LOGIN = "login"
    const val REGISTER = "register"
    const val HOME = "home"
}

@Composable
fun FoodieNavHost() {
    val nav = rememberNavController()

    NavHost(navController = nav, startDestination = Routes.WELCOME) {
        composable(Routes.WELCOME) {
            WelcomeScreen(
                onSignIn = { nav.navigate(Routes.LOGIN) },
                onCreateAccount = { nav.navigate(Routes.REGISTER) }
            )
        }
        composable(Routes.LOGIN) {
            LoginScreen(
                onBack = { nav.popBackStack() },
                onSignUp = {
                    nav.navigate(Routes.REGISTER) { popUpTo(Routes.WELCOME) }
                },
                onLoggedIn = {
                    nav.navigate(Routes.HOME) { popUpTo(Routes.WELCOME) { inclusive = true } }
                }
            )
        }
        composable(Routes.REGISTER) {
            RegisterScreen(
                onBack = { nav.popBackStack() },
                onSignIn = {
                    nav.navigate(Routes.LOGIN) { popUpTo(Routes.WELCOME) }
                },
                onRegistered = {
                    nav.navigate(Routes.HOME) { popUpTo(Routes.WELCOME) { inclusive = true } }
                }
            )
        }
        composable(Routes.HOME) { HomeScreen() }
    }
}
