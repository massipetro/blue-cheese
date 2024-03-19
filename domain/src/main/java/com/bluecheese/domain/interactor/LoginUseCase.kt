package com.bluecheese.domain.interactor

import arrow.core.Either
import arrow.core.left
import com.bluecheese.domain.repository.user.ApiScope
import com.bluecheese.domain.repository.user.UserRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@ActivityRetainedScoped
class LoginUseCase @Inject constructor(
    @ApiScope private val userRepository: UserRepository
) {
    fun perform(params: Params): Flow<Either<Exception, FirebaseUser?>> = with(params) {
        if (email.isBlank() || password.isBlank())
            return IllegalArgumentException().left().let(::flowOf)
        else userRepository.login(email, password)
    }

    data class Params(
        val email: String,
        val password: String,
    )
}