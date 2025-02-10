package com.alexander.tododaily.todotask.data.repository

import com.alexander.tododaily.todotask.domain.itask.ITaskRepository
import com.alexander.tododaily.todotask.ui.model.TaskModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class TaskRepositoryImpl @Inject constructor(
    private val firestore: FirebaseFirestore

) : ITaskRepository {


    private val tasksCollection = firestore.collection("tasks")

    override suspend fun addTask(task: TaskModel): Result<Unit> {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: return Result.failure(Exception("User not authenticated"))
        val taskWithUid = task.copy(uid = uid)

        return try {
            tasksCollection
                .document(taskWithUid.id.toString())
                .set(taskWithUid)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getTasks(): Result<List<TaskModel>> {
        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: return Result.failure(Exception("User not authenticated"))

        return try {
            val snapshot = tasksCollection
                .whereEqualTo("uid", uid)
                .get()
                .await()
            val tasks = snapshot.documents.mapNotNull { it.toObject(TaskModel::class.java) }
            Result.success(tasks)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
//    override suspend fun getTasks(): Result<List<TaskModel>> {
//        val uid = FirebaseAuth.getInstance().currentUser?.uid
//            ?: throw Exception("User not authenticated")
//
//        try {
//            val snapshot = firestore.collection("tasks")
//                .whereEqualTo("uid", uid) // Filtra por el UID del usuario
//                .get()
//                .await()
//
//            val tasks = snapshot.documents.mapNotNull { it.toObject(TaskModel::class.java) }
//            emit(Result.success(tasks))
//        } catch (e: Exception) {
//            emit(Result.failure(e))
//        }
//    }

    override suspend fun updateTask(task: TaskModel): Result<Unit> {


        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: return Result.failure(Exception("User not authenticated"))

        if (task.uid != uid) {
            return Result.failure(Exception("You cannot Update another user's task"))
        }

        return try {
            tasksCollection
                .document(task.id.toString())
                .set(task)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }



    override suspend fun deleteTask(task: TaskModel): Result<Unit> {

        val uid = FirebaseAuth.getInstance().currentUser?.uid
            ?: return Result.failure(Exception("User not authenticated"))

        if (task.uid != uid) {
            return Result.failure(Exception("You cannot update another user's task"))
        }

        return try {
            tasksCollection
                .document(task.id.toString())
                .delete()
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

}