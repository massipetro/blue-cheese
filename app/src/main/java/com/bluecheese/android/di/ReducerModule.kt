package com.bluecheese.android.di

import com.bluecheese.android.presentation.signin.SignInReducers
import com.bluecheese.android.presentation.signin.SignInReducersImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ReducerModule {

    @Binds
    abstract fun provideLoginReducers(
        reducers: SignInReducersImpl
    ): SignInReducers
}