package com.ucfjoe.marvelcomics.feature_marvel.presentation.ui

sealed class UiEvent {
    object PopBackStack: UiEvent()
    data class ShowSnackbar(val message: String) : UiEvent()
    data class Navigation(val route: String): UiEvent()
}