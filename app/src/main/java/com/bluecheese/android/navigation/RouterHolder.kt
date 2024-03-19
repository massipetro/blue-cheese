package com.bluecheese.android.navigation

interface RouterHolder {
    val router: Router
}

fun RouterHolder.navigateTo(
    route: NavigationParameter
) = router.navigateTo(route)

fun RouterHolder.navigateBack() = router.navigateBack()

fun RouterHolder.navigateBackTo(
    route: NavigationParameter,
    parent: NavigationParameter
) = router.navigateBackTo(route, parent)