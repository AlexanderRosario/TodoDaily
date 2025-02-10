package com.alexander.tododaily.todotask.core.di

import com.alexander.tododaily.todotask.data.repository.AuthRepositoryImpl
import com.alexander.tododaily.todotask.data.repository.TaskRepositoryImpl
import com.alexander.tododaily.todotask.domain.iauthuser.AuthRepository
import com.alexander.tododaily.todotask.domain.itask.ITaskRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }



    @Provides
    @Singleton
    fun provideTaskRepository(
        firestore: FirebaseFirestore
    ): ITaskRepository {
        return TaskRepositoryImpl(firestore)
    }
}