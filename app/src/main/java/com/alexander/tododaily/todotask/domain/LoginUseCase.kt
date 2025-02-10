package com.alexander.tododaily.todotask.domain

import com.alexander.tododaily.todotask.domain.iauthuser.AuthRepository
import javax.inject.Inject


class LoginUseCase @Inject constructor(
        private val authRepository: AuthRepository
    ) {
        suspend operator fun invoke(email: String, password: String): Result<Unit> {
            return authRepository.signInWithEmailAndPassword(email, password)
        }
    }
