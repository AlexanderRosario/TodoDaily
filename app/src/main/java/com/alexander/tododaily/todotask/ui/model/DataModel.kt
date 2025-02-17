package com.alexander.tododaily.todotask.ui.model


data class HabitModel(
    var habit: String

)

data class TaskModel(
    var id: Long = System.currentTimeMillis(),
    var uid: String? = null,
    var task: String = "",
    var description: String = "",
    var taskClasification: String = "",
    val todayDate: String = "", //get from kotlin
    var alarmDate:  Long? = null,
    var isEnable: Boolean = true,
    var isChecked: Boolean = false,
    var indicatorVisibility: Boolean = true
)


sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    object SignedOut : UiState()
    object InProgress : UiState()
    object SignIn : UiState()
    data class Error(val message: String) : UiState()

}
sealed class RegistrationState {
    object Loading : RegistrationState()
    object Success : RegistrationState()
    data class Error(val message: String) : RegistrationState()
}


data class menuState (
    val id:Int,
    val name:String,


)
//    data class Error(val message: String) : menuState()

data class TaskClasification(
    val id: Int,
    val taskClasification:String,

)



