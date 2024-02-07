package com.bluecheese.data.di

import com.bluecheese.data.repository.user.UserRepositoryImpl
import com.bluecheese.domain.repository.user.ApiScope
import com.bluecheese.domain.repository.user.UserRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @ApiScope
    @Singleton
    abstract fun bindUserRepository(
        repository: UserRepositoryImpl
    ): UserRepository
}