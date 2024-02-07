package com.bluecheese.data.repository.user

import arrow.core.Either
import arrow.core.left
import arrow.core.right
import com.bluecheese.domain.repository.user.UserRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth
) : UserRepository {
    override fun login(
        username: String,
        password: String
    ): Flow<Either<Exception, FirebaseUser>> {
        return flow {
            try {
                auth
                    .signInWithEmailAndPassword(username, password)
                    .await()
                    .user
                    ?.right()
                    ?: IllegalArgumentException().left()
            } catch (e: Exception) {
                e.left()
            }
        }
    }
}