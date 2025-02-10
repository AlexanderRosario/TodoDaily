package com.alexander.tododaily

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.navigation.compose.rememberNavController
import com.alexander.tododaily.todotask.navigation.NavigationWrapper
import com.alexander.tododaily.todotask.ui.IntroScreen.IntroViewModel
import com.alexander.tododaily.todotask.ui.home.HomeViewModel
import com.alexander.tododaily.todotask.ui.login.LoginViewModel
import com.alexander.tododaily.todotask.ui.signup.SignUpViewModel
import com.alexander.tododaily.ui.theme.TodoDailyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val loginViewModel: LoginViewModel by viewModels()
    private val introViewModel: IntroViewModel by viewModels()
    private val signUpViewModel: SignUpViewModel by viewModels()
    private val homeViewModel: HomeViewModel by viewModels()
//
//    //    private val homeViewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoDailyTheme {


                val navHostController = rememberNavController()
                NavigationWrapper(navHostController, loginViewModel, introViewModel,signUpViewModel,homeViewModel)
            }
        }
    }
}

