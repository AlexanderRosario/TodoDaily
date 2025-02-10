package com.alexander.tododaily.todotask.ui.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexander.tododaily.todotask.domain.AddTaskUseCase
import com.alexander.tododaily.todotask.domain.DeleteTaskUseCase
import com.alexander.tododaily.todotask.domain.GetTasksUseCase
import com.alexander.tododaily.todotask.domain.UpdateTaskUseCase
import com.alexander.tododaily.todotask.ui.model.TaskModel
import com.alexander.tododaily.todotask.ui.model.menuState
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val addTaskUseCase: AddTaskUseCase,
    private val getTasksUseCase: GetTasksUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val auth: FirebaseAuth
) : ViewModel() {


    val menuOptions: List<menuState> = listOf(
        menuState(1, "Actualizar"),
        menuState(2, "Borrar")
    )


    private val _isFeatureEnabled = mutableStateOf(false)
    val isFeatureEnabled: State<Boolean> = _isFeatureEnabled

    // Método para cambiar el estado
    fun toggleFeature() {
        _isFeatureEnabled.value = !_isFeatureEnabled.value
    }

    val calendar = Calendar.getInstance()
    val currentDate =
        "${calendar.get(Calendar.YEAR)}-${calendar.get(Calendar.MONTH) + 1}-${calendar.get(Calendar.DAY_OF_MONTH)}"
    val currentTime = "${calendar.get(Calendar.HOUR_OF_DAY)}:${calendar.get(Calendar.MINUTE)}:${
        calendar.get(Calendar.SECOND)
    }"
    val dateTime = "$currentDate $currentTime" // Combina fecha y hora


    private val _state = MutableLiveData<String>() // Para manejar mensajes de estado
    val state: LiveData<String> get() = _state


    private val _tasks = MutableStateFlow<List<TaskModel>>(emptyList())

    // Variable pública para exponer la lista de tareas (solo lectura)
    val tasks: StateFlow<List<TaskModel>> = _tasks

    private val _task = mutableStateOf(TaskModel())
    val task: State<TaskModel> = _task
    fun onChangeDeleteTask(task: TaskModel) {
        _task.value = task

    }


    private val _isDialogShowDelete = mutableStateOf(false)
    val isDialogShowDelete: State<Boolean> = _isDialogShowDelete

    fun onChangeShowDialogDele() {
        _isDialogShowDelete.value = !_isDialogShowDelete.value
    }

    private val _isDialogShowUpdate = mutableStateOf(false)
    val isDialogShowUpdate: State<Boolean> = _isDialogShowUpdate
    fun onChangeShowDialogUpdate() {

        _isDialogShowUpdate.value = !_isDialogShowUpdate.value
    }


    fun onClickAddTask(
        task: String,

        ) {
        if (task.isEmpty()) {

        } else {
            viewModelScope.launch {
                val newTask = TaskModel(
                    task = task, taskClasification = "Personal", todayDate = dateTime
                )
                val result = addTaskUseCase(newTask)
                if (result.isSuccess) {
                    _state.value = "Task added successfully"
//                _tasks.value = _tasks.value.map { task ->
//                    if (task.id == taskModel.id) taskModel else task
//                }
                    getTasks() // Refresca la lista
                } else {
                    _state.value = result.exceptionOrNull()?.message
                }
            }
        }

    }

    fun getTasks() {
        viewModelScope.launch {
            val result = getTasksUseCase()
            if (result.isSuccess) {
                _tasks.value = result.getOrDefault(emptyList())
            } else {
                val exception = result.exceptionOrNull()
                if (exception?.message == "User not authenticated") {
//                    _navigateToLogin.value = true // Redirigir al login
                } else {
                    _state.value = exception?.message
                }
            }
        }
    }


    fun onClickCheckedTask(taskModel: TaskModel) {
        _tasks.value = _tasks.value.map { task ->
            if (task == taskModel) {
                task.copy(
                    isChecked = !task.isChecked,
                    isEnable = task.isChecked,
                    indicatorVisibility = task.isChecked
                )
            } else {
                task
            }

        }
    }


    fun onChangeShowDialogDeleAll() {
        _isDialogShowDeleteAll.value = !_isDialogShowDeleteAll.value
    }

    private val _isDialogShowDeleteAll = mutableStateOf(false)
    val isDialogShowDeleteAll: State<Boolean> = _isDialogShowDeleteAll

    fun deleteSelectedTasks() {
        _tasks.value.map { task ->
            if (task.isChecked) {
                onChangeDeleteTask(task)
                deleteTask()
            } else {
                task
            }

        }

    }

    fun deleteTask() {
        viewModelScope.launch {
//            Log.i("JR", _task.value.toString())
            val result = deleteTaskUseCase(task.value)
            if (result.isSuccess) {
                getTasks()
            } else {
                val exception = result.exceptionOrNull()
                if (exception?.message == "User not authenticated") {
//                    _navigateToLogin.value = true // Redirigir al login
                } else {
                    _state.value = exception?.message
                }
            }
        }
    }


    fun updateTask(taskModel: TaskModel) {
        val updatedTask = _tasks.value.find { it.id == taskModel.id } ?: taskModel
//
        viewModelScope.launch {
            val result = updateTaskUseCase(updatedTask)
            if (result.isSuccess) {
//                getTasks()
            } else {
                val exception = result.exceptionOrNull()
                if (exception?.message == "User not authenticated") {
//                    _navigateToLogin.value = true // Redirigir al login
                } else {
                    _state.value = exception?.message
                }
            }

        }
    }

    private val _showLogOutDialog = mutableStateOf(false)
    val showLogOutDialog: State<Boolean> = _showLogOutDialog

    fun ChangeshowLogoutDialog() {
        Log.i("jr", showLogOutDialog.value.toString())
        _showLogOutDialog.value = !showLogOutDialog.value
    }

    fun UserLogOut() {
        auth.signOut()

    }

}

