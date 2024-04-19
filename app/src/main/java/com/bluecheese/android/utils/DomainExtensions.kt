package com.bluecheese.android.utils

import android.content.Context
import com.bluecheese.android.R
import com.bluecheese.domain.exception.DomainException

fun Exception.message(context: Context): String? = when (this) {
    is DomainException.PasswordValidation.MissingCapitalLetter -> context.getString(R.string.missing_capital_letter)
    is DomainException.PasswordValidation.MissingDigit -> context.getString(R.string.missing_digit)
    is DomainException.PasswordValidation.MissingSpecialChar -> context.getString(R.string.missing_special_character)
    else -> null
}