package com.bluecheese.android.di

import com.bluecheese.android.presentation.navigation.NavigationBarReducers
import com.bluecheese.android.presentation.navigation.NavigationBarReducersImpl
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

    @Binds
    abstract fun provideNavigationBarReducers(
        reducers: NavigationBarReducersImpl
    ): NavigationBarReducers
}