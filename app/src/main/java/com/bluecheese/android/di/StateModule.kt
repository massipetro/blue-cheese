package com.bluecheese.android.di

import com.bluecheese.android.presentation.signin.SignInState
import com.bluecheese.mvi.foundation.store
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
class StateModule {

    // @Provides
    // fun provideApplicationState() = store(ApplicationState())

    @Provides
    fun provideLoginState() = store(SignInState())
}