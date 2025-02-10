package com.alexander.tododaily.todotask.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController//
import androidx.navigation.compose.NavHost//
import androidx.navigation.compose.composable//
import com.alexander.tododaily.todotask.ui.IntroScreen.IntroViewModel
import com.alexander.tododaily.todotask.ui.home.HomeScreen
import com.alexander.tododaily.todotask.ui.home.HomeViewModel
import com.alexander.tododaily.todotask.ui.login.LoginScreen
import com.alexander.tododaily.todotask.ui.login.LoginViewModel
import com.alexander.tododaily.todotask.ui.signup.SignUpScreen
import com.alexander.tododaily.todotask.ui.signup.SignUpViewModel


@Composable
fun NavigationWrapper(
    navHostController: NavHostController,
    loginViewModel: LoginViewModel,
    introViewModel: IntroViewModel,
    signUpViewModel: SignUpViewModel,
    homeViewModel: HomeViewModel

) {


    NavHost(
        navController = navHostController,
        startDestination = Routes.login.route
    ) {
        composable(Routes.login.route) {
            LoginScreen(
                loginViewModel = loginViewModel,
                navHostController
            )
        }

        composable(Routes.signup.route) {
            SignUpScreen(
                signUpViewModel = signUpViewModel,
                navHostController
            )
        }
        composable(Routes.home.route) {
            HomeScreen(
                homeViewModel = homeViewModel,

                navHostController = navHostController
            )
        }
    }

    val isUserAuthenticated by loginViewModel.isUserAuthenticated.collectAsState()


    LaunchedEffect(isUserAuthenticated ) {
        if (isUserAuthenticated) {
            navHostController.navigate(Routes.home.route) {
                popUpTo(Routes.login.route) { inclusive = true }
            }
        } else {
            navHostController.navigate(Routes.login.route) {
                popUpTo(Routes.home.route) { inclusive = true }
            }
        }
    }
}