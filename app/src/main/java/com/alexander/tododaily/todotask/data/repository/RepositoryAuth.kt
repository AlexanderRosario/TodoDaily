package com.alexander.tododaily.todotask.data.repository

import com.alexander.tododaily.todotask.domain.iauthuser.AuthRepository
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

//class AuthRepositoryImpl(
//    private val firebaseAuth: FirebaseAuth
//) : AuthRepository {
//    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit> {
//        return try {
//            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
//            if (result.user != null) {
//                Result.success(Unit) // Éxito
//            } else {
//                Result.failure(Exception("User not found"))
//            }
//        } catch (e: Exception) {
//            Result.failure(e) // Error
//        }
//    }
//}


class AuthRepositoryImpl @Inject constructor(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {
    override suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit> {
        return try {
            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()
            if (result.user != null) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("User not found"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }


    override suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<Unit> {
        return try {
            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()
            if (result.user != null) Result.success(Unit)
            else Result.failure(Exception("User creation failed"))
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
