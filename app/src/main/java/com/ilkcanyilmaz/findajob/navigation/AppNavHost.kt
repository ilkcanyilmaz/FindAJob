package com.ilkcanyilmaz.findajob.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.ilkcanyilmaz.findajob.ui.auth.LoginScreen
import com.ilkcanyilmaz.findajob.ui.auth.SignupScreen
import com.ilkcanyilmaz.findajob.ui.home.HomeScreen
import com.ilkcanyilmaz.findajob.ui.newjob.NewJobScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController(),
    startDestination: String = ROUTE_LOGIN
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(ROUTE_LOGIN) {
            LoginScreen(navController = navController)
        }
        composable(ROUTE_SIGNUP) {
            SignupScreen(navController = navController)
        }
        composable(ROUTE_HOME) {
            HomeScreen(navController = navController)
        }
        composable(ROUTE_NEW_JOB) {
            NewJobScreen(navController = navController)
        }
    }
}