package com.alexander.tododaily.todotask.navigation

 import androidx.hilt.navigation.compose.hiltViewModel
// import androidx.navigation.compose.getBackStackEntry
import androidx.compose.runtime.Composable
 import androidx.compose.runtime.LaunchedEffect
 import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController//
import androidx.navigation.compose.NavHost//
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
 import com.alexander.tododaily.todotask.ui.home.HomeScreen
 import com.alexander.tododaily.todotask.ui.home.HomeViewModel
 import com.alexander.tododaily.todotask.ui.login.LoginScreen
import com.alexander.tododaily.todotask.ui.login.LoginViewModel
import com.alexander.tododaily.todotask.ui.signup.SignUpScreen
import com.alexander.tododaily.todotask.ui.signup.SignUpViewModel


@Composable
fun NavigationWrapper(
    navHostController: NavHostController = rememberNavController(),
    ) {


    NavHost(
        navController = navHostController,
        startDestination = Routes.Login.route
    ) {
        composable(Routes.Login.route) {
            val loginViewModel : LoginViewModel = hiltViewModel()

            LoginScreen(
            loginViewModel = loginViewModel,
            navHostController
            )
        }
//
        composable(Routes.Signup.route) {
            val signUpViewModel: SignUpViewModel = hiltViewModel()

            SignUpScreen(

                signUpViewModel = signUpViewModel,
                navHostController
            )
        }
        composable(Routes.Home.route) {
            val homeViewModel: HomeViewModel = hiltViewModel()
            HomeScreen(
                homeViewModel = homeViewModel,

                navHostController = navHostController
            )
        }
    }
//
//    val isUserAuthenticated by loginViewModel.isUserAuthenticated.collectAsState()
//
//
//    LaunchedEffect(isUserAuthenticated ) {
//        if (isUserAuthenticated) {
//            navHostController.navigate(Routes.Home.route) {
//                popUpTo(Routes.Login.route) { inclusive = true }
//            }
//        } else {
//            navHostController.navigate(Routes.Login.route) {
//                popUpTo(Routes.Home.route) { inclusive = true }
//            }
//        }
//    }
}



