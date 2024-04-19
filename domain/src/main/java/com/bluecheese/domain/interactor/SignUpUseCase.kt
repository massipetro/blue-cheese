package com.bluecheese.domain.interactor

import arrow.core.Either
import arrow.core.left
import com.bluecheese.domain.exception.DomainException
import com.bluecheese.domain.repository.user.ApiScope
import com.bluecheese.domain.repository.user.UserRepository
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject

@ActivityRetainedScoped
class SignUpUseCase @Inject constructor(
    @ApiScope private val userRepository: UserRepository
) {
    fun perform(params: Params): Flow<Either<Exception, FirebaseUser?>> = with(params) {
        val passwordException = isValidPassword(password)
        if (passwordException == null && isValidEmail(email)) userRepository.signUp(email, password)
        else return (passwordException ?: DomainException.EmailValidation()).left().let(::flowOf)
    }

    private fun isValidEmail(email: String): Boolean {
        val emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\$"
        return email.matches(emailRegex.toRegex())
    }

    private fun isValidPassword(password: String): DomainException? = when {
        !password.contains(Regex("[A-Z]")) -> DomainException.PasswordValidation.MissingCapitalLetter()
        !password.contains(Regex("[0-9]")) -> DomainException.PasswordValidation.MissingDigit()
        !password.contains(Regex("[^a-zA-Z0-9 ]")) -> DomainException.PasswordValidation.MissingSpecialChar()
        else -> null
    }

    data class Params(
        val email: String,
        val password: String,
    )
}