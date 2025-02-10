package com.alexander.tododaily.todotask.domain.itask

import com.alexander.tododaily.todotask.ui.model.TaskModel

interface ITaskRepository {
    suspend fun addTask(task: TaskModel): Result<Unit>
    suspend fun getTasks(): Result<List<TaskModel>>
    suspend fun updateTask(task: TaskModel): Result<Unit>
    suspend fun deleteTask(task: TaskModel): Result<Unit>
}