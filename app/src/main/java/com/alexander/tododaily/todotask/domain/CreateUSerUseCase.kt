package com.alexander.tododaily.todotask.domain

import com.alexander.tododaily.todotask.domain.iauthuser.AuthRepository
import javax.inject.Inject

class CreateUserUseCase @Inject constructor(
    private val authRepository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return authRepository.createUserWithEmailAndPassword(email, password)
    }
}
