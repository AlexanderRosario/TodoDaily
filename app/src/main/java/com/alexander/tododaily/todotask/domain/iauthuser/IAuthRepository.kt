package com.alexander.tododaily.todotask.domain.iauthuser

interface AuthRepository {
    suspend fun signInWithEmailAndPassword(email: String, password: String): Result<Unit>
    suspend fun createUserWithEmailAndPassword(email: String, password: String): Result<Unit>
}