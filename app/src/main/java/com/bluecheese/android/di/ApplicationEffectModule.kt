package com.bluecheese.android.di

import com.bluecheese.android.presentation.common.ApplicationEffect
import com.bluecheese.mvi.foundation.EventReceiver
import com.bluecheese.mvi.foundation.EventSender
import com.bluecheese.mvi.foundation.FlowEventEmitter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @Singleton
    fun provideCoroutineScope() = CoroutineScope(Dispatchers.Default)
}

@Module
@InstallIn(ActivityRetainedComponent::class)
class ApplicationEffectModule {

    @Provides
    fun provideApplicationEffectReceiver(
        receiver: FlowEventEmitter<ApplicationEffect>
    ): EventReceiver<ApplicationEffect> = receiver

    @Provides
    fun provideApplicationEffectSender(
        sender: FlowEventEmitter<ApplicationEffect>
    ): EventSender<ApplicationEffect> = sender

    @Provides
    @ActivityRetainedScoped
    fun provideApplicationEventEmitter(
        scope: CoroutineScope,
    ): FlowEventEmitter<ApplicationEffect> = FlowEventEmitter(scope)
}