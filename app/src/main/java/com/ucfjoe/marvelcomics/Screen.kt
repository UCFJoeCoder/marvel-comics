package com.ucfjoe.marvelcomics

sealed class Screen(val route: String) {
    data object CharactersScreen : Screen("characters")
    data object CharacterScreen : Screen("character")
}
