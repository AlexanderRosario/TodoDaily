package com.alexander.tododaily

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.rememberNavController
import com.alexander.tododaily.todotask.navigation.NavigationWrapper
//import com.alexander.tododaily.todotask.ui.component.DatePickerFieldToModal
import com.alexander.tododaily.ui.theme.TodoDailyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodoDailyTheme {

//                DatePickerFieldToModal()
                NavigationWrapper()
            }
        }
    }
}

