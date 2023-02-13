package com.aghogho.multimodulecalorietracker.navigation

import androidx.navigation.NavController
import com.aghogho.core.util.UiEvent

fun NavController.navigate(event: UiEvent.Navigate) {
    this.navigate(event.route)
}
