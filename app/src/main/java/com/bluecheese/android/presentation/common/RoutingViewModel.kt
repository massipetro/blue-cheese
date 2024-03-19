package com.bluecheese.android.presentation.common

import androidx.lifecycle.ViewModel
import com.bluecheese.android.navigation.Router
import com.bluecheese.android.navigation.RouterHolder
import com.bluecheese.android.navigation.navigateBack

abstract class RoutingViewModel(
    override val router: Router,
) : ViewModel(), RouterHolder {
    fun onBack() = navigateBack()
}