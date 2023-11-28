package com.ucfjoe.marvelcomics

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.character.CharacterScreen
import com.ucfjoe.marvelcomics.feature_marvel.presentation.ui.characters.ListCharactersScreen

@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "marvel"//Screen.CharactersScreen.route
    ) {
        navigation(
            startDestination = Screen.CharactersScreen.route,
            route = "marvel"
        ) {
            composable(route = Screen.CharactersScreen.route) {entry ->
                ListCharactersScreen(
                    onNavigation = {
                        navController.navigate(it.route)
                    }
                )
            }
            composable(
                route = Screen.CharacterScreen.route + "?character_id={character_id}",
                arguments = listOf(
                    navArgument("character_id") {
                        type = NavType.StringType
                        nullable = false
                        defaultValue = ""
                    }
                )
            ) {
                CharacterScreen(
                    onPopBackStack = { navController.popBackStack() },
                    onNavigation = { navController.navigate(it.route) }
                )
            }
        }
    }
}