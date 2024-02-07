package com.bluecheese.domain.repository.user

import arrow.core.Either
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ApiScope

interface UserRepository {
    fun login(
        username: String,
        password: String
    ): Flow<Either<Exception, FirebaseUser?>>
}