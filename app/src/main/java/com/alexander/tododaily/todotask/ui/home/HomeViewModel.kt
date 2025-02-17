package com.alexander.tododaily.todotask.ui.home

import android.icu.text.CaseMap.Title
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.alexander.tododaily.todotask.domain.AddTaskUseCase
import com.alexander.tododaily.todotask.domain.DeleteTaskUseCase
import com.alexander.tododaily.todotask.domain.GetTasksUseCase
import com.alexander.tododaily.todotask.domain.UpdateTaskUseCase
import com.alexander.tododaily.todotask.ui.model.TaskClasification
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


    private val _title = MutableLiveData<String>()
    private val _description = MutableLiveData<String>()
    private val _alarmDate = MutableLiveData<Long>()


    val title: LiveData<String> = _title
    val description: LiveData<String> = _description
    val alarmDate: LiveData<Long> = _alarmDate

    fun onChangeTask(title: String, description: String, alarmDate: Long?) {


        _title.value = title
        _description.value = description
        _alarmDate.value = alarmDate?:0L
    }


    private val _selectedTask = mutableStateOf<TaskModel?>(null)
    val selectedTask: State<TaskModel?> = _selectedTask

    fun onSelectTask(task: TaskModel) {

        _selectedTask.value = task
    }

    val menuOptions: List<menuState> = listOf(
        menuState(1, "Actualizar"), menuState(2, "Borrar")
    )


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




    fun clearSelectedTask() {
        _selectedTask.value = null

    }

    private val _isDialogDate = mutableStateOf(false)
    val isDialogDate: State<Boolean> = _isDialogDate

    fun onChangeShowDDate() {
        _isDialogDate.value = !_isDialogDate.value
    }


//
    private val _isDialogShowDelete = mutableStateOf(false)
    val isDialogShowDelete: State<Boolean> = _isDialogShowDelete

    fun onChangeShowDialog() {
        _isDialogShowDelete.value = !_isDialogShowDelete.value
    }

    private val _isDialogShowUpdate = mutableStateOf(false)
    val isDialogShowUpdate: State<Boolean> = _isDialogShowUpdate
    fun onChangeShowDialogEdit() {

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

    fun updateSelectedTask(title: String, description: String, alarmDate: Long) {
        _tasks.value = _tasks.value.map { task ->
            if (task == _selectedTask.value) {
                task.copy(
                    task = title,
                    description = description,
                    alarmDate = alarmDate
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
                deleteTask(task)
            } else {
                task
            }

        }

    }

    fun deleteTask(taskModel: TaskModel) {
        Log.i("JR", taskModel.toString())
        val deleteTask = _tasks.value.find { it.id == taskModel.id } ?: taskModel

        viewModelScope.launch {
            val result = deleteTaskUseCase(deleteTask)

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
        Log.i("jrsele2", taskModel.toString())

        val updatedTask = _tasks.value.find { it.id == taskModel.id } ?: taskModel
//
        Log.i("jripdatesele3", taskModel.toString())

        viewModelScope.launch {
            val result = updateTaskUseCase(updatedTask)
            if (result.isSuccess) {
                getTasks()
                Log.i("jrsucces4", taskModel.toString())

            } else {
                Log.i("jrError5", taskModel.toString())

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

    fun ChangeShowLogOutDialog() {

        _showLogOutDialog.value = !showLogOutDialog.value

    }

    fun LogOut() {
        auth.signOut()


    }

}

