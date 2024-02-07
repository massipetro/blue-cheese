package com.bluecheese.domain

import arrow.core.Either
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach

fun <In> Flow<Either<Exception, In>>.onEachFail(
    block: suspend (Exception) -> Unit
) = this.onEach { result ->
    result.mapLeft { block(it) }
}