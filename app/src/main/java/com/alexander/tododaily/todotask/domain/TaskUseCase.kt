package com.alexander.tododaily.todotask.domain

import com.alexander.tododaily.todotask.domain.itask.ITaskRepository
import com.alexander.tododaily.todotask.ui.model.TaskModel
import javax.inject.Inject

class AddTaskUseCase @Inject constructor(private val repository: ITaskRepository) {
    suspend operator fun invoke(task: TaskModel): Result<Unit> = repository.addTask(task)
}

class GetTasksUseCase @Inject constructor(private val repository: ITaskRepository) {
    suspend operator fun invoke(): Result<List<TaskModel>> = repository.getTasks()
}

class UpdateTaskUseCase @Inject constructor(private val repository: ITaskRepository) {
    suspend operator fun invoke(task: TaskModel): Result<Unit> = repository.updateTask(task)
}

class DeleteTaskUseCase @Inject constructor(private val repository: ITaskRepository) {
    suspend operator fun invoke(task: TaskModel): Result<Unit> = repository.deleteTask(task)
}
