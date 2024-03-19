package com.bluecheese.android.di

import com.bluecheese.android.presentation.login.LoginReducers
import com.bluecheese.android.presentation.login.LoginReducersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReducerModule {

    @Binds
    abstract fun provideLoginReducers(
        reducers: LoginReducersImpl
    ): LoginReducers
}