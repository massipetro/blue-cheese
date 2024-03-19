package com.bluecheese.android.di

import com.bluecheese.android.navigation.Router
import com.bluecheese.android.navigation.RouterImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import javax.inject.Singleton

@Module
@InstallIn(ViewModelComponent::class)
interface CommonModule {

    @Singleton
    @Binds
    fun provideRouter(
        router: RouterImpl
    ): Router
}