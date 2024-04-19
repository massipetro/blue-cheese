package com.bluecheese.domain.exception

sealed class DomainException : Exception() {
    class GenericError : DomainException()

    class EmailValidation : DomainException()

    sealed class PasswordValidation : DomainException() {
        class MissingCapitalLetter : PasswordValidation()
        class MissingDigit : PasswordValidation()
        class MissingSpecialChar : PasswordValidation()
    }
}