package com.alexander.tododaily.todotask.ui.IntroScreen

import androidx.lifecycle.ViewModel
import com.alexander.tododaily.todotask.ui.model.HabitModel

class IntroViewModel : ViewModel() {
    val habit = listOf(
        HabitModel("Exercise"),
        HabitModel("Read books"),
        HabitModel("Meditate"),
        HabitModel("Plan meals"),
        HabitModel("Water plats"),
        HabitModel("Journal"),
        HabitModel("Stretch 15 mins"),
        HabitModel("Review goals before"),
        HabitModel("SuperMarket List")

    )

}